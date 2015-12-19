package com.ibm.trek.player

import com.ibm.couchdb.{CouchDoc, TypeMapping}
import com.ibm.trek.common.{CouchDao, DbConfig}
import com.ibm.trek.player.model.Player

object PlayerDaoFactory extends CouchDao.DaoFactory[Player] {
  val typeMapping = TypeMapping(classOf[Player.Immutable] -> "player")

  def docExtractor(doc: CouchDoc[Player]): Player = doc.doc.copy(id = Some(doc._id))

  override def apply(config: DbConfig): CouchDao[Player] = {
    val dbApi = CouchDao.getOrCreateDb(config).map(db => CouchDao.getDbApi(db, config.name, typeMapping))
    dbApi.flatMap(api => {
      CouchDao.getOrCreateDesign(api, "player-design", Map()).map[CouchDao[Player]](
      { design => new CouchDao(api, design, docExtractor) })
    }).run
  }
}
