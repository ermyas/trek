

function distance(p1, p2) {

    var R = 6371; // Radius of the earth in km
    var dLat = deg2rad(p2.latitude - p1.latitude); // deg2rad below

    var dLon = deg2rad(p2.longitude - p1.longitude);
    var a =
      Math.sin(dLat / 2) * Math.sin(dLat / 2) +
      Math.cos(deg2rad(p1.latitude)) * Math.cos(deg2rad(p2.latitude)) *
      Math.sin(dLon / 2) * Math.sin(dLon / 2);

    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c; // Distance in km
}

function deg2rad(angleInDegrees) {
  return angleInDegrees * Math.PI / 180;
}
