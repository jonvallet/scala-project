package com.jonvallet.scala

import org.scalatest._

class TariffComparisonTest extends FlatSpec with Matchers {

  "The Tariff cost calculation" should "calculate the total annual cost of both power and gas consumption" in {
      assertResult("better-energy" -> 566.54) {
        TariffComparison.cost(2000, 2300) {
          Tariff("better-energy", Rates(Some(0.1367), Some(0.0288)), 8.33)
        }
      }
    }

  it should "calculate the total annual cost for tariff that only have one rate" in {
    assertResult("greener-energy" -> 429.20) {
      TariffComparison.cost(2000, 2300) {
        Tariff("greener-energy", Rates(Some(0.1544), None), 8.33)
      }
    }
  }

  it should "only calculates the total consumption of power if gas is not provided" in {
    assertResult("better-energy" -> 392.03) {
      TariffComparison.cost(2000, 0) {
        Tariff("better-energy", Rates(Some(0.1367), Some(0.0288)), 8.33)
      }
    }
  }

  it should "only calculates the total consumption of gas if power is not provided" in {
    assertResult("better-energy" -> 174.51) {
      TariffComparison.cost(0, 2300) {
        Tariff("better-energy", Rates(Some(0.1367), Some(0.0288)), 8.33)
      }
    }
  }

  it should "not calculates the total consumptions of neither gas or power if they are not proved " in {
    assertResult("better-energy" -> 0) {
      TariffComparison.cost(0, 0) {
        Tariff("better-energy", Rates(Some(0.1367), Some(0.0288)), 8.33)
      }
    }
  }

  it should "calculates the total consumptions and return them sorted by cheapest " in {
    val expectedValue = List("better-energy" -> 566.54, "2yr-fixed" -> 585.35)

    assertResult(expectedValue) {
      TariffComparison.costs(2000, 2300) {
        List(
          Tariff("better-energy", Rates(Some(0.1367), Some(0.0288)), 8.33),
          Tariff("2yr-fixed", Rates(Some(0.1397), Some(0.0296)), 8.75)
        )
      }
    }
  }

  it should "calculate the annual usage" in  {
    assertResult(2633) {
      TariffComparison.annualUsageInKWh(30.00, 0.1367)
    }
  }

  it should "fail if target cost is 0" in {
    assertThrows[IllegalArgumentException] {
      TariffComparison.annualUsageInKWh(30.00, 0)
    }
  }

}
