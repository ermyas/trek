namespace java com.ibm.trek.player
#@namespace scala com.ibm.trek.player

include "JourneyModel.thrift"
include "../puzzle-common-model/Exceptions.thrift"
include "../puzzle-common-model/Model.thrift"

service JourneyService {
//
//    void delete(PlayerModel.PlayerId playerId, PuzzleModel.PuzzleId puzzleId) throws (1: Exceptions.PlayerDoesNotExist e);
//
//    void deleteAll(PlayerModel.PlayerId playerId) throws (1: Exceptions.PlayerDoesNotExist e);

    JourneyModel.Journey get(Model.PlayerId playerId, Model.PuzzleId puzzleId) throws (1: Exceptions.PlayerDoesNotExist e);

    JourneyModel.PlayerSite visit(
        JourneyModel.PlayerSite playerSite
    ) throws (1: Exceptions.PlayerDoesNotExist e);
}

