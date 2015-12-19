package com.ibm.trek.player.journey

import com.ibm.trek.common.{CouchDao, DbConfig}
import com.ibm.trek.player.model.PlayerSite

object JourneyDaoFactory extends CouchDao.DaoFactory[PlayerSite] {
  override def apply(config: DbConfig): CouchDao[PlayerSite] = {
    val dbApi = CouchDao.getOrCreateDb(config).map(db => CouchDao.getDbApi(db, config.name, JourneyDao.typeTags))
    dbApi.flatMap(api => {
      CouchDao.getOrCreateDesign(api, JourneyDao.designName, JourneyViews.views).map[CouchDao[PlayerSite]](
      { design => new JourneyDao(api, design) })
    }).run
  }
}
