name := "REPS"
version := "0.1"
scalaVersion := "2.13.12"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.16" % Test,
  "com.typesafe.play" %% "play-json" % "2.9.4" // For JSON handling
)