namespace java com.ibm.trek.puzzle.master.model
#@namespace scala com.ibm.trek.puzzle.master.model

include "puzzle-common-model/Model.thrift"

typedef string PlayerId
typedef string PuzzleId
typedef string SiteId

struct Progress {
  1: byte currentStage,
  2: byte totalStages
}

struct PuzzleResponse {
  1: PlayerId playerId,
  2: PuzzleId puzzleId,
  3: optional SiteId nextSiteId,
  4: optional string nextSiteClue,
  5: optional string message,
  6: optional bool correctLastGuess,
  7: optional Progress progress
}


