package com.ibm.trek.puzzle.lenses

import com.ibm.trek.puzzle.model.PuzzleSite
import monocle.Lens

object SiteL {
  val _id = Lens((_: PuzzleSite).id)(v => o => o.copy(id = v))
  val _name = Lens((_: PuzzleSite).name)(v => o => o.copy(name = v))
  val _message = Lens((_: PuzzleSite).message)(v => o => o.copy(message = v))
  val _clue = Lens((_: PuzzleSite).clue)(v => o => o.copy(clue = v))
  val _site = Lens((_: PuzzleSite).site)(v => o => o.copy(site = v))
}
