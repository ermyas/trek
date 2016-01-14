/*
 *   Copyright 2015 IBM Corporation
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.ibm.trek.puzzle

import com.ibm.trek.common.CouchDao
import com.ibm.trek.puzzle.spec.SpecUtils
import org.specs2.execute.{AsResult, Result}
import org.specs2.mutable.Specification
import org.specs2.specification.ForEach

class PuzzleDaoSpec extends Specification with SpecUtils with ForEach[PuzzleDao] {

  sequential

  override def foreach[R: AsResult](f: PuzzleDao => R): Result = {
    await(CouchDao.deleteDb(dbConfig))
    val dao = PuzzleDaoFactory(dbConfig).asInstanceOf[PuzzleDao]
    AsResult(f(dao))
  }

  "PuzzleDao" should {
    "Get all puzzles" in { dao: PuzzleDao =>
      val p1 = awaitRight(dao.create(fixAusJourneyPuzzle))
      val p2 = awaitRight(dao.create(fixNZJourneyPuzzle))
      val p3 = awaitRight(dao.create(fixDDayPuzzle))

      "within specified limit" in {
        val all = awaitRight(dao.getAll(limit = 3))
        all.length mustEqual 3
        all must contain(exactly(p1, p2, p3))
        val only1 = awaitRight(dao.getAll(limit = 1))
        only1.length mustEqual 1
        only1 must contain(exactly(p1))
      }

      "after a specified skip number" in {
        val allPuzzles = awaitRight(dao.getAll(skip = 2))
        allPuzzles.length mustEqual 1
        allPuzzles must contain(exactly(p3))
      }

      "within specified limit and after a specified skip number" in {
        val allPuzzles = awaitRight(dao.getAll(limit = 1, skip = 1))
        allPuzzles.length mustEqual 1
        allPuzzles must contain(exactly(p2))
      }
      ok
    }
  }
}
