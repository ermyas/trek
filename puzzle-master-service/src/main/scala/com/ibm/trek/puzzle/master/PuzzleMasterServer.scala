package com.ibm.trek.puzzle.master

import java.util.logging.Logger

import com.ibm.trek.common.Server
import com.ibm.trek.common.cf.{CFApp, VCAPServices}
import com.ibm.trek.common.ops.{HttpClientFilter, HttpFilter}
import com.ibm.trek.player.PlayerService
import com.ibm.trek.puzzle.PuzzleService
import com.ibm.trek.puzzle.master.PuzzleMasterService.FinagledService
import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.http.{Http => Httpx}
import com.twitter.finagle.thrift.ThriftClientFramedCodec
import com.twitter.finagle.{Http, Service}
import com.twitter.server.TwitterServer
import com.twitter.util.{Await, Duration}
import org.apache.thrift.protocol.TJSONProtocol
import org.apache.thrift.protocol.TJSONProtocol.Factory
import org.apache.thrift.protocol.TBinaryProtocol
import org.jboss.netty.handler.codec.http.{HttpRequest, HttpResponse}

object PuzzleMasterServer extends TwitterServer {
  private val defaultHost = "0.0.0.0"
  val serverHost = flag("host", defaultHost, "IP address to host this service on")
  val serverPort = flag("port", 80, "Port number to host this service on")
  val playerServiceIp = flag("playerServiceIp", "0.0.0.0", "IP hosting player service")
  val playerServicePort = flag("playerServicePort", 8888, "Port number for talking to player service")
  val puzzleServiceIp = flag("puzzleServiceIp", "0.0.0.0", "IP hosting puzzle service")
  val puzzleServicePort = flag("puzzleServicePort", 8887, "Port number for talking to puzzle service")

  def createService(playerClient: PlayerService.FutureIface, puzzleClient: PuzzleService.FutureIface) = {
    val serviceImpl: PuzzleMasterServiceImpl = new PuzzleMasterServiceImpl(playerClient,
                                                                                   puzzleClient, new TimeStampGenerator)
    new FinagledService(serviceImpl, new TJSONProtocol.Factory())
  }

  def main() {
    val playerClient = createPlayerClient(s"${playerServiceIp()}:${playerServicePort()}")
    val puzzleClient = createPuzzleClient(s"${puzzleServiceIp()}:${puzzleServicePort()}")

    val serviceAddress =
      if (VCAPServices.defined) s"${CFApp.host}:${CFApp.port}"
      else s"${serverHost()}:${serverPort()}"

    val service = createService(playerClient, puzzleClient)

    val server = Http.serve(serviceAddress, new HttpFilter andThen service)

    Await.ready(server)
    closeOnExit(server)
  }

  private def createPlayerClient(address: String): PlayerService.FinagledClient = {
    val transport = ClientBuilder().name("playerserviceclient").dest(address).
      codec(ThriftClientFramedCodec()).hostConnectionLimit(1).build()
    new PlayerService.FinagledClient(transport, protocolFactory = new TBinaryProtocol.Factory())
  }

  private def createPuzzleClient(address: String): PuzzleService.FinagledClient = {
    val transport = ClientBuilder().name("puzzleserviceclient").dest(address).
      codec(ThriftClientFramedCodec()).hostConnectionLimit(1).build()
    new PuzzleService.FinagledClient(transport, protocolFactory = new TBinaryProtocol.Factory())
  }
}
