package com.ibm.trek.player.journey.spec

import com.ibm.trek.common.DbConfig
import com.ibm.trek.model.{Coordinate, Site}

trait Fixtures {
  val dbConfig = DbConfig(host = "localhost", 5984, "journeydb-spec", https = false, None, None)

  val wondersOfTheWorld = "Wonders of the ancient world"
  val phileas           = "Phileas Fogg"

  // The Great Pyramid at Giza, Nazlet El-Semman, Al Haram, Giza Governorate, Egypt
  val greatPyramid = Site(coord = Coordinate(latitude = 29.979169, longitude = 31.134052))
  // Hanging Gardens of Babylon
  val hangingGarden = Site(coord = Coordinate(latitude = 32.543510, longitude = 44.423962))
  //Temple of Artemis at Ephesus
  val templeOfArtemis = Site(coord = Coordinate(latitude = 37.951139, longitude = 27.365371))
  val fixSites    = Seq(greatPyramid, hangingGarden, templeOfArtemis)

}
