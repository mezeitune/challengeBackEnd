import sbt._

resolvers ++= Seq(
  "bintray-sbt-plugins" at "https://dl.bintray.com/eed3si9n/sbt-plugins/",
  Opts.resolver.sonatypeReleases
)


addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.9")
