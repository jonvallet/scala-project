name := "Alerts"

version := "0.1"

scalaVersion := "2.12.4"

assemblyOption in assembly := (assemblyOption in assembly).value.copy(prependShellScript = Some(Seq("#!/usr/bin/env sh", """exec java -jar "$0" "$@"""" + "\n")))

assemblyJarName in assembly := "alerts"

libraryDependencies += "io.reactivex" %% "rxscala" % "0.26.5"

libraryDependencies += "io.reactivex" % "rxjava" % "1.3.4"

libraryDependencies += "io.spray" %%  "spray-json" % "1.3.4"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"

