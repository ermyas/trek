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

import com.twitter.finagle.thrift.ThriftClientRequest
import com.twitter.finagle.{Filter, Service}
import com.twitter.util.Future
import org.jboss.netty.buffer.ChannelBuffers
import org.jboss.netty.handler.codec.http._

class HttpClientFilter(address: String) extends Filter[ThriftClientRequest, Array[Byte], HttpRequest, HttpResponse] {
  val log = Logger.getLogger("ThriftToHttpFilter")

  def apply(request: ThriftClientRequest, client: Service[HttpRequest, HttpResponse]) = {
    val httpRequest = toHttpReq(request)
    client(httpRequest).flatMap { res => Future.value(res.getContent.array()) }
  }

  private def toHttpReq(request: ThriftClientRequest) = {
    val content = ChannelBuffers.copiedBuffer(request.message)
    val httpRequest = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, "/")
    httpRequest.headers().set(HttpHeaders.Names.CONTENT_LENGTH, content.readableBytes().toString)
    httpRequest.headers().set(HttpHeaders.Names.HOST, address)
    httpRequest.setContent(content)
    httpRequest
  }
}
