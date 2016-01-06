package com.ibm.trek.puzzle.lenses

import com.ibm.trek.puzzle.model.Puzzle
import monocle.Lens

object PuzzleL {
  val _id           = Lens((_: Puzzle).id)(v => o => o.copy(id = v))
  val _startMessage = Lens((_: Puzzle).startMessage)(v => o => o.copy(startMessage = v))
  val _endMessage   = Lens((_: Puzzle).endMessage)(v => o => o.copy(endMessage = v))
  val _owner        = Lens((_: Puzzle).owner)(v => o => o.copy(owner = v))
  val _trail        = Lens((_: Puzzle).trail)(v => o => o.copy(trail = v))
  val _startCoord   = Lens((_: Puzzle).startCoord)(v => o => o.copy(startCoord = v))
  val _startZoom    = Lens((_: Puzzle).startZoom)(v => o => o.copy(startZoom = v))
}