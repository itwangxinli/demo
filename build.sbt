name := "demo"

version := "1.0"

lazy val `demo` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(jdbc, ehcache, ws,
  specs2 % Test, guice,
  "org.pac4j" %% "play-pac4j" % "6.0.1",
  "org.pac4j" % "pac4j-http" % "3.1.0"

)


      