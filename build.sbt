name := "challengeBackEnd"

version := "0.1"

scalaVersion := "2.12.4"
lazy val versions = new {
  val finatra = "19.10.0"
  val guice = "4.2.2"
  val logback = "1.2.3"
}

libraryDependencies ++= Seq(
  "com.softwaremill.sttp.client" %% "core" % "2.0.0-RC1",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "com.typesafe.slick" %% "slick" % "3.2.1",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.1",
  "org.scalatest" %% "scalatest"   % "3.0.1"   % "test",
  "org.mockito"   % "mockito-core" % "1.10.19" % "test",
  "com.twitter"                  %% "finatra-http"                    % versions.finatra,
  "com.twitter"                  %% "finatra-httpclient"              % versions.finatra,
  "com.twitter"                  %% "finatra-jackson"                 % versions.finatra,
  "ch.qos.logback"               % "logback-classic"                  % versions.logback,
  "com.twitter"                  %% "twitter-server-logback-classic"  % versions.finatra,
  "com.twitter"                  %% "inject-server"                   % versions.finatra % "test",
  "com.twitter"                  %% "inject-app"                      % versions.finatra % "test",
  "com.twitter"                  %% "inject-core"                     % versions.finatra % "test",
  "com.twitter"                  %% "inject-modules"                  % versions.finatra % "test",
  "com.google.inject.extensions" % "guice-testlib"                    % versions.guice   % "test")

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}