package com.ibm.trek.puzzle.master

import com.ibm.trek.model.Coordinate

object Spatial {

  def distance(p1: Coordinate, p2: Coordinate) = {

    val R = 6371 // Radius of the earth in km
    val dLat = deg2rad(p2.latitude - p1.latitude) // deg2rad below

    val dLon = deg2rad(p2.longitude - p1.longitude)
    val a =
      Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(deg2rad(p1.latitude)) * Math.cos(deg2rad(p2.latitude)) *
          Math.sin(dLon / 2) * Math.sin(dLon / 2)

    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
    val dist = R * c // Distance in km
    println(s"Distance between two coords: $dist km")
    dist
  }

  def deg2rad(deg: Double) = deg * (Math.PI / 180)
}
