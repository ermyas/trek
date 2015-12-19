package com.ibm.trek.player.journey

import com.ibm.couchdb.{TypeMapping, CouchDbApi, CouchDesign}
import com.ibm.trek.common.CouchDao
import com.ibm.trek.player.model.PlayerSite

import scalaz.concurrent.Task

class JourneyDao(db: CouchDbApi, couchDesign: CouchDesign)
  extends CouchDao[PlayerSite](db, couchDesign, CouchDao.simpleDocExtractor[PlayerSite]) {

  def get(playerId: String, puzzleId: String, desc: Boolean = true): Task[Seq[PlayerSite]] = {
    println(s"retrieving Journey for Player:$playerId, Puzzle:$puzzleId")
    db.query.view[(String, String), String](couchDesign.name, JourneyViews.byPlayerAndPuzzle).get
      .descending(desc).key((playerId, puzzleId)).queryIncludeDocs[PlayerSite].map(_.getDocs.map(docExtractor))
  }

  def getLastSite(playerId: String, puzzleId: String): Task[PlayerSite] = {
    get(playerId, puzzleId, desc = true).map(x => x.head)
  }
}

object JourneyDao {
  val typeTags = TypeMapping(classOf[PlayerSite.Immutable] -> JourneyViews.playerSiteMapping)
  val designName = "journeydesign"
}
