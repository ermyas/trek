package com.ibm.trek.player

import java.util.logging.Logger

import com.ibm.couchdb.CouchException
import com.ibm.trek.common.CouchDao
import com.ibm.trek.player.model.Player
import com.ibm.trek.player.spec.SpecUtil
import com.twitter.util.Await
import org.specs2.execute.{AsResult, Result}
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.specification.ForEach

class PlayerServiceSpec extends Specification with SpecUtil with Mockito with ForEach[PlayerServiceImpl] {
  sequential

  private val log = Logger.getLogger(getClass.getName)
  def createService(dao: CouchDao[Player]) = new PlayerServiceImpl(dao, fixJourneyClient)

  override def foreach[R](f: (PlayerServiceImpl) => R)(implicit evidence$3: AsResult[R]): Result = {
    log.info("Creating service...")
    val service = createService(createDao(dbConfig))
    AsResult(f(service))
  }

  "PlayerService" should {
    "Create a Player" in { service: PlayerServiceImpl =>
      val created = Await.result(service.create(fixPlayers.head))
      created.id must beSome
      created must beEqualTo(fixPlayers.head.copy(id = created.id))
    }

    "Get a Player" in { service: PlayerServiceImpl =>
      val player = Await.result(service.create(fixPlayers.head).flatMap(x => service.get(x.id.get)))
      player must beEqualTo(fixPlayers.head.copy(id = player.id))
    }

    "Fail to get a Player if does not exist" in { service: PlayerServiceImpl =>
      Await.result(service.get(nonexistent)) must throwAn[CouchException[Player]]
    }

    "Delete a Player" in { service: PlayerServiceImpl =>
      val created = Await.result(service.create(fixPlayers.head))
      Await.result(service.remove(created.id.get))
      Await.result(service.get(created.id.get)) must throwAn[CouchException[Player]]
    }

    "Fail to delete a Player if not exist" in { service: PlayerServiceImpl =>
      Await.result(service.remove(nonexistent)) must throwAn[CouchException[Player]]
    }

    "Get a Player Site" in { service: PlayerServiceImpl =>
      service.getJourney(nonexistent, fixPuzzleId)
      there was one(fixJourneyClient).get(nonexistent, fixPuzzleId)
    }

    "Visit a Player Site" in { service: PlayerServiceImpl =>
      service.visit(fixPlayerSites.head)
      there was one(fixJourneyClient).visit(fixPlayerSites.head)
    }
  }
}
