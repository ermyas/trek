package com.ibm.trek.puzzle.master

import com.ibm.trek.player.PlayerService
import com.ibm.trek.puzzle.PuzzleService
import com.twitter.util.{Await, Future}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, OneInstancePerTest, ShouldMatchers}

class PuzzleMasterLogicTest extends FlatSpec with ShouldMatchers with OneInstancePerTest with MockFactory with Fixtures {

  // configure the timestamp gen
  val timestampStub = stub[TimeStampGenerator]
  val playerServiceStub = stub[PlayerService.FutureIface]
  val puzzleServiceStub = stub[PuzzleService.FutureIface]

  // configure stubs
  timestampStub.apply _ when() returns now
  puzzleServiceStub.get _ when puzzleId returns Future(puzzle)
  playerServiceStub.visit _ when * returns Future(journey)

  val service = new PuzzleMasterServiceImpl(playerServiceStub, puzzleServiceStub, timestampStub)

  "submitGuess" should "return same clue if guess is incorrect" in {
    val response = Await.result(service submitGuess(playerId, puzzleId, badGuess, firstSite.id))
    response.nextSiteClue shouldEqual Some(firstSite.clue)
  }

  it should "return current site id if guess is incorrect" in {
    val response = Await.result(service submitGuess(playerId, puzzleId, badGuess, firstSite.id))
    response.nextSiteId shouldEqual Some(firstSite.id)
  }

  it should "return next clue if guess is correct" in {
    val response = Await.result(service submitGuess(playerId, puzzleId, goodGuess, firstSite.id))
    response.nextSiteClue shouldEqual Some(secondSite.clue)
  }

  it should "return next site id if guess is correct" in {
    val response = Await.result(service submitGuess(playerId, puzzleId, goodGuess, firstSite.id))
    response.nextSiteId shouldEqual Some(secondSite.id)
  }

  it should "return completion message when last clue is solved" in {
    val response = Await.result(service submitGuess(playerId, puzzleId, lastSite.site, lastSite.id))
    response.message shouldEqual Some(puzzle.endMessage)
  }

  it should "return no message when last clue is not solved" in {
    val response = Await.result(service submitGuess(playerId, puzzleId, badGuess, lastSite.id))
    response.message shouldEqual None
  }

  "startPuzzle" should "return a whole puzzle" in {
    val puzzle = Await.result(service startPuzzle(playerId, puzzleId))
    puzzle._2.head.clue shouldEqual firstSite.clue
  }


}