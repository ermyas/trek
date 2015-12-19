// SVG image overlay setup and animation

var s = Snap("#eye");
var bigCircle = s.circle(2, 2, 2.5).attr({fill: "none", strokeWidth: 1.5, stroke: "black", opacity: 0.5});
var giantCircle = s.circle(2, 2, 5.4).attr({fill: "none", strokeWidth: 7, stroke: "black"});

// Cross-hair
s.circle(2, 2, 0.1).attr({fill: "none", strokeWidth: 0.01, stroke: "black", opacity: 0.9});
s.line(2, 0, 2, 4).attr({strokeWidth: 0.01, stroke: "black"});
s.line(0, 2, 4, 2).attr({strokeWidth: 0.01, stroke: "black"});

function animateGuess(correctGuess) {
    if (correctGuess) {
        bigCircle.animate({stroke: "#69f0ae"}, 100, null, function () {
            restore();
        });
    } else {
        bigCircle.animate({stroke: "#f44336"}, 100, null, function () {
            restore();
        });
    }
}

function restore() {
    bigCircle.animate({stroke: "black"}, 500, null);
}


