package com.ibm.trek.puzzle.lenses

import com.ibm.trek.puzzle.{Fixtures, Implicits}
import monocle.syntax._
import org.specs2.mutable.Specification
import org.specs2.specification.AllExpectations

class LensesSpec extends Specification with Fixtures with AllExpectations with Implicits {
  "Puzzle Lens should" >> {
    "Retrieve " >> {
      "ID when _id lens is used" >> {
        (fixDDayPuzzle applyLens PuzzleL._id get) === fixDDayPuzzle.id
      }
      "startMessage when _startMessage lens is used" >> {
        (fixDDayPuzzle applyLens PuzzleL._startMessage get) === fixDDayPuzzle.startMessage
      }
      "endMessage when _endMessage lens is used" >> {
        (fixDDayPuzzle applyLens PuzzleL._endMessage get) === fixDDayPuzzle.endMessage
      }
      "owner when _owner lens is used" >> {
        (fixDDayPuzzle applyLens PuzzleL._owner get) === fixDDayPuzzle.owner
      }
    }
    "Modify " >> {
      "ID when _id lens is used" >> {
        (fixDDayPuzzle applyLens PuzzleL._id get) === fixDDayPuzzle.id
      }
      "startMessage when _startMessage lens is used" >> {
        (fixDDayPuzzle applyLens PuzzleL._startMessage get) === fixDDayPuzzle.startMessage
      }
      "endMessage when _endMessage lens is used" >> {
        (fixDDayPuzzle applyLens PuzzleL._endMessage get) === fixDDayPuzzle.endMessage
      }
      "owner when _owner lens is used" >> {
        (fixDDayPuzzle applyLens PuzzleL._owner get) === fixDDayPuzzle.owner
      }
    }
  }
}
