namespace java com.ibm.trek.puzzle.model
#@namespace scala com.ibm.trek.puzzle.model

include "../puzzle-common-model/Model.thrift"

typedef string PuzzleId
typedef string SiteId
typedef string PlayerId

struct PuzzleSite {
  1: SiteId id,
  2: string name,
  3: string message,
  4: string clue,
  5: Model.Site site
}

typedef list<PuzzleSite> Trail

struct Puzzle {
  1: optional PuzzleId id,
  2: Trail trail,
  3: string startMessage,
  4: string endMessage,
  5: PlayerId owner
}
