namespace java com.ibm.trek.puzzle.master
#@namespace scala com.ibm.trek.puzzle.master

include "PuzzleMasterModel.thrift"
include "puzzle-common-model/Model.thrift"
include "puzzle-common-model/Exceptions.thrift"
include "puzzle-service-interface/PuzzleModel.thrift"

service PuzzleMasterService {

    PuzzleMasterModel.PuzzleResponse submitGuess(
      1: PuzzleMasterModel.PlayerId playerId, 
      2: PuzzleMasterModel.PuzzleId puzzleId, 
      3: Model.Site siteGuess, 
      4: PuzzleModel.SiteId targetPuzzleSiteId) throws (1: Exceptions.PuzzleDoesNotExist e);

    PuzzleMasterModel.PuzzleResponse startPuzzle(
      1: PuzzleMasterModel.PlayerId playerId, 
      2: PuzzleMasterModel.PuzzleId puzzleId) throws (1: Exceptions.PuzzleDoesNotExist e);
}
