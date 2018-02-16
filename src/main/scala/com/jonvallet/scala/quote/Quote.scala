package com.jonvallet.scala.quote

import scala.math.BigDecimal.RoundingMode

case class Loan(lender: String, rate: BigDecimal, available: BigDecimal, margin: BigDecimal)

class InsufficientAvailableAmount extends Exception

object Quote {
  def rate(loans: List[Loan], loan: BigDecimal): BigDecimal = {

    var availableLoans = loans.sortBy(_.rate).map(l => if (l.available < l.margin) (l.rate, l.available) else (l.rate, l.margin))
    var remainLoan = loan
    var usedLoans: List[(BigDecimal, BigDecimal)] = Nil
    while (remainLoan > 0) {
      availableLoans match {
        case head :: tail if remainLoan <= head._2 =>
          usedLoans = (head._1, remainLoan) :: usedLoans
          remainLoan = 0
          availableLoans = tail
        case head :: tail =>
          usedLoans = head :: usedLoans
          remainLoan = remainLoan - head._2
          availableLoans = tail
        case _ => throw new InsufficientAvailableAmount
      }
    }

    usedLoans.map(e => e._2 * e._1 / loan).sum.setScale(3, RoundingMode.HALF_EVEN)
  }

  def monthlyRepayment(loan: BigDecimal, rate: BigDecimal, months: Int): BigDecimal = {
    val monthlyRate = rate / 12

    val monthlyRepayment = (loan * monthlyRate) / (1 - scala.math.pow(1 + monthlyRate.toDouble, -months))
    monthlyRepayment.setScale(2, RoundingMode.HALF_EVEN)
  }
}

