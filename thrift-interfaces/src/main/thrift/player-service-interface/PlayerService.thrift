namespace java com.ibm.trek.player
#@namespace scala com.ibm.trek.player

include "PlayerModel.thrift"
include "../puzzle-common-model/Exceptions.thrift"
include "../puzzle-common-model/Model.thrift"
include "../journey-service-interface/JourneyModel.thrift"


service PlayerService {

    void remove(Model.PlayerId playerId) throws (1: Exceptions.PlayerDoesNotExist e);

    PlayerModel.Player create(PlayerModel.Player player);

    PlayerModel.Player get(Model.PlayerId playerId) throws (1: Exceptions.PlayerDoesNotExist e);

    JourneyModel.Journey getJourney(
        1: Model.PlayerId playerId, 
        2: Model.PuzzleId puzzleId
    ) throws (1: Exceptions.PlayerDoesNotExist e);

    JourneyModel.PlayerSite visit(
        1: JourneyModel.PlayerSite playerSite
    ) throws (1: Exceptions.PlayerDoesNotExist e);
}

