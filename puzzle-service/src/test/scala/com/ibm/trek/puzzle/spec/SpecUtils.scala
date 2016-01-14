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

package com.ibm.trek.puzzle.spec

import com.ibm.trek.common.{CouchDao, DbConfig}
import com.ibm.trek.puzzle.PuzzleDaoFactory
import com.ibm.trek.puzzle.model.Puzzle
import com.twitter.util.{Await, Awaitable}
import org.slf4j.LoggerFactory
import org.specs2.matcher.MatcherMacros
import org.specs2.mutable.Specification
import org.specs2.scalaz.DisjunctionMatchers

import scalaz.concurrent.Task
import scalaz.{-\/, \/, \/-}

trait SpecUtils extends Specification with Fixtures with DisjunctionMatchers with MatcherMacros {

  private val log = LoggerFactory.getLogger(getClass.getName)

  def await[T](future: Task[T]): Throwable \/ T = future.attemptRun

  def mustFail[T](t: Task[T]) = await(t).isLeft === true

  def awaitRight[T](future: Task[T]): T = {
    val res = await(future)
    val docs = res match {
      case -\/(err) =>
        log.error(err.getMessage, err)
        None
      case \/-(d) =>
        Some(d)
    }
    res must beRightDisjunction
    docs.get
  }

  def deleteDb(config: DbConfig) = CouchDao.deleteDb(config)

  def createDao(): CouchDao[Puzzle] = {
    PuzzleDaoFactory(dbConfig)
  }

  def awaitArgFailure[T](f: Awaitable[T]) = Await.result(f) must throwAn[IllegalArgumentException]
}
