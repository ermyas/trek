namespace java com.ibm.trek.puzzle.model
#@namespace scala com.ibm.trek.puzzle.model

include "../puzzle-common-model/Model.thrift"

struct PuzzleSite {
  1: Model.SiteId id,
  2: string name,
  3: string message,
  4: string clue,
  5: Model.Site site
}

typedef list<PuzzleSite> Trail

struct Puzzle {
  1: optional Model.PuzzleId id,
  2: Trail trail,
  3: string startMessage,
  4: string endMessage,
  5: Model.PlayerId owner,
  6: optional Model.Coordinate startCoord,
  7: optional i32 startZoom
}
