package com.ibm.trek.player

import com.ibm.trek.common.CouchDao
import com.ibm.trek.player.model.{Player, PlayerSite}
import com.twitter.util.Future

import scala.language.implicitConversions

class PlayerServiceImpl(dao: CouchDao[Player], journeyService: JourneyService[Future])
  extends PlayerService.FutureIface {

  override def remove(id: String): Future[Unit] = {
    dao.delete(id).toFuture
  }

  override def create(player: Player): Future[Player] = {
    dao.create(player).toFuture
  }

  override def get(id: String): Future[Player] = {
    dao.get(id).toFuture
  }

  override def getJourney(playerId: String, puzzleId: String): Future[Seq[PlayerSite]] = {
    journeyService.get(playerId, puzzleId)
  }

  override def visit(playerSite: PlayerSite): Future[PlayerSite] = {
    journeyService.visit(playerSite)
  }
}
