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

package com.ibm.trek.common.cf

object VCAPServices {
  private lazy val env = Option(System.getenv("VCAP_SERVICES")).map(upickle.default.read[Map[String, Seq[CFService]]])

  def defined = env.isDefined

  def services(): Option[Map[String, Seq[CFService]]] = env

  def service(s: String): Option[Seq[CFService]] = services().flatMap(x=>x.get(s))

}

object CFApp {
  def port = Integer.parseInt(System.getenv("PORT"))

  def host = "0.0.0.0"
}
case class CFService(name: String, label: String,
                     tags: Array[String] = Array[String](), plan: String,
                     credentials: Credentials)

case class Credentials(host: String = "", username: String = "", password: String = "", port: Int)

