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

package com.ibm.trek.common.ops

import com.twitter.util.{Future, Promise}
import scalaz.concurrent.Task
import scalaz.{-\/, \/-}

trait TaskOps {
  implicit class TaskWrapper[A](task: Task[A]) {
    def toFuture: Future[A] = {
      val promise = new Promise[A]
      task runAsync {
        case -\/(e) => promise.setException(e)
        case \/-(a) => promise.setValue(a)
      }
      promise
    }
  }
}
