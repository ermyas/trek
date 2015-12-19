// Important stuff: the endpoint is: "http://hostip:hostport/thriftservicename"
// where the thrift service name is defined in your .thrift
var host = "trekpuzzlemaster.mybluemix.net";
var port = "80";
var service = "PuzzleMasterService";
var transport = new Thrift.Transport("http://" + host + ":" + port + "/" + service);
var protocol = new Thrift.TJSONProtocol(transport);
var client = new PuzzleMasterServiceClient(protocol);
var playerId = "c0dbb438541364bd416d181dff6e768e"; // this player should already exist
var puzzleId = "109ca71f0a3b2a0735ee5fb93f3046fe"; // as should this puzzle
var puzzle = null;

function startPuzzle() {
    try {
        client.startPuzzle(playerId, puzzleId, puzzleInitializer);
    } catch (p) {
        console.log(p);
    }
}

function submitGuess() {
    if (puzzle != null) {
        try {
            var guess = buildGuess();
            var targetId = puzzle.nextSiteId;
            client.submitGuess(playerId, puzzleId, guess, targetId, processGuessResult);
        } catch (p) {
            console.log(p);
        }
    }
}

function buildGuess() {
    var center = map.getCenter();
    var point = new Coordinate();
    point.latitude = center.lat;
    point.longitude = center.lng;
    var guess = new Site();
    guess.coord = point;
    return guess
}
