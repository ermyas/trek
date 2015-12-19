namespace java com.ibm.trek.puzzle.master
#@namespace scala com.ibm.trek.puzzle.master

include "PuzzleMasterModel.thrift"
include "../puzzle-common-model/Model.thrift"
include "../puzzle-common-model/Exceptions.thrift"

service PuzzleMasterService {

    PuzzleMasterModel.PuzzleResponse submitGuess(
      1: Model.PlayerId playerId, 
      2: Model.PuzzleId puzzleId, 
      3: Model.Site siteGuess, 
      4: Model.SiteId targetPuzzleSiteId) throws (1: Exceptions.PuzzleDoesNotExist e);

    PuzzleMasterModel.PuzzleResponse startPuzzle(
      1: Model.PlayerId playerId, 
      2: Model.PuzzleId puzzleId) throws (1: Exceptions.PuzzleDoesNotExist e);
}
