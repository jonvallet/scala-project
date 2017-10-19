name := "Tariff"

version := "0.1"

scalaVersion := "2.12.3"


import sbtassembly.AssemblyPlugin.defaultShellScript

assemblyOption in assembly := (assemblyOption in assembly).value.copy(prependShellScript = Some(Seq("#!/usr/bin/env sh", """exec java -jar "$0" "$@"""" + "\n")))

assemblyJarName in assembly := "tariff"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"
libraryDependencies += "io.spray" %%  "spray-json" % "1.3.3"
