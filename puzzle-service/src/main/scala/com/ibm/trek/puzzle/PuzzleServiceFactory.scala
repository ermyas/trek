package com.ibm.trek.puzzle

import com.ibm.trek.common.{CouchDao, CouchService, ServiceFactory}
import com.ibm.trek.puzzle.PuzzleService.FinagledService
import com.ibm.trek.puzzle.model.Puzzle
import org.apache.thrift.protocol.TBinaryProtocol.Factory

object PuzzleServiceFactory extends ServiceFactory[Puzzle] {
  override def apply(dao: CouchDao[Puzzle]): CouchService = {
    dao match {
      case puzzleDao: PuzzleDao =>
        val service = new PuzzleServiceImpl(dao = puzzleDao)
        new FinagledService(service, new Factory())
    }
  }
}

