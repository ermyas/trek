var puzzleInitializer = function (result) {

    trailIndex = 0;
    trailLength = result.trail.length;
    puzzle = result;

    var stage = result.trail[trailIndex];

    showModal(puzzle.startMessage);
    updateClueCard(stage.clue);
    showProgressBar();
    updateProgress();
    resetView();
    $("#continue").focus();
};

var processSuccessfulGuess = function () {
    animateGuess(true);
    var stage = puzzle.trail[trailIndex];
    trailIndex++;

    if (trailIndex == trailLength) {
        var msg = stage.message + "<br/>" + puzzle.endMessage;
        showModal(msg);
        hideClueCards();
    } else if (stage != null && trailIndex < trailLength) {
        updateView(laststage, nextstage);
    }
    updateProgress();
};

function updateProgress() {
    animateProgress(trailIndex, trailLength);
}

function showClueCards() {
    $("#clues").removeClass("hide");
}

function showProgressBar() {
    progressCircle.attr({stroke: "grey"});
    $("#progress").removeClass("hide");
}

function hideClueCards() {
    $("#clues").addClass("hide");
}

function showModal(msg) {
    $('#puzzleMessage').html(msg);
    $('#message').openModal();
}

function updateClueCard(clue) {
    $('#clue').html(clue);
    showClueCards();
}

function updateView(prev, next) {

    if (prev.message != null) {
        showModal(prev.message);
    }

    if (next != null && next.clue != null) {
        updateClueCard(next.clue);
    } else {
        hideClueCards();
    }

}

