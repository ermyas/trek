package com.ibm.trek.puzzle

import com.ibm.couchdb.{CouchDoc, TypeMapping}
import com.ibm.trek.common.{CouchDao, DbConfig}
import com.ibm.trek.puzzle.model.Puzzle

object PuzzleDaoFactory extends CouchDao.DaoFactory[Puzzle] {
  def extractDoc(doc: CouchDoc[Puzzle]): Puzzle = doc.doc.copy(id = Some(doc._id))

  override def apply(config: DbConfig): CouchDao[Puzzle] = {
    val typeMapping = TypeMapping(classOf[Puzzle.Immutable] -> "puzzle")
    val dbApi = CouchDao.getOrCreateDb(config).map(db => CouchDao.getDbApi(db, config.name, typeMapping))
    dbApi.flatMap(api => {
      CouchDao.getOrCreateDesign(api, "puzzle-design", Map()).map[CouchDao[Puzzle]](
      { design => new CouchDao(api, design, extractDoc) })
    }).run
  }
}
