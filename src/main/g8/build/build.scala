import cbt._

class TestBuild(val context: Context) extends BaseBuild {
  override def projectDirectory = context.workingDirectory / "test"
  override def dependencies = (
    super.dependencies ++ // don't forget super.dependencies here for scala-library, etc.
    Seq(new Build(context)) // link the main project      
  )
}

class Build(val context: Context) extends BaseBuild {
  override def test = new TestBuild(context) // by default CBT assumes that test buildfile is `.\test\build\build.scala`
  override def dependencies = (
    super.dependencies ++ // don't forget super.dependencies here for scala-library, etc.
    Seq(
      // source dependency
      // DirectoryDependency( projectDirectory ++ "/subProject" )
    ) ++
    // pick resolvers explicitly for individual dependencies (and their transitive dependencies)
    Resolver(mavenCentral, sonatypeReleases).bind(
      // CBT-style Scala dependencies
      // ScalaDependency( "com.lihaoyi", "ammonite-ops", "0.5.5" )
      // MavenDependency( "com.lihaoyi", "ammonite-ops_2.11", "0.5.5" )

      // SBT-style dependencies
      // "com.lihaoyi" %% "ammonite-ops" % "0.5.5"
      // "com.lihaoyi" % "ammonite-ops_2.11" % "0.5.5"
    )
  )
}
