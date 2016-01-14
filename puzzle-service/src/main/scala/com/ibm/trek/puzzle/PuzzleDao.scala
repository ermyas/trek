package com.ibm.trek.puzzle

import com.ibm.couchdb._
import com.ibm.trek.common.CouchDao
import com.ibm.trek.puzzle.model.Puzzle

import scalaz.concurrent.Task

class PuzzleDao(db: CouchDbApi, couchDesign: CouchDesign)
  extends CouchDao[Puzzle](db, couchDesign, PuzzleDao.extractDoc) {

  def getAll(limit: Int = 10, skip: Int = 0): Task[Seq[Puzzle]] = {
    db.query.view[String, Puzzle](couchDesign.name, PuzzleDao.byOwner).get.skip(skip).
    limit(limit).query.map[Seq[Puzzle]](_.rows.map(doc => doc.value.copy(id = Some(doc.key))))
  }
}

object PuzzleDao {
  val puzzleDocType = "puzzle"
  val typeMapping   = TypeMapping(classOf[Puzzle.Immutable] -> puzzleDocType)
  val designName    = "puzzle-design"
  val byOwner       = "byowner"

  val views = Map(byOwner -> CouchView(map =
                                         s"""
                                            |function(doc) {
                                            |var d = doc.doc
                                            |if(doc.kind == "$puzzleDocType")
                                            |emit(doc._id, d);
                                            |}
      """.stripMargin))

  def extractDoc(doc: CouchDoc[Puzzle]): Puzzle = doc.doc.copy(id = Some(doc._id))
}
