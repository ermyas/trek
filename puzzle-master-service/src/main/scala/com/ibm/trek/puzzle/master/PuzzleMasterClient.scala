package com.ibm.trek.puzzle.master

import java.util.logging.Logger

import com.ibm.trek.common.ops.HttpClientFilter
import com.ibm.trek.model.{Coordinate, Site}
import com.ibm.trek.player.PlayerService
import com.ibm.trek.player.model.Player
import com.ibm.trek.puzzle.PuzzleService
import com.ibm.trek.puzzle.master.model.PuzzleResponse
import com.ibm.trek.puzzle.model.{Puzzle, PuzzleSite}
import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.http.Http
import com.twitter.util.{Await, Duration, Future}
import org.apache.thrift.protocol.TJSONProtocol.Factory
object PuzzleMasterClient {

  private val log = Logger.getLogger(getClass.getName)

  def createHttpClient(address: String) = ClientBuilder().codec(Http()).hosts(address).
                                          hostConnectionLimit(Integer.MAX_VALUE).logger(log)
                                          .tcpConnectTimeout(Duration.Top).retries(0).build()

  def createPuzzleClient(address: String) = new
      PuzzleService.FinagledClient(new HttpClientFilter(address) andThen createHttpClient(address),
                                   protocolFactory = new Factory())

  def createPuzzleMasterClient(address: String) =
    new PuzzleMasterService.FinagledClient(new HttpClientFilter(address) andThen createHttpClient(address),
                                           protocolFactory = new Factory())

  def createPlayerClient(address: String) =
    new PlayerService.FinagledClient(new HttpClientFilter(address) andThen createHttpClient(address),
                                     protocolFactory = new Factory())

  def createDummyPuzzle(client: PuzzleService.FinagledClient): Future[Puzzle] = {
    val gPy = PuzzleSite(id = "great-pyramids", name = "The Great Pyramids", clue = "The Great Pyramids of Giza",
                         message = Some("Great Pyramids of Giza"),
                         site = Site(coord = Coordinate(latitude = 29.979169, longitude = 31.134052)))

    val hg = PuzzleSite(id = "hanging-garden", name = "Hanging Garden", clue = "Hanging Gardens of Babylon",
                        message = Some("Hanging Gardens of Babylon"),
                        site = Site(coord = Coordinate(latitude = 32.543510, longitude = 44.423962)))

    val ta = PuzzleSite(id = "temple-artemis", name = "Temple of Artemis", clue = "Temple of Artemis",
                        message = Some("Templte of Artemis"),
                        site = Site(coord = Coordinate(latitude = 37.951139, longitude = 27.365371)))
    val dummyPuzzle = Puzzle(trail = Seq(gPy, hg, ta),
                             startMessage = "Around the World in 80 Days is the name of the game!! Lets Play!",
                             endMessage = "Even Phileas Fogg would not have done it better, WELL DONE!",
                             owner = "Phileas Fogg")

    client.create(dummyPuzzle)
  }

  def createDummyPlayer(client: PlayerService.FinagledClient) = {
    val player = Player(name = "Phileas Fogg")
    client.create(player)
  }

  def main(args: Array[String]): Unit = {
    if (args.length != 3) {
      println(s"Usage: ${getClass.getName} puzzle-service-add player-service-add puzzle-master-add")
      System.exit(1)
    }

    val puzzleClient: PuzzleService.FinagledClient = createPuzzleClient(args(0))
    val playerClient: PlayerService.FinagledClient = createPlayerClient(args(1))
    val puzzleMasterClient: PuzzleMasterService.FinagledClient = createPuzzleMasterClient(args(2))

    val puzzle = Await.result(createDummyPuzzle(puzzleClient))
    val player = Await.result(createDummyPlayer(playerClient))

    println(s"Puzzle ${puzzle.id}\nPlayer: ${player.id}")
    val startRes = Await.result(puzzleMasterClient.startPuzzle(playerId = player.id.get, puzzleId = puzzle.id.get))


    log.info("Starting puzzle")
    printPuzzle(startRes)

    val guessResult = Await.result(puzzleMasterClient.submitGuess(playerId = player.id.get, puzzleId = puzzle.id.get,
                                                                  siteGuess = Site(Coordinate(-38.0, 145)),
                                                                  targetPuzzleSiteId = "great-pyramids"
                                                                 ))

    log.info("Guessing result")
    printResult(guessResult)
  }

  def printResult(response: PuzzleResponse): Unit = {
    println(s"Hi ${response.playerId}, is playing puzzle ${response.puzzleId}")
    response.nextSiteClue match {
      case Some(c) => println(s"Clue: $c")
      case None => println("No clue available!")
    }
  }

  def printPuzzle(puzzle: Puzzle): Unit = {
    println(s"Playing puzzle ${puzzle.id.get}")
    puzzle.trail.map(_._4).foreach(println)
  }
}