package com.ibm.trek.player.journey

import com.ibm.trek.common.uPickleTypes
import com.ibm.trek.model.{Coordinate, Site}
import com.ibm.trek.player.model._
import upickle.Js
import upickle.default.Aliases._

trait Implicits extends uPickleTypes{

  implicit def PlayerSiteRW: RW[PlayerSite] = RW[PlayerSite]({
    x => jsObj(
      ("timestamp", writeRequired(x.timestamp)),
      ("puzzle", writeRequired(x.puzzle)),
      ("player", writeRequired(x.player)),
      ("site", writeRequired(x.site))
    )
  }, {
    case json: Js.Obj =>
      val f = json.value.toMap
      PlayerSite(
        timestamp = readRequired[Long](f, "timestamp"),
        puzzle = readRequired[String](f, "puzzle"),
        player = readRequired[String](f, "player"),
        site = readRequired[Site](f, "site")
      )
  })

  implicit def SiteRW: RW[Site] = RW[Site]({
    x => jsObj(("coord", writeRequired(x.coord)))
  }, {
    case json: Js.Obj =>
      val f = json.value.toMap
      Site(
        coord = readRequired[Coordinate](f, "coord")
      )
  })

  implicit def CoordinateRW: RW[Coordinate] = RW[Coordinate]({
    x => jsObj(
      ("latitude", writeRequired(x.latitude)),
      ("longitude", writeRequired(x.longitude))
    )
  }, {
    case json: Js.Obj =>
      val f = json.value.toMap
      Coordinate(
        latitude = readRequired[Double](f, "latitude"),
        longitude = readRequired[Double](f, "longitude")
      )
  })
}
