package com.jonvallet.scala

import spray.json._

case class Rates(power: Option[BigDecimal], gas: Option[BigDecimal])
case class Tariff(tariff: String, rates: Rates, standing_charge: BigDecimal)

object TariffMapper extends DefaultJsonProtocol {
  implicit val ratesFormat: RootJsonFormat[Rates] = jsonFormat2(Rates)
  implicit val ratesTariff: RootJsonFormat[Tariff] = jsonFormat3(Tariff)

  def apply(json: String) : Tariff = json.parseJson.convertTo[Tariff]
  def parseJsonArray(json: String): List[Tariff] = json.parseJson.convertTo[List[Tariff]]
}
