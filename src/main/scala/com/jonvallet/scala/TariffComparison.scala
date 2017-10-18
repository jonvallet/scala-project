package com.jonvallet.scala

import scala.math.BigDecimal.RoundingMode

object TariffComparison {
  def cost(powerUsage: BigDecimal, gasUsage: BigDecimal)(tariff: Tariff): (String, BigDecimal) = {
    val powerCost: BigDecimal = tariff.rates.power match {
      case Some(powerTariff) => powerTariff * powerUsage + tariff.standing_charge
      case None => 0
    }

    val gasCost: BigDecimal = tariff.rates.gas match {
      case Some(gasTariff) => if (gasUsage == 0) 0 else gasTariff * gasUsage + tariff.standing_charge
      case None => 0
    }

    val totalCost = (powerCost + gasCost) * 1.05

    tariff.tariff -> totalCost.setScale(2, RoundingMode.HALF_EVEN)
  }

  def costs(powerUsage: BigDecimal, gasUsage: BigDecimal)(tariffs: List[Tariff]): List[(String, BigDecimal)] = {
    tariffs.map(tariff => cost(powerUsage, gasUsage)(tariff)).sortBy(_._2)
  }

//  def usage
}
