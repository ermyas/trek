package com.ibm.trek.puzzle.master

import com.twitter.util.{Await, Future}
import com.ibm.trek.player.model._
import com.ibm.trek.player.PlayerService
import com.ibm.trek.puzzle.master.model._
import com.ibm.trek.puzzle.master._
import com.ibm.trek.puzzle.PuzzleService
import com.ibm.trek.puzzle.model._
import com.ibm.trek.model._
import org.slf4j.LoggerFactory

class PuzzleMasterServiceImpl(
                               playerClient: PlayerService.FutureIface,
                               puzzleClient: PuzzleService.FutureIface,
                               timestampGenerator: TimeStampGenerator
                             ) extends PuzzleMasterService.FutureIface {

  def lastSite(sites: List[PuzzleSite]): Boolean = sites.length == 1

  object difficulty extends Enumeration {
    type difficulty = Value
    val Easy, Moderate, Hard = Value
  }

  val difficultySettings = Map((difficulty.Easy, 1.0), (difficulty.Moderate, 0.5), (difficulty.Hard, 0.1))
  val defaultDifficulty = difficulty.Easy
  val log = LoggerFactory.getLogger(getClass.getName)


  override def submitGuess(playerId: String, puzzleId: String, guess: Site, targetId: String): Future[PuzzleResponse] = {
    // Submit the visited site to the player service
    log.info("Notifying player service")
    notifyPlayerService(PlayerSite(timestampGenerator(), guess, player = playerId, puzzle = puzzleId))

    // Retrieve the puzzle
    log.info("Retrieving puzzle")
    val puzzle = Await.result(puzzleClient.get(puzzleId))

    // Building a response for the user
    log.info("Building a response")
    val responseTemplate = PuzzleResponse(playerId, puzzleId, Some(targetId))
    buildResponse(puzzle, guess, targetId, responseTemplate)
  }

  def buildResponse(puzzle: Puzzle, guess: Site, targetId: String, responseTemplate: PuzzleResponse): Future[PuzzleResponse] = {
    // The target site is given by the site guess key
    val remainingTrail: List[PuzzleSite] = traverseTrail(puzzle.trail.toList, targetId)

    // Based on where we are in the trail
    remainingTrail match {
      case Nil => Future.exception(new PuzzleSiteDoesNotExist("The trail is empty"))
      case _ => Future.value(evaluateGuess(guess, remainingTrail, puzzle, responseTemplate))
    }
  }

  def evaluateGuess(guess: Site, remainingTrail: List[PuzzleSite], puzzle: Puzzle, responseTemplate: PuzzleResponse): PuzzleResponse = {

    val targetSite = remainingTrail.head.site
    val numberOfStages = puzzle.trail.length
    val currentStage = numberOfStages - remainingTrail.length

    def progressFactory(i: Int) = Some(Progress(currentStage = i.toByte, numberOfStages.toByte))

    if (correctGuess(guess, targetSite, difficultySettings(defaultDifficulty))) {

      val response = responseTemplate.copy(
        progress = progressFactory(currentStage + 1),
        correctLastGuess = Some(true)
      )

      remainingTrail match {

        case _ :: Nil => response.copy(
          nextSiteId = None,
          message = Some(puzzle.endMessage)
        )

        case _ :: next :: _ => response.copy(
          nextSiteId = Some(next.id),
          nextSiteClue = Some(next.clue)
        )
        case Nil => response.copy(
          nextSiteId = None,
          nextSiteClue = None
        )
      }
    }
    else
      responseTemplate.copy(
        nextSiteClue = Some(remainingTrail.head.clue),
        correctLastGuess = Some(false),
        progress = progressFactory(currentStage)
      )
  }

  def traverseTrail(trail: List[PuzzleSite], currentSiteId: String): List[PuzzleSite] = {
    trail match {
      case Nil => Nil
      case head :: tail => head.id match {
        case `currentSiteId` => trail
        case _ => traverseTrail(tail, currentSiteId)
      }
    }
  }

  override def startPuzzle(playerId: String, puzzleId: String): Future[Puzzle] = {
    log.info(s"Starting new puzzle ($puzzleId) for player ($playerId)")
    puzzleClient.get(puzzleId)
  }

  def notifyPlayerService(playerSite: PlayerSite): PlayerSite =
    Await.result(playerClient.visit(playerSite))

  def correctGuess(guess: Site, target: Site, delta: Double): Boolean = {
    Spatial.distance(guess.coord, target.coord) < delta
  }

  override def getPuzzleList(limit: Option[Int], skip: Option[Int]): Future[Seq[Puzzle]] = {
    log.info(s"Retrieving puzzle list (limit = $limit, skip = $skip)")
    puzzleClient.getAll(limit.getOrElse(10), skip.getOrElse(0))
  }
}
