package com.ibm.trek.puzzle

import java.util.logging.{Level, Logger}

import com.ibm.trek.puzzle.PuzzleService.FinagledClient
import com.ibm.trek.puzzle.model.{Puzzle, PuzzleSite}
import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.thrift.ThriftClientFramedCodec
import com.twitter.util.Await
import org.apache.thrift.protocol.TBinaryProtocol.Factory

object PuzzleServiceClient {
  private val log = Logger.getLogger(getClass.getName)
  log.setLevel(Level.ALL)

  def main(args: Array[String]): Unit = {
    val address = args(0)
    log.info(s"Connecting to address: $address")

    val transport = ClientBuilder().name("puzzleserviceclient").dest(address).
                    codec(ThriftClientFramedCodec()).hostConnectionLimit(1).build()

    val client = new FinagledClient(transport, new Factory())

    println("creating puzzle instance")

    val puzzle = Puzzle(trail = Seq[PuzzleSite](), startMessage = "Hello Columbus fan",
                        endMessage = "Goodbye Columbus fan", owner = "someone")

    val createdPzl = client.create(puzzle)
    val result = Await.result(createdPzl)
    println(s"ID of Puzzle Retrieved ${result.id} object $result")
  }
}