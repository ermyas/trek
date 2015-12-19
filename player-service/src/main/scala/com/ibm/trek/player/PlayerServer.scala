package com.ibm.trek.player

import com.ibm.trek.common.cf.VCAPServices
import com.ibm.trek.common.{CouchDao, Server, ServiceFactory}
import com.ibm.trek.player.PlayerService.FinagledService
import com.ibm.trek.player.model.Player
import com.twitter.util.Future
import org.apache.thrift.protocol.TBinaryProtocol.Factory

object PlayerServer extends Server {
  val vcapKey = "cloudantNoSQLDB"
  val journeyServer = flag("journeyServer", "0.0.0.0:9889", "The address of the JourneyService instance. This could " +
                                                          "either be a host:port or a zookeeper path")
  def main(): Unit = {
    val address = s"${journeyServer()}"
    val journeyService = JourneyClientFactory(address)
    log.info(s"Creating journey service on $address")
    createCouchServer(journeyService).serve()
  }

  def createCouchServer(journeyService: JourneyService[Future]) = {
    VCAPServices.service(vcapKey) match {
      case Some(details) => createFromVCAP[Player] (details.head.credentials, dbName(), serverHost(),
        Integer.parseInt(System.getenv("PORT")), zk, createDb(),PlayerDaoFactory, serviceFactory(journeyService))
      case None => createFromFlags[Player](PlayerDaoFactory, serviceFactory(journeyService))
    }
  }

  private def serviceFactory(journeyService: JourneyService[Future]): ServiceFactory[Player] = {
    def serviceFactory(dao: CouchDao[Player]): FinagledService = new
        FinagledService(new PlayerServiceImpl(dao, journeyService), new Factory())
    serviceFactory
  }
}

