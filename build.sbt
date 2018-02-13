name := "Quote"

version := "0.1"

scalaVersion := "2.12.3"

assemblyOption in assembly := (assemblyOption in assembly).value.copy(prependShellScript = Some(Seq("#!/usr/bin/env sh", """exec java -jar "$0" "$@"""" + "\n")))

assemblyJarName in assembly := "quote"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"
