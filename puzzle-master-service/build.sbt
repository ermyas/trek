name := "puzzle-master"
version := "1.0"

mainClass in assembly := Some("com.ibm.trek.puzzle.master.PuzzleMasterServer")
assemblyJarName in assembly := "puzzle-master-service.jar"

docker <<= (docker dependsOn assembly)

imageNames in docker := Seq(ImageName(s"${organization.value}/${name.value}:latest"),
                            ImageName(s"${organization.value}/${name.value}:${version.value}"))

dockerfile in docker := {
  val artifact = (assemblyOutputPath in assembly).value
  val artifactTargetPath = s"/app/${artifact.name}"
  new Dockerfile {
    from("java")
    add(artifact, artifactTargetPath)
  }
}
