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
import upickle.Js
import upickle.default._
trait uPickleTypes {
  type R[T] = Reader[T]
  type W[T] = Writer[T]

  def jsObj(fields: (String, Js.Value)*): Js.Obj = {
    Js.Obj(fields.filter(_._2 != Js.Null): _*)
  }

  def writeRequired[T: W](field: T): Js.Value = {
    writeJs[T](field)
  }

  def writeOptional[T: W](field: Option[T]): Js.Value = {
    field.map(writeJs[T]).getOrElse(Js.Null)
  }

  def readRequired[T: R](fields: Map[String, Js.Value], name: String): T = {
    readJs[T](fields(name))
  }

  def readOptional[T: R](fields: Map[String, Js.Value], name: String): Option[T] = {
    fields.get(name).map(readJs[T])
  }
}
