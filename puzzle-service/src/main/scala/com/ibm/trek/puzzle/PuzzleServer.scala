package com.ibm.trek.puzzle

import com.ibm.trek.common.Server
import com.ibm.trek.common.cf.{CFApp, VCAPServices}
import com.ibm.trek.puzzle.model.Puzzle

import scala.language.implicitConversions

object PuzzleServer extends Server {
  val vcapKey = "cloudantNoSQLDB"

  def main(): Unit = { createCouchServer().serve() }

  def createCouchServer() = {
    VCAPServices.service(vcapKey) match {
      case Some(details) => createFromVCAP[Puzzle] (details.head.credentials, dbName(), CFApp.host,
        CFApp.port, zk, createDb(),PuzzleDaoFactory, PuzzleServiceFactory)
      case None => createFromFlags[Puzzle](PuzzleDaoFactory, PuzzleServiceFactory)
    }
  }
}
