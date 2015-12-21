import sbt.Keys._
import sbt._
com.twitter.scrooge.ScroogeSBT.newSettings

lazy val finagleDeps = Seq(
                             "com.twitter" %% "finagle-thrift" % "6.29.0",
                             "com.twitter" %% "finagle-serversets" % "6.29.0",
                             "com.twitter" %% "finagle-http" % "6.28.0",
                             "com.twitter" %% "twitter-server" % "1.14.0"
                          ).map(_.excludeAll(ExclusionRule("org.apache.thrift"), ExclusionRule("org.slf4j")))

lazy val testDeps = Seq(
  "org.specs2" %% "specs2-core" % "3.6.4" % "test",
  "org.typelevel" %% "scalaz-specs2" % "0.3.0" % "test",
  "org.scalacheck" %% "scalacheck" % "1.12.1" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % "test",
  "org.scalaz" %% "scalaz-scalacheck-binding" % "7.1.0" % "test",
  "ch.qos.logback" % "logback-classic" % "1.1.2" % "test")

lazy val commonDeps = Seq(
                           "com.ibm" %% "couchdb-scala" % "0.6.0",
                           "org.apache.thrift" % "libthrift" % "0.9.2",
                           "ch.qos.logback" % "logback-classic" % "1.1.2") ++ finagleDeps ++ testDeps

lazy val commonSettings = Seq(
                               organization := "com.ibm.trek",
                               parallelExecution in Test := false,
                               scalaVersion := "2.11.7",
                               sbtVersion := "0.13.8",
                               scalacOptions ++= Seq("-Yrangepos", "-deprecation", "-unchecked", "-feature",
                                                     "-language:higherKinds", "-language:postfixOps"),
                               scalacOptions in Test ++= Seq("-Yrangepos"),
                               libraryDependencies ++= commonDeps,
                               resolvers += "twitter-repo" at "https://maven.twttr.com",
                               assemblyMergeStrategy in assembly := {
                                 case "com/twitter/common/args/apt/cmdline.arg.info.txt.1" => MergeStrategy.first
                                 case x =>
                                   val oldStrategy = (assemblyMergeStrategy in assembly).value
                                   oldStrategy(x)
                               }
                             )

// Projects
lazy val thriftInterfaces = project.in(file("thrift-interfaces")).settings(commonSettings: _*)

lazy val common = project.in(file("puzzle-common")).settings(commonSettings: _*).dependsOn(thriftInterfaces)

lazy val puzzleService = project.in(file("puzzle-service")).settings(commonSettings: _*).
                         dependsOn(common, thriftInterfaces)
                         .enablePlugins(DockerPlugin)

lazy val journeyService= project.in(file("journey-service")).settings(commonSettings: _*).
                         dependsOn(common, thriftInterfaces)
                         .enablePlugins(DockerPlugin)

lazy val playerService = project.in(file("player-service")).settings(commonSettings: _*).
                         dependsOn(common, thriftInterfaces)
                         .enablePlugins(DockerPlugin)

lazy val puzzleMasterService = project.in(file("puzzle-master-service")).settings(commonSettings: _*).
                               dependsOn(common, thriftInterfaces)
                               .enablePlugins(DockerPlugin)

