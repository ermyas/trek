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
import com.ibm.trek.common.cf.Credentials
import com.twitter.server.TwitterServer

trait Server extends TwitterServer {
  private val defaultHost = "0.0.0.0"
  val serverHost = flag("host", defaultHost, "IP address to host this service on")
  val serverPort = flag("port", 80, "Port number to host this service on")
  val zkAddress  = flag("zk", "", "Address of ZooKeeper service registry server")
  val dbHost     = flag("dbHost", "127.0.0.1", "Database IP Address")
  val dbPort     = flag("dbPort", 5984, "Database server port number")
  val dbHttps    = flag("dbHttps", false, "Database connection protocol: https or http")
  val dbName     = flag("dbName", "store", "Database name to connect to")
  val dbUsername = flag("dbUsername", "", "Database dbUsername")
  val dbPassword = flag("dbPassword", "", "Database dbPassword")
  val createDb   = flag("createDb", true, "Create database if does not exist")

  lazy val zk = zkAddress() match {case x if x.trim.isEmpty => None case z => Some(z)}

  def createFromFlags[T](daoFactory: DaoFactory[T], serviceFactory: ServiceFactory[T]) = {
    log.info("creating CouchServer from commandline flags")
    create[T](serverHost(), serverPort(), zk, dbHost(), dbPort(), dbHttps(), dbName(), dbUsername(),
                       dbPassword(), createDb(), daoFactory, serviceFactory)
  }

  private def create[T](host: String, port: Int, zk: Option[String], dbHost: String, dbPort: Int, https: Boolean,
                        dbName: String, dbUsername: String, dbPassword: String, create: Boolean,
                        daoFactory: DaoFactory[T], serviceFactory: ServiceFactory[T]) =
    new CouchServer[T](host, port, zk, dbHost, dbPort, https, dbName, dbUsername, dbPassword, create,
                       daoFactory, serviceFactory)

  def createFromVCAP[T](dbCred: Credentials, dbname: String, host: String, port: Int, zk: Option[String],
                        createdb: Boolean, daoFactory: DaoFactory[T], serviceFactory: ServiceFactory[T]) = {
    log.info("creating CouchServer from VCAP variables")
    create[T](host, port, zk, dbCred.host, dbCred.port, https=true, dbname, dbCred.username, dbCred.password,
                       createdb, daoFactory, serviceFactory)
  }
}
