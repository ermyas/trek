package com.ibm.trek.common

import _root_.com.ibm.couchdb.CouchDoc
import com.ibm.trek.common.spec.FixPerson

trait Fixtures extends uPickleTypes {

  def personExtractor = { x: CouchDoc[FixPerson] => x.doc.copy(id = Some(x._id)) }


  val fixAlice = FixPerson("Alice", None)
  val fixJill  = FixPerson("Bob", None)

  val couchDbHost     = System.getProperty("couchDbHost", "127.0.0.1")
  val couchDbPort     = System.getProperty("couchDbPort", "5984").toInt
  val couchDbUsername = System.getProperty("couchDbUsername", null)
  val couchDbPassword = System.getProperty("couchDbPassword", null)

  val dbConfig = DbConfig(host = couchDbHost, port = couchDbPort, name = "trek-common-test", username =
    Option(couchDbUsername), password = Option(couchDbPassword), https = false)
}
