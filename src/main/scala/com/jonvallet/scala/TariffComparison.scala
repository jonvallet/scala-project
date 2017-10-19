package com.jonvallet.scala

import scala.math.BigDecimal.RoundingMode

object TariffComparison {
  def cost(powerUsage: BigDecimal, gasUsage: BigDecimal)(tariff: Tariff): (String, BigDecimal) = {
    val powerCost: BigDecimal = tariff.rates.power match {
      case Some(powerTariff) if powerUsage > 0 => powerTariff * powerUsage + tariff.standing_charge
      case _ => 0
    }

    val gasCost: BigDecimal = tariff.rates.gas match {
      case Some(gasTariff) if gasUsage > 0 => gasTariff * gasUsage + tariff.standing_charge
      case _ => 0
    }

    val totalCost = (powerCost + gasCost) * 1.05

    tariff.tariff -> totalCost.setScale(2, RoundingMode.HALF_EVEN)
  }

  def costs(powerUsage: BigDecimal, gasUsage: BigDecimal)(tariffs: List[Tariff]): List[(String, BigDecimal)] = {
    tariffs.map(tariff => cost(powerUsage, gasUsage)(tariff)).sortBy(_._2)
  }

  def annualUsageInKWh(targetMonthlySpend: BigDecimal, costPerKWh: BigDecimal): Int = {
    require(costPerKWh > 0, "Cost per kWh cannot be zero")
    (targetMonthlySpend / costPerKWh * 12).toInt
  }
}
