package com.ibm.trek.puzzle.master

import com.ibm.trek.model.{Coordinate, Site}
import com.ibm.trek.player.PlayerService
import com.ibm.trek.puzzle.PuzzleService
import com.ibm.trek.puzzle.model.{Puzzle, PuzzleSite}
import com.twitter.util.{Await, Future}
import org.scalacheck.Gen
import org.scalamock.scalatest.MockFactory
import org.scalatest.prop.PropertyChecks
import org.scalatest.{PropSpec, ShouldMatchers}

class PuzzleMasterLogicSpec extends PropSpec with PropertyChecks with ShouldMatchers with MockFactory with Fixtures {


  // for all puzzles, for all sites in the puzzle, a correct guess
  // should yield the next answer

  val mySiteGen = for {
    x <- Gen.choose[Double](-180, 180)
    y <- Gen.choose(-90, 90)
  } yield Site(Coordinate(y, x))

  val myPuzzleSiteGen = for {
    id <- Gen.alphaStr
    name <- Gen.alphaStr
    message <- Gen.alphaStr
    clue <- Gen.alphaStr
    site <- mySiteGen
  } yield PuzzleSite(id, name, message, clue, site)

  val myPuzzleGen = for {
    id <- Gen.alphaStr
    trail <- Gen.containerOf[Seq, PuzzleSite](myPuzzleSiteGen)
    startMessage <- Gen.alphaStr
    endMessage <- Gen.alphaStr
    owner <- Gen.alphaStr
  } yield Puzzle(Some(id), trail, startMessage, endMessage, owner)

  property("Successful guesses yield new clues") {
    forAll(myPuzzleGen) { p: Puzzle =>
      whenever(p.trail.length > 1) {

        // extract values
        val puzzleId = p.id.get
        val currentSiteId = p.trail.head.id
        val nextSiteClue = p.trail.tail.head.clue
        val goodGuess = p.trail.head.site

        // configure the timestamp gen
        val tsGenMock = stub[TimeStampGenerator]
        val playerServiceStub = stub[PlayerService.FutureIface]
        val puzzleServiceStub = stub[PuzzleService.FutureIface]

        // configure stubs and mocks
        tsGenMock.apply _ when() returns now
        playerServiceStub.visit _ when * returns Future(journey)
        puzzleServiceStub.get _ when puzzleId returns Future(p)

        val service = new PuzzleMasterServiceImpl(playerServiceStub, puzzleServiceStub, tsGenMock)

        // Submit a guess
        val response = Await.result(service submitGuess(playerId, puzzleId, goodGuess, currentSiteId))
        response.nextSiteClue shouldEqual Some(nextSiteClue)
      }
    }
  }
}