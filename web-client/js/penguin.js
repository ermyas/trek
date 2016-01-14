var s = Snap("#penguin");

var penguin_leftfoot = s.ellipse(1.4,3.9,0.6,0.2).attr({fill: "orange", strokeWidth: 0.0});
var penguin_rightfoot = s.ellipse(2.6,3.9,0.6,0.2).attr({fill: "orange", strokeWidth: 0.0});

var penguin_head = s.ellipse(2,0.7,1.05,0.7).attr({fill: "#5fc2af", strokeWidth: 0.0});
var penguin_body = s.ellipse(2,2.65,1.45,1.4).attr({fill: "#5fc2af", strokeWidth: 0.0});
var penguin_belly = s.ellipse(2,2.7,1.05,1.3).attr({fill: "white", strokeWidth: 0.0});

var penguin_beak = s.polygon( 1.3,.7, 2.0, 1.35, 2.7,.7 ).attr({fill: "orange", strokeWidth: 0.0});

var penguin_lefteye = s.ellipse(1.55,0.65,0.48,0.48).attr({fill: "white", strokeWidth: 0.0});
var penguin_leftpupil = s.ellipse(1.58,0.75,0.24,0.25).attr({fill: "black", strokeWidth: 0.0});

var penguin_righteye = s.ellipse(2.45,0.65,0.48,0.48).attr({fill: "white", strokeWidth: 0.0});
var penguin_leftpupil = s.ellipse(2.42,0.75,0.24,0.25).attr({fill: "black", strokeWidth: 0.0});




var m = Snap("#horizon");


var mount_a = m.polygon(
    0.0,0.95,
    0.0,0.7,
    0.3,0.6,
    0.7,0.2,
    1.0,0.14,
    1.3,0.2,
    1.7,0.32,
    2.0,0.1,
    2.3,0.0,
    2.5,0.02,
    2.8,0.1,
    3.3,0.1,
    3.7,0.2,
    4.0,0.37,
    4.0,1.0
).attr({fill: "#81d4fa", strokeWidth: 0.0});

var mount_b = m.polygon(
    0.0,1.0,
    0.0,0.8,
    0.3,0.7,
    0.7,0.3,
    1.0,0.2,
    1.3,0.3,
    1.7,0.35,
    2.0,0.2,
    2.3,0.0,
    2.7,0.1,
    3.0,0.3,
    3.3,0.2,
    3.7,0.3,
    4.0,0.4,
    4.0,1.0
).attr({fill: "#01579b", strokeWidth: 0.0});
