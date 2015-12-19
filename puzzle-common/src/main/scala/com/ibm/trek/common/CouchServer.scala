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

package com.ibm.trek.common

import com.ibm.trek.common.CouchDao.DaoFactory
import com.twitter.finagle.{ListeningServer, Thrift}
import com.twitter.util.Await
import org.slf4j.LoggerFactory

class CouchServer[T](val serverHost: String = "0.0.0.0", val serverPort: Int = 8888,
                     val zkAddress: Option[String] = None,
                     val dbHost: String = "127.0.0.1", val dbPort: Int = 5984, val dbHttps: Boolean = false,
                     val dbName: String, val dbUsername: String = "", val dbPassword: String = "",
                     val createDb: Boolean = true, daoFactory: DaoFactory[T], serviceFactory: ServiceFactory[T]) {

  val log = LoggerFactory.getLogger(getClass.getName)

  private def createServer(daoFactory: DaoFactory[T], serviceFactory: ServiceFactory[T]): ListeningServer = {
    val dbConfig = DbConfig(host = dbHost, port = dbPort, name = dbName, https = dbHttps,
                            username = Option(dbUsername).filter(_.trim.nonEmpty),
                            password = Option(dbPassword).filter(_.trim.nonEmpty))

    val serverConfig = ServerConfig(host = serverHost, port = serverPort, zkAddress = zkAddress)

    log.info(s"Server Configuration: $serverConfig, DB Configuration: $dbConfig")
    val couchService = serviceFactory(daoFactory(dbConfig))

    val server = serverConfig.zkAddress match {
      case Some(z) =>
        log.info(s"Announcing to ZK on $z")
        Thrift.serveAndAnnounce(z, s"${serverConfig.host}:${serverConfig.port}", couchService)
      case None =>
        log.info("No ZK address provided...")
        Thrift.serve(s"${serverConfig.host}:${serverConfig.port}", couchService)
    }
    server
  }

  def serve(): Unit = {
    val server = createServer(daoFactory, serviceFactory)
    Await.ready(server)
  }
}
