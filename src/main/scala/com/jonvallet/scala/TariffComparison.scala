package com.jonvallet.scala

import scala.math.BigDecimal.RoundingMode

object TariffComparison {
  def cost(powerUsageInKWh: Int, gasUsageInKWh: Int)(tariff: Tariff): (String, BigDecimal) = {
    val powerCost: BigDecimal = tariff.rates.power match {
      case Some(powerTariff) if powerUsageInKWh > 0 => powerTariff * powerUsageInKWh + 12*tariff.standing_charge
      case _ => 0
    }

    val gasCost: BigDecimal = tariff.rates.gas match {
      case Some(gasTariff) if gasUsageInKWh > 0 => gasTariff * gasUsageInKWh + 12*tariff.standing_charge
      case _ => 0
    }

    val totalCost = (powerCost + gasCost) * 1.05

    tariff.tariff -> totalCost.setScale(2, RoundingMode.HALF_EVEN)
  }

  def costs(powerUsageInKWh: Int, gasUsageInKWh: Int)(tariffs: List[Tariff]): List[(String, BigDecimal)] = {
    tariffs.map(tariff => cost(powerUsageInKWh, gasUsageInKWh)(tariff)).sortBy(_._2)
  }

  def annualUsageInKWh(targetMonthlySpend: BigDecimal, costPerKWh: BigDecimal): Int = {
    require(costPerKWh > 0, "Cost per kWh cannot be zero")
    (targetMonthlySpend / costPerKWh * 12).toInt
  }
}
