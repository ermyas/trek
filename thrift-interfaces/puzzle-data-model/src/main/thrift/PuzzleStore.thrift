namespace java com.ibm.treasure
#@namespace java com.ibm.treasure

include "Model.thrift"
include "Exceptions.thrift"

service PuzzleStore {
    Model.Puzzle createPuzzle(Model.Puzzle puzzle);
    void deletePuzzle(i64 puzzleId) throws (1: Exceptions.PuzzleDoesNotExist e);
    Model.Puzzle getPuzzle(i64 puzzleId) throws (1: Exceptions.PuzzleDoesNotExist e);
}


