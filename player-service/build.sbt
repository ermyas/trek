name := "player-service"
version := "1.0"

mainClass in assembly := Some("com.ibm.trek.player.PlayerServer")
assemblyJarName in assembly := "player-service.jar"