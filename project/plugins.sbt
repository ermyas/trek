logLevel := Level.Warn
resolvers += "twitter-repo" at "https://maven.twttr.com"
addSbtPlugin("com.twitter" %% "scrooge-sbt-plugin" % "4.1.0")
addSbtPlugin("se.marcuslonnberg" % "sbt-docker" % "1.2.0")
