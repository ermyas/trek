namespace java com.ibm.treasure.datamodel
#@namespace scala com.ibm.treasure.datamodel

typedef string PlayerId
typedef i64 PuzzleId
typedef i64 SiteId

struct Coordinate {
  1: double latitude,
  2: double longitude,
}

struct Site {
  1: SiteId id,
  2: string name,
  3: string message,
  4: string clue,
  5: Coordinate location
}

struct VisitedSite {
  1: i64 timestamp,
  2: Coordinate location,
  3: optional Site site
}

typedef list<Site> Trail
typedef list<VisitedSite> Journey

struct Puzzle {
  1: i64 id,
  2: Trail trail,
  3: string startMessage,
  4: string endMessage,
  5: PlayerId owner
}

struct SiteGuess {
  1: optional PlayerId playerId,
  2: PuzzleId puzzleId,
  3: i64 nextSiteId,
  4: Coordinate guess
}

struct PuzzleResponse {
  1: PlayerId playerId,
  2: PuzzleId puzzleId,
  3: optional string nextSiteClue,
  4: Journey journey
}

struct Hint {
  1: string message
}

struct Player {
  1: PlayerId playerId,
  2: map<PuzzleId, Journey> history
}

