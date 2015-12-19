package com.ibm.trek.player.journey.spec

import com.ibm.trek.common.{CouchDao, DbConfig}
import com.ibm.trek.player.journey.{Implicits, JourneyDao, JourneyViews}
import com.ibm.trek.player.model.PlayerSite
import org.specs2.matcher._
import org.specs2.mutable.Specification
import org.specs2.scalaz.DisjunctionMatchers

import scalaz._
import scalaz.concurrent.Task

trait SpecUtils extends Specification with Implicits with

                        Fixtures with
                        DisjunctionMatchers with
                        MatcherMacros {

  def await[T](future: Task[T]): Throwable \/ T = future.attemptRun

  def awaitRight[T](future: Task[T]): T = {
    val res = await(future)
    res must beRightDisjunction
    res.toOption.get
  }

  def deleteDb(config: DbConfig) = CouchDao.deleteDb(config)

  def createDao(config: DbConfig): JourneyDao = {
    val db = CouchDao.getOrCreateDb(config).run
    val dbApi = CouchDao.getDbApi(db, config.name, JourneyDao.typeTags)
    val design = CouchDao.getOrCreateDesign(dbApi, JourneyDao.designName, JourneyViews.views).run
    new JourneyDao(dbApi, design)
  }

  def visitSites(dao: JourneyDao, player: String) = {
    val sites = Seq(greatPyramid, hangingGarden, templeOfArtemis)
    for {
      site <- sites
      playerSite = awaitRight(dao.create(PlayerSite(timestamp = System.currentTimeMillis(),
                                                    site = site,
                                                    puzzle = wondersOfTheWorld, player = phileas)))
    } yield playerSite
  }
}
