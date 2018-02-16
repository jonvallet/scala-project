package com.jonvallet.scala

import com.jonvallet.scala.quote.{InsufficientAvailableAmount, Loan, Quote}
import org.scalatest._

class QuoteCalculationTest extends FlatSpec with Matchers {

  val loans = List(
    Loan(   "Bob",   0.075, 640, 1000),
    Loan(  "Jane",   0.069, 480, 1000),
    Loan(  "Fred",   0.071, 520, 1000),
    Loan(  "Mary",   0.104, 170, 1000),
    Loan(  "John",   0.081, 320, 1000),
    Loan(  "Dave",   0.074, 140, 1000),
    Loan("Angela",   0.071,  60, 1000),
  )

  "The Quote calculation" should "calculate the rate" in {
    assertResult(0.069) {
      Quote.rate(loans, 400)
    }
  }

  it should "fail if total available amount is not enough" in {
    assertThrows[InsufficientAvailableAmount] {
      Quote.rate(loans, 100000)
    }
  }

  it should "calculate the rate if several loans are needed" in {
    assertResult(0.070) {
      Quote.rate(loans, 1000)
    }
  }

  it should "calculate the monthly repayment" in {
    assertResult(30.88) {
      Quote.monthlyRepayment(1000, 0.070, 36)
    }
  }

}
