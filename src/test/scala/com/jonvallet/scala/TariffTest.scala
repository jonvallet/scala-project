package com.jonvallet.scala

import org.scalatest.FunSuite

import scala.io.Source

class TariffTest extends FunSuite {

  test("TariffMapper.apply") {
    val json = "{\"tariff\": \"better-energy\", \"rates\": {\"power\":  0.1367, \"gas\": 0.0288}, \"standing_charge\": 8.33}"
    val expectedValue = Tariff("better-energy", Rates(Some(0.1367), Some(0.0288)), 8.33)

    assertResult(expectedValue) {
      TariffMapper(json)
    }
  }

  test ("TariffMapper.parseJsonArray") {
    val json = "  [{\"tariff\": \"better-energy\", \"rates\": {\"power\":  0.1367, \"gas\": 0.0288}, \"standing_charge\": 8.33}]"
    val expectedValue = List(Tariff("better-energy", Rates(Some(0.1367), Some(0.0288)), 8.33))

    assertResult(expectedValue) {
      TariffMapper.parseJsonArray(json)
    }
  }

  test ("TariffMapper.parseJsonArray with prices.json") {
    val json = Source.fromResource("prices.json").mkString
    val expectedValue = List(Tariff("better-energy",Rates(Some(0.1367),Some(0.0288)),8.33), Tariff("2yr-fixed",Rates(Some(0.1397),Some(0.0296)),8.75), Tariff("greener-energy",Rates(Some(0.1544),None),8.33), Tariff("simpler-energy",Rates(Some(0.1396),Some(0.0328)),8.75))

    assertResult(expectedValue) {
      TariffMapper.parseJsonArray(json)
    }
  }
}
