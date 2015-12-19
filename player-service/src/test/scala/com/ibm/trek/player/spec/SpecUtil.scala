/*
 *   Copyright 2015 IBM Corporation
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.ibm.trek.player.spec

import com.ibm.trek.common.{CouchDao, DbConfig}
import com.ibm.trek.player.model.Player
import com.ibm.trek.player.{Fixtures, PlayerDaoFactory}
import org.specs2.matcher.MatcherMacros
import org.specs2.mutable.Specification
import org.specs2.scalaz.DisjunctionMatchers

trait SpecUtil extends Specification with Fixtures with DisjunctionMatchers with MatcherMacros {

  def deleteDb(config: DbConfig) = CouchDao.deleteDb(config)

  def createDao(config: DbConfig): CouchDao[Player] = PlayerDaoFactory(config)
}
