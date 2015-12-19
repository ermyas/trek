/*
 *   Copyright 2015 IBM Corporation
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.ibm.trek.common.ops

import java.util.logging.Logger

import com.twitter.finagle._
import com.twitter.util.Future
import org.jboss.netty.buffer.ChannelBuffers
import org.jboss.netty.handler.codec.http._

class HttpFilter extends Filter[HttpRequest, HttpResponse, Array[Byte], Array[Byte]] {
  private val log = Logger.getLogger(getClass.getName)
  override def apply(request: HttpRequest, service: Service[Array[Byte], Array[Byte]]): Future[HttpResponse] = {
    log.info(s"HTTP Filter receiving request size: ${request.getMethod} ${request.getUri} ${
      request.getContent.array()
      .length
    } Bytes")
    val thriftFuture = service(request.getContent.array())
    log.info(s"Thrift method invoked, returning response....${request.getProtocolVersion}")
    thriftFuture flatMap { res =>
      log.info(s"Sending HTTP Response to client of size ${request.getContent.array().length} Protocol " +
               s"version:${request.getProtocolVersion}")
      val response = new DefaultHttpResponse(request.getProtocolVersion, HttpResponseStatus.OK)
      response.setContent(ChannelBuffers.wrappedBuffer(res))
      response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, response.getContent.readableBytes.toString)
      Future.value(response)
    }
  }
}
