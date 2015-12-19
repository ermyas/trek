namespace java com.ibm.trek.player.model
#@namespace scala com.ibm.trek.player.model

include "../puzzle-common-model/Model.thrift"

# A location that a user has been to
struct PlayerSite {
  1: i64 timestamp,
  2: Model.Site site
  3: Model.PlayerId player
  4: Model.PuzzleId puzzle
}

typedef list<PlayerSite> Journey
