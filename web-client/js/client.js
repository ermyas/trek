// Important stuff: the endpoint is: "http://hostip:hostport/thriftservicename"
// where the thrift service name is defined in your .thrift
var settings= {
    "host": "192.168.99.100",
    "port": "8886",
    "service" : "PuzzleMasterService"
};

var playerId = "291aca633eea6175064ad18acf000669";
var puzzleId = "a11d5988fcc359c7cd8095a492000c68";

var transport = new Thrift.Transport("http://" + settings.host + ":" + settings.port + "/" + settings.service);
var protocol = new Thrift.TJSONProtocol(transport);
var client = new PuzzleMasterServiceClient(protocol);

//  Global vars
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
            var stage = puzzle.trail[trailIndex];

            // For record keeping, we submit the guess to the puzzle master service
            client.submitGuess(playerId, puzzleId, guess, stage.id, postSubmit);

            // For improved performance we evaluate the guess locally
            var success = evaluateGuess(guess, stage.site.coord);

            var site = (success) ? stage.site : guess;
            placeMarker(site, success, stage.name);

            if (success) processSuccessfulGuess();
            else animateGuess(false);

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
