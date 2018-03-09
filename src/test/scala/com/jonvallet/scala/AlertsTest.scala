package com.jonvallet.scala

import com.jonvallet.scala.alerts.{Alert, AlertSystem, Document, Mention}
import org.scalatest._
import scala.concurrent.duration._

class AlertsTest extends FlatSpec with Matchers {

  "A valid json document" should "map correctly to mentions" in {
    assertResult(Document(123, List(
      Mention("EVIL Corp", "Unfair Lending Practices", 525),
      Mention("Tyrell Corporation", "Unfair Lending Practices", 25)
    ))) {
      val json = """{"id": 123,"mentions": [{ "company": "EVIL Corp", "topic": "Unfair Lending Practices", "impact": 525 },{ "company": "Tyrell Corporation", "topic": "Unfair Lending Practices", "impact": 25 }]}"""
      Document(json)
    }

  }

  "An AlertSystem" should "return an alarm" in {

    assertResult(Seq(Alert("EVIL Corp", "Unfair Lending Practices", 525))) {
      val mentions = List(
        Mention("EVIL Corp", "Unfair Lending Practices", 525),
        Mention("Tyrell Corporation", "Unfair Lending Practices", 25)
      )
      AlertSystem.analyzeMentions(mentions)
    }
  }

  it should "aggregate mentions per company and topic" in {
    assertResult(Seq(Alert("EVIL Corp", "Unfair Lending Practices", 725))) {
      val mentions = List(
        Mention("EVIL Corp", "Unfair Lending Practices", 525),
        Mention("Tyrell Corporation", "Unfair Lending Practices", 25),
        Mention("EVIL Corp", "Unfair Lending Practices", 200)
      )
      AlertSystem.analyzeMentions(mentions)
    }
  }

  it should "filter mentions per company if provided" in {
    assertResult(Seq(Alert("EVIL Corp", "Unfair Lending Practices", 725))) {
      val mentions = List(
        Mention("EVIL Corp", "Unfair Lending Practices", 525),
        Mention("Tyrell Corporation", "Unfair Lending Practices", 255),
        Mention("EVIL Corp", "Unfair Lending Practices", 200)
      )
      AlertSystem.analyzeMentionsForCompany(mentions, "EVIL Corp")
    }
  }

  it should "print an alert with the duration" in {
      println (AlertSystem.toJson(Alert("EVIL Corp", "Unfair Lending Practices", 525, 1517590000000L, 1517590000030L)))
  }
}
