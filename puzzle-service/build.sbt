name := "puzzle-service"
version := "1.0"

mainClass in assembly := Some("com.ibm.trek.puzzle.PuzzleServer")
assemblyJarName in assembly := "puzzle-service.jar"

docker <<= (docker dependsOn assembly)

dockerfile in docker := {
  val artifact = (assemblyOutputPath in assembly).value
  val artifactTargetPath = s"/app/${artifact.name}"
  new Dockerfile {
    from("java")
    add(artifact, artifactTargetPath)
    entryPoint("java", "-jar", artifactTargetPath)
  }
}