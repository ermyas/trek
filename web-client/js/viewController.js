
var puzzleInitializer = function (result) {
    puzzle = result;
    updateView(result);
    showClueCards();
    showProgressBar();
    updateProgress(result);
    resetView();
};

var processGuessResult = function (result) {
    puzzle = result;
    updateView(result);
    animateGuess(result.correctLastGuess);
    updateProgress(result);
};

function updateProgress(puzzleResponse) {
    var p = puzzleResponse.progress;
    if (p != null) {
        run(p.currentStage, p.totalStages);
    }
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

function updateView(p) {
    if (p.message != null) {
        $('#puzzleMessage').html(p.message);
        $('#message').openModal();
    }
    if (p.nextSiteClue != null) {
        $('#clue').html(p.nextSiteClue);
    } else {
        hideClueCards();
    }
}

