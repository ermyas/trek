package com.ibm.trek.puzzle

import com.ibm.trek.puzzle.model.Puzzle
import com.twitter.scrooge.ThriftService
import com.twitter.util.Future
import org.slf4j.LoggerFactory

import scala.language.implicitConversions

class PuzzleServiceImpl(dao: PuzzleDao) extends PuzzleService.FutureIface {
  val log = LoggerFactory.getLogger(getClass.getName)

  override def delete(puzzleId: String): Future[Unit] = {
    log.info(s"request: delete puzzle: $puzzleId")
    if (puzzleId.trim.isEmpty)
      Future.exception(new IllegalArgumentException("Delete Puzzle requires a valid puzzle id"))
    else
      dao.delete(puzzleId).toFuture
  }

  override def create(puzzle: Puzzle): Future[Puzzle] = {
    log.info(s"request: create puzzle: $puzzle")
    dao.create(puzzle).toFuture
  }

  def getAll(limit: Int = 10, skip: Int = 0): Future[Seq[Puzzle]] = {
    log.info(s"request: list all puzzles: limit=$limit, skip=$skip")
    dao.getAll(limit, skip).toFuture
  }

  override def update(puzzle: Puzzle): Future[Puzzle] = {
    log.info(s"request: update puzzle: puzzle")
    puzzle.id match {
      case None => Future.exception(new IllegalArgumentException("Update Puzzle requires a valid puzzle id"))
      case Some(id) => dao.update(id, puzzle).toFuture
    }
  }

  override def get(puzzleId: String): Future[Puzzle] = {
    log.info(s"request: get puzzle: $puzzleId")
    if (puzzleId.trim.isEmpty)
      Future.exception(new IllegalArgumentException("Get Puzzle requires a valid puzzle id"))
    else
      dao.get(puzzleId).toFuture
  }
}

object PuzzleServiceImpl {
  def create(dao: PuzzleDao): ThriftService = {
    new PuzzleServiceImpl(dao)
  }
}
