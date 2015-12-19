package com.ibm.trek.player

import java.util.logging.{Level, Logger}

import com.ibm.trek.model.{Coordinate, Site}
import com.ibm.trek.player.PlayerService.FinagledClient
import com.ibm.trek.player.model.{Player, PlayerSite}
import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.thrift.ThriftClientFramedCodec
import com.twitter.util.Await
import org.apache.thrift.protocol.TBinaryProtocol.Factory
object PlayerServiceClient {
  val dummyPuzzleId = "fake-puzzle-id"
  val player = Player(name = "John Doe")
  private val log = Logger.getLogger(getClass.getName)
  log.setLevel(Level.ALL)

  def main(args: Array[String]): Unit = {
    val address = s"${args(0)}"

    val transport = ClientBuilder().name("playerserviceclient").dest(address).
                    codec(ThriftClientFramedCodec()).hostConnectionLimit(1).build()

    val playerService = new FinagledClient(transport, new Factory())

    val ops = playerService.create(player)

    Await.result(ops.flatMap(p => {
      println(s"* CREATED Player '${p.id}'")
      val playerSite = PlayerSite(player = p.id.get, puzzle = dummyPuzzleId, timestamp = System.currentTimeMillis(),
                                  site = Site(Coordinate(23423.343, 23423.22)))
      playerService.visit(playerSite)
    }).flatMap { p =>
      println(s"retrieving journey $p")
      playerService.getJourney(p.player, dummyPuzzleId)
               }.foreach(x => {
      println(s"being printed $x")
      println(x)
    }))
  }
}