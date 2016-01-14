namespace java com.ibm.trek.puzzle
#@namespace scala com.ibm.trek.puzzle

include "../puzzle-common-model/Model.thrift"
include "../puzzle-common-model/Exceptions.thrift"
include "PuzzleModel.thrift"

service PuzzleService {
    void delete(Model.PuzzleId puzzleId) throws (1: Exceptions.PuzzleDoesNotExist e);

    PuzzleModel.Puzzle create(PuzzleModel.Puzzle puzzle);

    PuzzleModel.Puzzle update(PuzzleModel.Puzzle puzzle);

    PuzzleModel.Puzzle get(Model.PuzzleId puzzleId) throws (1: Exceptions.PuzzleDoesNotExist e);

    list<PuzzleModel.Puzzle> getAll(i32 limit, i32 skip) throws (1: Exceptions.PuzzleDoesNotExist e);

}

