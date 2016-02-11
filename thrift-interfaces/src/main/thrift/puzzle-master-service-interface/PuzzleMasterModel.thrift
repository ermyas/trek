namespace java com.ibm.trek.puzzle.master.model
#@namespace scala com.ibm.trek.puzzle.master.model

include "puzzle-common-model/Model.thrift"

struct Progress {
  1: byte currentStage,
  2: byte totalStages
}

struct PuzzleResponse {
  1: Model.PlayerId playerId,
  2: Model.PuzzleId puzzleId,
  3: optional Model.SiteId nextSiteId,
  4: optional string nextSiteClue,
  5: optional string message,
  6: optional bool correctLastGuess,
  7: optional Progress progress
}


