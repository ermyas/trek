var australia = {lat: -25.8, lon: 134.0};

L.mapbox.accessToken = 'pk.eyJ1IjoicGtnZSIsImEiOiJkNWZlZDA5MzQwNjUxODE5MDZjZDdiODZiM2E2OGQ2NCJ9.BbJdbQcJRJc4Pj7Jy_BaZQ';
var map = L.mapbox.map('map', 'pkge.n40j2pkk', {doubleClickZoom: false}).setView(australia, 4).on('dblclick', function (e) {
    // Zoom exactly to each double-clicked point
    map.setView(e.latlng, map.getZoom() + 1);
});
L.AwesomeMarkers.Icon.prototype.options.prefix = 'fa';

function setView(zoom, lat, lon) {
    var loc = {lat: lat, lon: lon};
    console.log(loc)
    map.setView(loc, zoom);
}

function resetView(result) {

    var loc = (result.startCoord == null) ? australia : {lat: result.startCoord.latitude, lon: result.startCoord.longitude};
    var zoom = (result.startZoom == null) ? 4 : result.startZoom;

    map.setView(loc, zoom);
}

$(document).keyup(function (e) {
    //console.log(e);

    var delta = (e.shiftKey) ? 0.2 : 1;

    if (e.which == 32) { // space
        submitGuess();
    }
    if (e.which == 65 || e.which == 73) { // a or i
        zoom();
    }
    if (e.which == 90 || e.which == 79) { // z or o, or escape (27)
        zoom(-1);
    }
    if (e.which == 76) {
        pan(delta, 0);
    }
    if (e.which == 72) {
        pan(-delta, 0);
    }
    if (e.which == 74) {
        pan(0, delta);
    }
    if (e.which == 75) {
        pan(0, -delta);
    }
});

function zoom(delta) {
    delta = typeof delta !== 'undefined' ? delta : 1;
    var z = map.getZoom() + delta;
    map.setZoom(z);
}

function pan(dx, dy) {
    dx = typeof dx !== 'undefined' ? dx : 1;
    dy = typeof dy !== 'undefined' ? dy : 1;
    map.panBy([dx * 80, dy * 80]);
}


var failureMarker = L.AwesomeMarkers.icon({
    icon: 'map-signs',
    markerColor: 'lightred'
});

var successMarker = L.AwesomeMarkers.icon({
    icon: 'camera',
    markerColor: 'cadetblue',
    labelAnchor: [15, -24]
});

function placeMarker(site, success, label) {
    var coords = [site.coord.latitude, site.coord.longitude];
    if (success) L.marker(coords, {icon: successMarker}).bindLabel(label, { noHide: false}).addTo(map);
    else L.marker(coords, {icon: failureMarker}).addTo(map);
}
