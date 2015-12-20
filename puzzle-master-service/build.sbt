name := "puzzle-master"
version := "1.0"

mainClass in assembly := Some("com.ibm.trek.puzzle.master.PuzzleMasterServer")
assemblyJarName in assembly := "puzzle-master-service.jar"
