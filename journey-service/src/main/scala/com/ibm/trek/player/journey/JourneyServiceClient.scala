package com.ibm.trek.player.journey

import java.util.logging.{Level, Logger}

import com.ibm.trek.model.{Coordinate, Site}
import com.ibm.trek.player.JourneyService.FinagledClient
import com.ibm.trek.player.model.PlayerSite
import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.thrift.ThriftClientFramedCodec
import com.twitter.util.{Await, Future}
import org.apache.thrift.protocol.TBinaryProtocol.Factory
object JourneyServiceClient {
  private val log = Logger.getLogger(getClass.getName)
  log.setLevel(Level.ALL)
  val puzzleIds = Seq("puzzle-1", "puzzle-2")
  val playerIds = Seq("player-1", "player-2", "player-3")

  def main(args: Array[String]): Unit = {
    val address = args(0)

    log.info(s"Connecting to address: $address")

    val transport = ClientBuilder().name("journeyserviceclient").dest(address).
                    codec(ThriftClientFramedCodec()).hostConnectionLimit(1).build()

    val journeyService = new FinagledClient(transport, new Factory())

    log.info(s"Connecting to ZK $address")

    val visits = for {
      puzzle <- puzzleIds
      player <- playerIds
      playerSite = PlayerSite(player = player, puzzle = puzzle, timestamp = System.currentTimeMillis(),
                              site = Site(Coordinate(23423.343, 23423.22)))
      visit = journeyService.visit(playerSite = playerSite)
    } yield visit

    val allVisits = Future.collect(visits)

    log.info(s"Adding all visits...${visits.size}")
    Await.result(allVisits)

    log.info("Retrieving all journey data")
    val result = Await.result(journeyService.get(playerId = playerIds.head, puzzleId = puzzleIds.head))

    log.info(s"Results retrieve ${result.size}")
    result.foreach(println)
  }
}
