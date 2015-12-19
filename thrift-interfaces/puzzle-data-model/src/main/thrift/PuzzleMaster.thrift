namespace java com.ibm.treasure
#@namespace java com.ibm.treasure

include "Model.thrift"


service PuzzleMaster {
    Model.PuzzleResponse submitGuess(Model.SiteGuess guess);
    Model.PuzzleResponse startPuzzle(i64 puzzleId, Model.PlayerId playerId, optional Model.SiteId siteId);
}
