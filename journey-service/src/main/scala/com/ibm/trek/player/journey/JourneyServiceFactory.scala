package com.ibm.trek.player.journey

import com.ibm.trek.common.{CouchDao, CouchService, ServiceFactory}
import com.ibm.trek.player.JourneyService.FinagledService
import com.ibm.trek.player.model.PlayerSite
import org.apache.thrift.protocol.TBinaryProtocol.Factory

object JourneyServiceFactory extends ServiceFactory[PlayerSite] {
  override def apply(dao: CouchDao[PlayerSite]): CouchService = {
    dao match {
      case journeyDao: JourneyDao =>
        val service = new JourneyServiceImpl(dao = journeyDao)
        new FinagledService(service, new Factory())
    }
  }
}
