enablePlugins(ScalaJSPlugin)

name := "canvas-ui"
version := "0.2.0-SNAPSHOT"
scalaVersion := "3.2.0"
libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "2.3.0"
)
