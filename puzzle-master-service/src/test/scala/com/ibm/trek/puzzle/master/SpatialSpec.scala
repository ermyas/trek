package com.ibm.trek.puzzle.master

import com.ibm.trek.model.Coordinate
import org.scalacheck.Gen
import org.scalatest.prop.PropertyChecks
import org.scalatest.{Matchers, PropSpec}

class SpatialSpec extends PropSpec with PropertyChecks with Matchers {

  val myCoordGen = for {
    lat <- Gen.choose(-90, 90)
    lng <- Gen.choose[Double](-180, 180)
  } yield Coordinate(latitude = lat, longitude = lng)

  property("Nearby coordinates are nearby") {
    forAll(myCoordGen) { coord: Coordinate =>

      val nearbySite = Coordinate(coord._1 + 0.001, coord._2 + 0.001)
      val dist = Spatial.distance(coord, nearbySite)
      val comp = dist < 1
      comp shouldBe true
    }
  }
}