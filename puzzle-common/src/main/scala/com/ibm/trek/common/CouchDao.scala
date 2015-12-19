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

package com.ibm.trek.common

import com.ibm.couchdb.Res.DocOk
import com.ibm.couchdb._

import scala.language.implicitConversions
import scalaz.concurrent.Task
import scalaz.{-\/, \/-}

class CouchDao[T: upickle.default.ReadWriter](val dbApi: CouchDbApi, val design: CouchDesign,
                                      val docExtractor: CouchDoc[T] => T) extends Dao[String, T] {

  implicit def toBoolean(resp: Task[DocOk]): Task[Unit] =
    resp.attemptRun match {
      case -\/(e) => Task.fail(e)
      case \/-(v) => Task.now(())
    }

  override def delete(id: String): Task[Unit] =
    get(id, dbApi, docExtractor).flatMap(x => dbApi.docs.delete(x))

  override def create(t: T): Task[T] = create(dbApi, t)

  override def get(id: String): Task[T] = get(id, dbApi, docExtractor).map(_.doc)

  override def update(id: String, updated: T): Task[T] = get(id, dbApi, docExtractor).flatMap(puzzleDoc => {
    dbApi.docs.update[T](puzzleDoc.copy(doc = updated)).flatMap(x => get(x.id))
  })

  private def get(id: String, db: CouchDbApi, extractor: CouchDoc[T] => T): Task[CouchDoc[T]] =
    db.docs.get[T](id).map({ x => x.copy(doc = extractor(x)) })

  private def create(db: CouchDbApi, t: T): Task[T] = db.docs.create[T](t).flatMap(
    z => get(z.id, db, docExtractor).map(puzzle => docExtractor(puzzle)))
}

object CouchDao {

  type DaoFactory[T] = DbConfig => CouchDao[T]

  def simpleDocExtractor[T: upickle.default.ReadWriter]: (CouchDoc[T]) => T = _.doc

  def getDbApi(db: CouchDb, dbName: String, typeMapping: TypeMapping): CouchDbApi =
    db.db(dbName, typeMapping)

  private def initDb(config: DbConfig) = {
    config.username match {
      case Some(username) if config.password.isDefined =>
        CouchDb(config.host, config.port, config.https, username, config.password.get)
      case _ => CouchDb(config.host, config.port, config.https)
    }
  }

  def getDb(config: DbConfig): Task[CouchDb] = {
    val db = initDb(config)
    db.dbs.get(config.name).map(resp => db)
  }

  def createDb(config: DbConfig): Task[CouchDb] = {
    val db = initDb(config)
    db.dbs.create(config.name).map(resp => db)
  }

  def getOrCreateDb(config: DbConfig): Task[CouchDb] = {
    getDb(config) or createDb(config)
  }

  def deleteDb(config: DbConfig): Task[Unit] = {
    initDb(config).dbs.delete(config.name).map[Unit](resp => ())
  }

  def getOrCreateDesign(dbApi: CouchDbApi, name: String, views: Map[String, CouchView]): Task[CouchDesign] = {
    val d = CouchDesign(name, views = views)
    getDesign(dbApi, name).attemptRun match {
      case -\/(e) => dbApi.design.create(d).map[CouchDesign](res => d)
      case \/-(r) => Task.now(r)
    }
  }

  def getDesign(dbApi: CouchDbApi, name: String): Task[CouchDesign] = dbApi.design.get(name)
}
