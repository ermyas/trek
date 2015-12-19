package com.ibm.trek.common

import _root_.com.ibm.couchdb.CouchDoc
import com.ibm.trek.common.spec.FixPerson

trait Fixtures extends uPickleTypes {

  def personExtractor = { x: CouchDoc[FixPerson] => x.doc.copy(id = Some(x._id)) }


  val fixAlice = FixPerson("Alice", None)
  val fixJill  = FixPerson("Bob", None)
  val dbConfig = DbConfig(host = "localhost", port = 5984, name = "trek-common-test", https = false)
}
