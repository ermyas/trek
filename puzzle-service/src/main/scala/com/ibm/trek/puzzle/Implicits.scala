package com.ibm.trek.puzzle

import com.ibm.trek.common.uPickleTypes
import com.ibm.trek.model.{Coordinate, Site}
import com.ibm.trek.puzzle.model.{Puzzle, PuzzleSite}
import upickle.Js
import upickle.default.Aliases._

trait Implicits extends uPickleTypes {
  implicit def SiteRW: RW[Site] = RW[Site]({
    x => jsObj(
      ("coord", writeRequired(x.coord))
    )
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

  implicit def PuzzleSiteRW: RW[PuzzleSite] = RW[PuzzleSite]({
    x => jsObj(
      ("id", writeRequired(x.id)),
      ("name", writeRequired(x.name)),
      ("clue", writeRequired(x.clue)),
      ("message", writeRequired(x.message)),
      ("site", writeRequired(x.site))
    )
  }, {
    case json: Js.Obj =>
      val f = json.value.toMap
      PuzzleSite(
        id = readRequired[String](f, "id"),
        name = readRequired[String](f, "name"),
        clue = readRequired[String](f, "clue"),
        message = readOptional[String](f, "message"),
        site = readRequired[Site](f, "site")
      )
  })

  implicit def PuzzleRW: RW[Puzzle] = RW[Puzzle]({
    x => jsObj(
      ("id", writeOptional(x.id)),
      ("startMessage", writeRequired(x.startMessage)),
      ("endMessage", writeRequired(x.endMessage)),
      ("owner", writeRequired(x.owner)),
      ("trail", writeRequired(x.trail)),
      ("startCoord", writeOptional(x.startCoord)),
      ("startZoom", writeOptional(x.startZoom))
    )
  }, {
    case json: Js.Obj =>
      val f = json.value.toMap
      Puzzle(
        id = readOptional[String](f, "id"),
        startMessage = readRequired[String](f, "startMessage"),
        endMessage = readRequired[String](f, "endMessage"),
        owner = readRequired[String](f, "owner"),
        trail = readRequired[Seq[PuzzleSite]](f, "trail"),
        startCoord = readOptional[Coordinate](f, "startCoord"),
        startZoom = readOptional[Int](f, "startZoom")
      )
  })
}
