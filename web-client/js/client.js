// Important stuff: the endpoint is: "http://hostip:hostport/thriftservicename"
// where the thrift service name is defined in your .thrift
//var host = "trekpuzzlemaster.mybluemix.net";
var host = "192.168.99.100";
//var port = "80";
var port = "8886";
var service = "PuzzleMasterService";
var transport = new Thrift.Transport("http://" + host + ":" + port + "/" + service);
var protocol = new Thrift.TJSONProtocol(transport);
var client = new PuzzleMasterServiceClient(protocol);
var playerId = "1d80253ea9d3bde96863a7bf270000ac"; // this player should already exist
var puzzleId = "3a219bd30893157833cad612bc000e1a"; // as should this puzzle
var puzzle = null;
var trailIndex;
var trailLength;

function startPuzzle() {
    try {
        client.startPuzzle(playerId, puzzleId, puzzleInitializer);
    } catch (p) {
        console.log(p);
    }
}

function submitGuess() {
    if (puzzle != null && trailIndex < trailLength) {
        try {
            var guess = buildGuess();
            console.log(puzzle.trail[trailIndex]);
            var stage = puzzle.trail[trailIndex];

            // For improved performance we evaluate the guess locally
            var success = evaluateGuess(guess, stage.site.coord);

            var site = (success) ? stage.site : guess;
            placeMarker(site, success);

            if (success) processSuccessfulGuess();
            else animateGuess(false);

            // For record keeping, we submit the guess to the puzzle master service
            var targetId = puzzle.trail[trailIndex].id;
            client.submitGuess(playerId, puzzleId, guess, targetId, postSubmit);
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

function evaluateGuess(guess, target) {
    return distance(guess.coord, target) < 10;
}
