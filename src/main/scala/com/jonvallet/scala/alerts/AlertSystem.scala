package com.jonvallet.scala.alerts

import spray.json._

import scala.concurrent.duration._ // if you don't supply your own Protocol (see below)

case class Alert(company: String, topic: String, impactTotal: Int, periodStartMillis: Long = 0L, periodEndMillis: Long = 0L)

object AlertSystem extends DefaultJsonProtocol {
  implicit val alertFormat: RootJsonFormat[Alert] = jsonFormat5(Alert.apply)
  val defaultThreshold = 100

  def analyzeMentions(mentions: Seq[Mention],
                      currentTimeInMillis: Long = 0L,
                      duration: Duration = 0 seconds,
                      threshold: Int = defaultThreshold,
                     ): Seq[Alert] = {
    val groupedByMentions: Map[(String, String), Seq[Alert]] = mentions
      .filter(mention => mention.impact > threshold)
      .map(mention => Alert(mention.company, mention.topic, mention.impact))
      .groupBy(mention => (mention.company, mention.topic))

    groupedByMentions.map(e => {
      val company = e._1._1
      val topic = e._1._2
      val impactTotal = e._2.map(_.impactTotal).sum
      Alert(company, topic, impactTotal, currentTimeInMillis, currentTimeInMillis + duration.toMillis)
    }).toSeq
  }

  def analyzeMentionsForCompany(mentions: Seq[Mention],
                                company: String,
                                currentTimeInMillis: Long = 0L,
                                duration: Duration = 0 seconds,
                                threshold: Int = defaultThreshold,
                               ): Seq[Alert] =
    analyzeMentions(mentions.filter(_.company == company), currentTimeInMillis, duration, threshold)

  def toJson(alert: Alert): String = alert.toJson.toString
}
