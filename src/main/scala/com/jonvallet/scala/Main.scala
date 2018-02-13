package com.jonvallet.scala

import scala.io.Source

object Main {

  val HELP: String =
    "Usage: [options]\n\n" +
    "   <MARKET_FILE> <LOAN_AMOUNT>  for the given market offers, output the lowest rate, with the monthly and total repayment amount.\n" +
    "                                Ex. $./quote market.csv 1000"

  def main(args: Array[String]): Unit = {

    args.toList match {
      case arg1 :: arg2 :: _  => ???
      case _ => println(HELP)
    }
  }

}
