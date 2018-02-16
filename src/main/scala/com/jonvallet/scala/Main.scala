package com.jonvallet.scala

import com.jonvallet.scala.quote.{InsufficientAvailableAmount, Loan, Quote}

import scala.io.Source

object Main {

  val HELP: String =
    "Usage: [options]\n\n" +
    "   <MARKET_FILE> <LOAN_AMOUNT>  for the given market offers, output the lowest rate, with the monthly and total repayment amount.\n" +
    "                                Ex. $./quote market.csv 1000"

  def calculateQuote(file: String, amount: String): Unit = {

    val lines = Source.fromFile(file).getLines().toList.tail
    val loans = lines.map(_.split(",")).map(line => Loan(line(0), BigDecimal(line(1)), BigDecimal(line(2)), 100000))
    try {
      val loanAmount = BigDecimal(amount)
      val rate = Quote.rate(loans, loanAmount)
      val monthlyRepayment = Quote.monthlyRepayment(loanAmount, rate, 36)
      println(s"Requested Amount: £$loanAmount")
      println(f"Rate: ${rate * 100}%2.1f"+"%")
      println(s"Monthly repayment: £$monthlyRepayment")
      println(s"Total repayment: £${monthlyRepayment*36}")

    } catch {
      case _: InsufficientAvailableAmount => println("Is not possible to provide a quote at this time")
    }

  }

  def main(args: Array[String]): Unit = {

    args.toList match {
      case file :: amount :: _  => calculateQuote(file, amount)
      case _ => println(HELP)
    }
  }

}
