package com.ibm.trek.puzzle.spec

import com.ibm.trek.model.{Coordinate, Site}
import com.ibm.trek.puzzle.model.{Puzzle, PuzzleSite}
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification

trait Fixtures extends Specification with Mockito {

  val fixSiteCotentin = Site(coord = Coordinate(latitude = 49.457051, longitude = -1.502296))

  val fixSiteUtahBeach = PuzzleSite(id = "utah-beach", name = "Utah Beach", clue = "The American Zones of the D-Day Landing",
    message = Some("The American Zones of the D-Day Landing on June 6th 1944"), site = fixSiteCotentin)

  val fixDDayPuzzle = Puzzle(
    trail = Seq[PuzzleSite](fixSiteUtahBeach),
    startMessage = "Welcome to the D-Day landing puzzle!",
    endMessage = "Great job completing the D-Day landing puzzle.",
    owner = "Eisenhower")

  val fixDDayPuzzleSaved = Puzzle(
    id = Some("puzzle-dday"),
    trail = Seq[PuzzleSite](fixSiteUtahBeach),
    startMessage = "Welcome to the D-Day landing puzzle!",
    endMessage = "Great job completing the D-Day landing puzzle.",
    owner = "Eisenhower")

  def awaitArgFailure[T](f: Awaitable[T]) = Await.result(f) must throwAn[IllegalArgumentException]

  val dbConfig = DbConfig(host = couchDbHost, port = couchDbPort, name = "trek-common-test", username =
    Option(couchDbUsername), password = Option(couchDbPassword), https = false)
}
