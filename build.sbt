name := "log-analyzer"

version := "1.0"

scalaVersion := "2.12.3"

libraryDependencies ++= {
  val akkaVersion = "2.5.32"
  val akkaHttpVersion = "10.0.10"
  val scalaTestVersion = "3.0.1"
  val scalaMockV = "3.5.0"
  val slickVersion = "3.2.1"
  val akkaHttpJsonSerializersVersion = "1.34.0"
  Seq(
    "com.typesafe.akka"  %% "akka-actor"                  % akkaVersion,
    "com.typesafe.akka"  %% "akka-stream"                 % akkaVersion,

    "com.typesafe.akka"  %% "akka-http"                   % akkaHttpVersion,
    "com.typesafe.akka"  %% "akka-http-core"              % akkaHttpVersion,
    "com.typesafe.akka"  %% "akka-http-testkit"           % akkaHttpVersion % Test,

    "com.typesafe.slick" %% "slick"                       % slickVersion,
    "com.typesafe.slick" %% "slick-hikaricp"              % slickVersion,
    "org.slf4j"           % "slf4j-nop"                   % "1.7.25",
    "org.flywaydb"        % "flyway-core"                 % "3.2.1",
    "com.h2database"      % "h2"                          % "1.4.192",
    "com.lightbend.akka" %% "akka-stream-alpakka-slick"   % "2.0.2",
    "de.heikoseeberger"  %% "akka-http-jackson"           % akkaHttpJsonSerializersVersion,

    "org.scalatest"      %% "scalatest"                   % scalaTestVersion % Test,
    "org.scalamock"      %% "scalamock-scalatest-support" % scalaMockV % Test,
    "ch.megard"          %% "akka-http-cors"              % "1.1.1"
  )
}
