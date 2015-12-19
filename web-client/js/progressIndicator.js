
var canvasSize = 200,
    centre = canvasSize / 2,
    radius = canvasSize * 0.25,
    sn = Snap('#progress-arc'),
    path = "",
    arc = sn.path(path),
    startY = centre - radius;

var progressCircle = sn.circle(centre, centre, radius).attr({fill: "none", strokeWidth: 6, stroke: "grey"});

function run(progress, total) {
    var percent = progress / total;
    var endpoint = percent * 360;
    Snap.animate(0, endpoint, function (val) {
        arc.remove();

        var d = val,
            dr = d - 90,
            radians = Math.PI * (dr) / 180,
            endX = centre + radius * Math.cos(radians),
            endY = centre + radius * Math.sin(radians),
            largeArc = d > 180 ? 1 : 0;
        path = "M" + centre + "," + startY + " A" + radius + "," + radius + " 0 " + largeArc + ",1 " + endX + "," + endY;

        arc = sn.path(path);
        arc.attr({
            stroke: '#3da08d',
            fill: 'none',
            strokeWidth: 6
        });

        //$('#percent').html(Math.round(val / 360 * 100) + '%');
        $('#percent').html(progress + "/" + total);


    }, 2000, mina.easeinout);
    if (percent == 1) {
        progressCircle.attr({stroke: "#3da08d"});
    }
}


