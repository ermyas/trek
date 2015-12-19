package com.ibm.trek.puzzle.master

import com.ibm.trek.player.PlayerService
import com.ibm.trek.puzzle.PuzzleService
import com.twitter.util.Future
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, OneInstancePerTest, ShouldMatchers}

class PuzzleMasterServiceImplTest extends FlatSpec with ShouldMatchers with OneInstancePerTest with MockFactory with Fixtures {

  // configure the timestamp gen
  val tsGenMock = stub[TimeStampGenerator]
  tsGenMock.apply _ when() returns now

  val playerServiceMock = mock[PlayerService.FutureIface]
  val puzzleServiceStub = stub[PuzzleService.FutureIface]

  puzzleServiceStub.get _ when puzzleId returns Future(puzzle)

  val service = new PuzzleMasterServiceImpl(playerServiceMock, puzzleServiceStub, tsGenMock)

  "PuzzleMaster" should "notify Player Service when a new guess is submitted" in {
    // set expectations
    (playerServiceMock.visit _).expects(playerSite) returning Future(journey) once()

    // run system under test
    service submitGuess(playerId, puzzleId, playerSite.site, "Melb")
  }

}