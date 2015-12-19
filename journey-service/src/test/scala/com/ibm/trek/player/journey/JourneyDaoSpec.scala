package com.ibm.trek.player.journey

import com.ibm.trek.common.CouchDao
import com.ibm.trek.player.journey.spec.SpecUtils
import com.ibm.trek.player.model.PlayerSite
import org.specs2.execute.{AsResult, Result}
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.specification.ForEach
import com.ibm.couchdb._

class JourneyDaoSpec extends Specification with SpecUtils with Mockito with ForEach[JourneyDao] {

  sequential

  override def foreach[R: AsResult](f: JourneyDao => R): Result = {
    CouchDao.deleteDb(dbConfig).ignoreError.run
    AsResult(f(createDao(dbConfig)))
  }

  "Journey DAO" should {
    "create a PlayerSite" in { dao: JourneyDao =>
      val toEgypt = PlayerSite(timestamp = System.currentTimeMillis(),
                               site = greatPyramid,
                               puzzle = wondersOfTheWorld, player = phileas)
      val savedSite = awaitRight(dao.create(toEgypt))
      savedSite must beEqualTo(toEgypt)
    }

    "Retrieve player visits" in { dao: JourneyDao =>
      val createdVisits = visitSites(dao, phileas)
      val playerVists = awaitRight(dao.get(playerId = phileas, puzzleId = wondersOfTheWorld))
      playerVists.length === createdVisits.length
      playerVists must containTheSameElementsAs(createdVisits)
    }

    "Retrieve the last PlayerSite instance" in { dao: JourneyDao =>
      val playerSites = visitSites(dao, phileas)
      val lastVisit = awaitRight(dao.getLastSite(playerId = phileas, puzzleId = wondersOfTheWorld))
      lastVisit must beEqualTo(playerSites.last)
    }
  }
}
