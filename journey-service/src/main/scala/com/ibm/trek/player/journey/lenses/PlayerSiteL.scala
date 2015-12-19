package com.ibm.trek.player.journey.lenses

import com.ibm.trek.player.model.PlayerSite
import monocle.Lens

object PlayerSiteL {
  val _timeStamp = Lens((_: PlayerSite).timestamp)(v => o => o.copy(timestamp= v))
  val _site      = Lens((_: PlayerSite).site)(v => o => o.copy(site = v))
}
