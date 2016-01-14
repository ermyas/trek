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
      val puzzle1 = awaitRight(dao.create(fixDDayPuzzle))
      val puzzle2 = awaitRight(dao.create(fixAusJourneyPuzzle))

      val saved1 = awaitRight(dao.get(puzzle1.id.get))
      val saved2 = awaitRight(dao.get(puzzle2.id.get))

      saved1 mustEqual puzzle1
      saved2 mustEqual puzzle2
      val allPuzzles = awaitRight(dao.getAll())
      allPuzzles.length mustEqual 2
      allPuzzles must contain(puzzle1, puzzle2)
    }
  }
}
