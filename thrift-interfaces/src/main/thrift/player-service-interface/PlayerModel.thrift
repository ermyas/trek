namespace java com.ibm.trek.player.model
#@namespace scala com.ibm.trek.player.model

include "../puzzle-common-model/Model.thrift"

enum PuzzleState {
  STARTED = 1,
  COMPLETED  = 2,
  FAILED = 3,
  ABANDONED = 4
}

struct Player {
  1: string name,
  2: optional Model.PlayerId id,
  3: optional map<Model.PuzzleId, PuzzleState> puzzles
}

