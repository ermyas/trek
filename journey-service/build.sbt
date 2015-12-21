name := "journey-service"
version := "1.2"

mainClass in assembly := Some("com.ibm.trek.player.journey.JourneyServer")
assemblyJarName in assembly := "journey-service.jar"

docker <<= (docker dependsOn assembly)

imageNames in docker := Seq(ImageName(s"${organization.value}/${name.value}:latest"),
                            ImageName(s"${organization.value}/${name.value}:v${version.value}"))

dockerfile in docker := {
  val artifact = (assemblyOutputPath in assembly).value
  val artifactTargetPath = s"/app/${artifact.name}"
  new Dockerfile {
    from("java")
    add(artifact, artifactTargetPath)
    entryPoint("java", "-jar", artifactTargetPath)
  }
}
