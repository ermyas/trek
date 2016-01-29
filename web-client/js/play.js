var playerId = "291aca633eea6175064ad18acf000669";

//  Global vars
var puzzle = null;
var trailIndex;
var trailLength;
var puzzleId;

function startPuzzle(puzzleId, zoom, lat, lon) {
    try {
        setView(zoom, lat, lon);
        client.startPuzzle(playerId, puzzleId, puzzleInitializer);
    } catch (p) {
        console.log(p);
    }
}

window.onload = function () {
    puzzleId = getURLParameter('pid');
    var zoom = getURLParameter('z');
    var lat = getURLParameter('lt');
    var lon = getURLParameter('ln');
    startPuzzle(puzzleId, zoom, lat, lon);
};

function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [, ""])[1].replace(/\+/g, '%20')) || null
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
