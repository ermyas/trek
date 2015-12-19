package com.ibm.trek.player

import com.ibm.trek.common.uPickleTypes
import com.ibm.trek.player.model.{Player, PuzzleState}
import upickle.Js
import upickle.default.Aliases._

trait Implicits extends uPickleTypes {
  implicit def PlayerRW: RW[Player] = RW[Player]({
    x => jsObj(
      ("id", writeOptional(x.id)),
      ("name", writeRequired(x.name))
    )
  }, {
    case json: Js.Obj =>
      val f = json.value.toMap
      Player(
        id = readOptional[String](f, "id"),
        name = readRequired[String](f, "name")
      )
  })

  implicit def PuzzleStateRW: RW[PuzzleState] = RW[PuzzleState]({
    x => jsObj(
      ("value", writeRequired(x.value)))
  }, {
    case json: Js.Obj =>
      val f = json.value.toMap
      PuzzleState(
        readRequired[Int](f, "value")
      )
  })
}