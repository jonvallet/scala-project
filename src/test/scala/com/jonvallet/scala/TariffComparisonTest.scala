package com.jonvallet.scala

import org.scalatest.FunSuite

class TariffComparisonTest extends FunSuite {

  test("TariffComparison.cost(2000, 2300){Tariff(\"better-energy\", Rates(Some(0.1367), Some(0.0288)), 8.33)}") {
    assertResult("better-energy" -> 374.12) {
      TariffComparison.cost(2000, 2300) {
        Tariff("better-energy", Rates(Some(0.1367), Some(0.0288)), 8.33)
      }
    }
  }

  test("TariffComparison.cost(2000, 2300){Tariff(\"greener-energy\", Rates(Some(0.1544), None), 8.33)}") {
    assertResult("greener-energy" -> 332.99) {
      TariffComparison.cost(2000, 2300) {
        Tariff("greener-energy", Rates(Some(0.1544), None), 8.33)
      }
    }
  }

  test("TariffComparison.cost(2000, 0){Tariff(\"better-energy\", Rates(Some(0.1367), Some(0.0288)), 8.33)}") {
    assertResult("better-energy" -> 295.82) {
      TariffComparison.cost(2000, 0) {
        Tariff("better-energy", Rates(Some(0.1367), Some(0.0288)), 8.33)
      }
    }
  }

  test("TariffComparison.cost(0, 2300){Tariff(\"better-energy\", Rates(Some(0.1367), Some(0.0288)), 8.33)}") {
    assertResult("better-energy" -> 78.30) {
      TariffComparison.cost(0, 2300) {
        Tariff("better-energy", Rates(Some(0.1367), Some(0.0288)), 8.33)
      }
    }
  }

  test("TariffComparison.cost(0, 0){Tariff(\"better-energy\", Rates(Some(0.1367), Some(0.0288)), 8.33)}") {
    assertResult("better-energy" -> 0) {
      TariffComparison.cost(0, 0) {
        Tariff("better-energy", Rates(Some(0.1367), Some(0.0288)), 8.33)
      }
    }
  }

  test("TariffComparison.costs(2000, 2300){List(Tariff(\"better-energy\", Rates(Some(0.1367), Some(0.0288)), 8.33), Tariff(\"2yr-fixed\", Rates(Some(0.1397), Some(0.0296)), 8.75))}") {
    val expectedValue = List("better-energy" -> 374.12, "2yr-fixed" -> 383.23)

    assertResult(expectedValue) {
      TariffComparison.costs(2000, 2300) {
        List(
          Tariff("better-energy", Rates(Some(0.1367), Some(0.0288)), 8.33),
          Tariff("2yr-fixed", Rates(Some(0.1397), Some(0.0296)), 8.75)
        )
      }
    }
  }

  test("TariffComparison.annualUsageInKWh(30.00, 0.1367)") {
    assertResult(2633) {
      TariffComparison.annualUsageInKWh(30.00, 0.1367)
    }
  }

}
