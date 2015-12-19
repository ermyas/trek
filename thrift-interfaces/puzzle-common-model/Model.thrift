namespace java com.ibm.trek.model
#@namespace scala com.ibm.trek.model


struct Coordinate {
  1: double latitude,
  2: double longitude
}

struct Site {
  1: Coordinate coord
}
