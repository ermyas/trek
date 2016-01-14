package com.ibm.trek.puzzle

import com.ibm.trek.puzzle.spec.{Fixtures, SpecUtils}
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.specification.AllExpectations

import scalaz.concurrent.Task

class PuzzleServiceSpec extends Specification with Fixtures with SpecUtils with AllExpectations with Implicits with
Mockito {
  def create(dao: PuzzleDao) = {
    new PuzzleServiceImpl(dao)
  }

  def createMockDao() = {
    val dao: PuzzleDao = mock[PuzzleDao]
    dao.create(fixDDayPuzzle) returns Task.now(fixDDayPuzzleSaved)
    dao.update(fixDDayPuzzleSaved.id.get, fixDDayPuzzleSaved) returns Task.now(fixDDayPuzzleSaved)
    dao.get(fixDDayPuzzleSaved.id.get) returns Task.now(fixDDayPuzzleSaved)
    dao.delete(fixDDayPuzzleSaved.id.get) returns Task.now(Unit)
    dao.getAll(limit = 3, skip = 2) returns Task.now(Seq(fixAusJourneyPuzzle))
    dao
  }

  "Puzzle service" should {
    val mockCouchDao = createMockDao()
    val service = create(mockCouchDao)
    val id = fixDDayPuzzleSaved.id.get
    "Creates puzzle" in {
      service.create(fixDDayPuzzle)
      there was one(mockCouchDao).create(fixDDayPuzzle)
    }
    "Get a puzzle" in {
      service.get(id)
      there was one(mockCouchDao).get(id)
    }
    "Get all puzzles" in { implicit ee: org.specs2.concurrent.ExecutionEnv =>
      service.getAll(limit = 3, skip = 2)
      there was one(mockCouchDao).getAll(limit = 3, skip = 2)
    }

    "Get a puzzle should fail when invalid id provided" in {
      awaitArgFailure(service.get(""))
    }
    "Update a puzzle" in {
      service.update(fixDDayPuzzleSaved)
      there was one(mockCouchDao).update(id, fixDDayPuzzleSaved)
    }
    "Update a puzzle should fail when invalid id provided" in {
      awaitArgFailure(service.update(fixDDayPuzzle))
      there was no(mockCouchDao).update(id, fixDDayPuzzle)
    }
    "Delete a puzzle" in {
      service.delete(id)
      there was one(mockCouchDao).delete(id)
    }
    "Delete a puzzle should fail when invalid id provided" in {
      awaitArgFailure(service.delete(""))
      there was no(mockCouchDao).delete("")
    }
  }
}