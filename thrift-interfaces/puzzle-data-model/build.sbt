import sbt.Keys._

com.twitter.scrooge.ScroogeSBT.newSettings

name := "TreasureHuntDataModel"

version := "1.0"

scalaVersion := "2.11.6"

scalacOptions ++= Seq("-Yrangepos", "-deprecation", "-unchecked", "-feature", "-language:higherKinds", "-language:postfixOps")

scalacOptions in Test ++= Seq("-Yrangepos")

libraryDependencies ++= Seq(
  "org.apache.thrift" % "libthrift" % "0.9.2",
  "com.twitter" %% "scrooge-core" % "3.17.0",
  "com.twitter" %% "finagle-thrift" % "6.24.1-MONOCACHE"
)
