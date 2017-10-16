package com.jonvallet.scala

import org.scalatest.FunSuite

class HelloWorldTest extends FunSuite {

  test("Hello World should return `Hello World!`") {
    assert(HelloWord() == "Hello World!")
  }

}
