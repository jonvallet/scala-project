package com.jonvallet.scala

import scala.io.Source

object Main {

  val HELP: String =
    "Usage: [options]\n\n" +
    "  cost <POWER_USAGE> <GAS_USAGE>                         for the given annual kWh consumption(s), output an annual cost inclusive of VAT for applicable tariffs, \n" +
    "                                                         sorted by cheapest first. \n" +
    "                                                         Ex. $ cost 2000 2300 \n" +
    "  usage <TARIFF_NAME> <FUEL_TYPE> <TARGET_MONTHLY_SPEND> for the specified tariff calculate how much energy (in kWh) would be used annually from a monthly spend\n" +
    "                                                         in pounds (inclusive of VAT). Fuel type can be of power or gas.\n" +
    "                                                         Ex. $ usage greener-energy power 40"

  lazy val tariffs = {
    val json = Source.fromResource("prices.json").mkString
    TariffMapper.parseJsonArray(json)
  }

  def cost(powerUsage: String, gasUsage: String): Unit = {
    TariffComparison.costs(powerUsage.toInt, gasUsage.toInt)(tariffs).map(e => s"${e._1} ${e._2}").foreach(println)
  }

  def usage(arg2: String, arg3: String, arg4: String): Unit = ???

  def main(args: Array[String]): Unit = {

    args.toList match {
      case arg1 :: arg2 :: arg3 :: tail if arg1 == "cost" => cost(arg2, arg3)
      case arg1 :: arg2 :: arg3 :: arg4 :: tail if arg1 == "usage" => usage(arg2, arg3, arg4)
      case _ => println(HELP)
    }
  }

}
