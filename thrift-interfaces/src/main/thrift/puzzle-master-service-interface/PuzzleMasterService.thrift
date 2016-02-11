namespace java com.ibm.trek.puzzle.master
#@namespace scala com.ibm.trek.puzzle.master

include "PuzzleMasterModel.thrift"
include "puzzle-common-model/Model.thrift"
include "puzzle-common-model/Exceptions.thrift"
include "puzzle-service-interface/PuzzleModel.thrift"

service PuzzleMasterService {

    PuzzleMasterModel.PuzzleResponse submitGuess(
      1: Model.PlayerId playerId, 
      2: Model.PuzzleId puzzleId, 
      3: Model.Site siteGuess, 
      4: Model.SiteId targetPuzzleSiteId) throws (1: Exceptions.PuzzleDoesNotExist e);

    PuzzleModel.Puzzle startPuzzle(
      1: Model.PlayerId playerId, 
      2: Model.PuzzleId puzzleId) throws (1: Exceptions.PuzzleDoesNotExist e);

    list<PuzzleModel.Puzzle> getPuzzleList(
      1: optional i32 limit,
      2: optional i32 skip);
}
