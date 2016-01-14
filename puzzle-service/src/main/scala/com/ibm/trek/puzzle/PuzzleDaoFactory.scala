package com.ibm.trek.puzzle

import com.ibm.couchdb.CouchDoc
import com.ibm.trek.common.{CouchDao, DbConfig}
import com.ibm.trek.puzzle.model.Puzzle

object PuzzleDaoFactory extends CouchDao.DaoFactory[Puzzle] {
  def extractDoc(doc: CouchDoc[Puzzle]): Puzzle = doc.doc.copy(id = Some(doc._id))

  override def apply(config: DbConfig): CouchDao[Puzzle] = {
    val dbApi = CouchDao.getOrCreateDb(config).map(db => CouchDao.getDbApi(db, config.name, PuzzleDao.typeMapping))
    dbApi.flatMap(api => {
      CouchDao.getOrCreateDesign(api, PuzzleDao.designName, PuzzleDao.views).map[CouchDao[Puzzle]](
        { design => new PuzzleDao(api, design) })
    }).run
  }
}
