package com.jonvallet.scala

import org.scalatest.{FlatSpec, FunSuite, Matchers}

class HelloWorldTest extends FlatSpec with Matchers {

  "Hello World" should "return `Hello World!`" in {
    assertResult ("Hello World!") {HelloWord()}
  }

}
