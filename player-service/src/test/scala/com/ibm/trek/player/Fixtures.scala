package com.ibm.trek.player

import com.ibm.trek.common.DbConfig
import com.ibm.trek.model.{Coordinate, Site}
import com.ibm.trek.player.model.{Player, PlayerSite}
import com.twitter.util.Future
import org.specs2.mock.Mockito

trait Fixtures extends Mockito {
  val fixPlayers     = Seq(
    Player(name = "Philip Fry", id = None, puzzles = None),
    Player(name = "Bender Rodriguez,", id = None, puzzles = None),
    Player(name = "Hubert Farnsworth", id = Some("001"), puzzles = None)
  )
  val fixPuzzleId    = "Around the World"
  val fixSitePyramid = Site(coord = Coordinate(latitude = 29.979169, longitude = 31.134052))
  val fixPlayerSites = Seq(PlayerSite(System.currentTimeMillis(), site = fixSitePyramid,
                                      puzzle = fixPuzzleId, player = fixPlayers.head.name))

  val dbConfig         = DbConfig(host = "localhost", port = 5984, name = "trek-common-test", https = false)
  val nonexistent      = "non-existent-player"
  val fixJourneyClient = {
    val mockClient = mock[JourneyService[Future]]
    mockClient.get(anyString, anyString) returns Future.value(fixPlayerSites)
    mockClient.visit(fixPlayerSites.head) returns Future.value(fixPlayerSites.head)
    mockClient
  }
}
