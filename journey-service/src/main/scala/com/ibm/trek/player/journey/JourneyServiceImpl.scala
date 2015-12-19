package com.ibm.trek.player.journey

import com.ibm.trek.player.JourneyService
import com.ibm.trek.player.model._
import com.twitter.util.Future

import scala.language.implicitConversions

class JourneyServiceImpl(dao: JourneyDao) extends JourneyService.FutureIface {
  override def get(playerId: String, puzzleId: String): Future[Seq[PlayerSite]] = dao.get(playerId, puzzleId).toFuture

  override def visit(playerSite: PlayerSite): Future[PlayerSite] = dao.create(playerSite).toFuture
}
