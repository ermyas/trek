package com.ibm.trek.puzzle.master

import com.ibm.trek.model.{Coordinate, Site}
import com.ibm.trek.player.model.{Player, PlayerSite}
import com.ibm.trek.puzzle.model.{Puzzle, PuzzleSite}

trait Fixtures {

  val now = System.currentTimeMillis

  val firstSite = PuzzleSite(id = "melb", name = "Melbourne", message = "here be dragons", clue = "fight fire with fire", site = Site(Coordinate(0.0, 1.1)))
  val secondSite = PuzzleSite(id = "syd", name = "Sydney", message = "here be knights", clue = "the pen it mighty", site = Site(Coordinate(5.0, 3.1)))
  val lastSite = PuzzleSite(id = "adelaide", name = "Adelaide", message = "go where sinks", clue = "no more drink", site = Site(Coordinate(2.0, 3.1)))
  val puzzleId = "cap'n crunch"
  val playerId = "johan-id"
  val puzzle = Puzzle(Some(puzzleId), Seq(firstSite, secondSite, lastSite), "Hey there goose", "nice one!", "joey")
  val player = Player("johan", Some(playerId))
  val playerSite = PlayerSite(now, Site(Coordinate(1.0, 2.0)), player = player.id.get, puzzle = puzzle.id.get)
//  val journey = Seq(playerSite)
  val journey = playerSite

  val badGuess = Site(Coordinate(-1.0, -1.0))
  val goodGuess = Site(Coordinate(0.0, 1.1))
}