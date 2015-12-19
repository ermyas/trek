package com.ibm.trek.player

import com.ibm.trek.player.JourneyService.FinagledClient
import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.thrift.ThriftClientFramedCodec
import com.twitter.util.Future
import org.apache.thrift.protocol.TBinaryProtocol.Factory
object JourneyClientFactory {
  def apply(address: String): JourneyService[Future] = {
    val transport = ClientBuilder().name("journeyserviceclient").dest(address).
                    codec(ThriftClientFramedCodec()).hostConnectionLimit(1).build()

    new FinagledClient(transport, new Factory())
  }
}
