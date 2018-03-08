package com.jonvallet.scala.alerts

import rx.lang.scala.{Observable, _}

import scala.concurrent.duration._

object Main {
  def main (args: Array[String]): Unit = {


    val filter: Option[String] = args.toList match {
      case arg1 :: _ => Some(args.mkString(" "))
      case _ => None
    }
    println("Write exit to finish")
    filter match {
      case Some(company) => println(s"Filtering Alerts for company $company")
      case None => println("Observing all alerts")
    }

    val observable: Observable[(Duration, Seq[Document])] = io.Source.stdin.getLines()
      .toStream
      .toObservable
      .takeWhile(line => !line.equals("exit"))
      .map(line => Document(line))
      .tumblingBuffer(30 seconds)
      .timeInterval

    observable.foreach(seq => {
      val mentions: Seq[Mention] = for {
        document <- seq._2
        mention <- document.mentions
      } yield mention

      val alerts = filter match {
        case Some(company) => AlertSystem.analyzeMentionsForCompany(mentions, company, System.currentTimeMillis(), seq._1)
        case None => AlertSystem.analyzeMentions(mentions, System.currentTimeMillis(), seq._1)
      }
      alerts.foreach(alert => println(AlertSystem.toFormmatedJson(alert)))
    })
  }
}
