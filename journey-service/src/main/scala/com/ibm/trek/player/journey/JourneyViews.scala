package com.ibm.trek.player.journey

import com.ibm.couchdb._

object JourneyViews {
  val playerSiteMapping = "playersite"
  val byPlayerAndPuzzle = "byPlayerAndPuzzle"

  val views = Map(
    byPlayerAndPuzzle -> CouchView(map =
      s"""
         |function(doc) {
         |var d = doc.doc
         |if(doc.kind == "$playerSiteMapping")
         |emit([d.player, d.puzzle], d.player);
         |}
      """.stripMargin)
  )
}