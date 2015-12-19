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

package com.ibm.trek.common

import com.ibm.trek.common.spec.{FixPerson, SpecUtils}
import org.specs2.execute.{AsResult, Result}
import org.specs2.mutable.Specification
import org.specs2.specification.ForEach

class CouchDaoSpec extends Specification with SpecUtils with ForEach[CouchDao[FixPerson]] {

  sequential

  val nonexistent = "non-existent-doc"


  override def foreach[R: AsResult](f: CouchDao[FixPerson] => R): Result = {
    CouchDao.deleteDb(dbConfig)
    val dao = createDao()
    AsResult(f(dao))
  }

  "CouchDao" should {
    "Create a document" in { dao: CouchDao[FixPerson] =>
      val fixAliceSaved: FixPerson = dao.create(fixAlice).run
      val fixBobSaved: FixPerson = dao.create(fixJill).run
      fixAliceSaved.name === fixAlice.name
      fixAliceSaved.id must beSome
      fixBobSaved.name === fixBobSaved.name
      fixBobSaved.id must beSome
    }

    "Get a document that exists" in { dao: CouchDao[FixPerson] =>
      val created = dao.create(fixAlice).run
      val fixAliceSaved: FixPerson = dao.get(created.id.get).run
      fixAliceSaved.name === fixAlice.name
      fixAlice.name === created.name
      fixAliceSaved must beEqualTo(created)
    }

    "Fail to Get a document that does not exist" in { dao: CouchDao[FixPerson] =>
      val aliceReq = dao.get(nonexistent)
      mustFail(aliceReq)
    }

    "Delete a document that exists" in { dao: CouchDao[FixPerson] =>
      val created = dao.create(fixAlice).run
      dao.delete(created.id.get).run
      val deletedObj = dao.get(created.id.get)
      mustFail(deletedObj)
    }

    "Fail to delete a document that does not exist" in { dao: CouchDao[FixPerson] =>
      val deletedDoc = dao.delete(nonexistent)
      mustFail(deletedDoc)
    }

    "Update a document" in { dao: CouchDao[FixPerson] =>
      val created = dao.create(fixAlice).run
      val updated = dao.update(id = created.id.get, created.copy(name = fixJill.name)).run

      updated.name === fixJill.name
      updated.id must beSome
      dao.get(updated.id.get).run === updated
    }

    "Fail to update a document that does not exist" in { dao: CouchDao[FixPerson] =>
      val created = dao.create(fixAlice).run
      val updated = dao.update(id = nonexistent, created.copy(name = fixJill.name))

      mustFail(updated)
    }
  }
}
