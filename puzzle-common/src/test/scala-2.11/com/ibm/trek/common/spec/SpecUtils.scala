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

package com.ibm.trek.common.spec

import com.ibm.couchdb.TypeMapping
import com.ibm.trek.common.{CouchDao, DbConfig, Fixtures}
import org.specs2.matcher.MatcherMacros
import org.specs2.mutable.Specification
import org.specs2.scalaz.DisjunctionMatchers
import upickle.Js
import upickle.default.Aliases._
import upickle.default._
import scalaz.\/
import scalaz.concurrent.Task

trait SpecUtils extends Specification with Fixtures with DisjunctionMatchers with MatcherMacros {

  def await[T](future: Task[T]): Throwable \/ T = future.attemptRun

  def mustFail[T](t: Task[T]) = await(t).isLeft === true
  
  def awaitRight[T](future: Task[T]): T = {
    val res = await(future)
    res must beRightDisjunction
    res.toOption.get
  }

  def deleteDb(config: DbConfig) = CouchDao.deleteDb(config)

  implicit def FixPersonRW: RW[FixPerson] = RW[FixPerson](
    {x => jsObj(("name", writeRequired[String](x.name)),("id", writeOptional[String](x.id)))},
    {
    case json: Js.Obj => val f = json.value.toMap
    FixPerson(name = readRequired[String](f, "name"),id = readOptional[String](f, "id"))
    })

  def createDao(): CouchDao[FixPerson] = {
    val db = CouchDao.getOrCreateDb(dbConfig).run
    val dbApi = CouchDao.getDbApi(db, "trek-common-test", TypeMapping(classOf[FixPerson] -> "person"))
    val design = CouchDao.getOrCreateDesign(dbApi, "couchdao-test-design", Map()).run
    new CouchDao[FixPerson](dbApi, design, docExtractor = personExtractor)
  }
}
