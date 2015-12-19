package com.ibm.trek.player.journey

import com.ibm.trek.common.Server
import com.ibm.trek.common.cf.{CFApp, VCAPServices}
import com.ibm.trek.player.model.PlayerSite

object JourneyServer extends Server {
  val vcapKey = "cloudantNoSQLDB"

  def main(): Unit = { createCouchServer().serve() }

  def createCouchServer() = {
    VCAPServices.service(vcapKey) match {
      case Some(details) => createFromVCAP[PlayerSite] (details.head.credentials, dbName(), CFApp.host,CFApp.port, zk,
      createDb(), JourneyDaoFactory, JourneyServiceFactory)
      case None => createFromFlags[PlayerSite](JourneyDaoFactory, JourneyServiceFactory)
    }
  }
}