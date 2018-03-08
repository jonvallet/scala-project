package com.jonvallet.scala.alerts

import spray.json.{DefaultJsonProtocol, _}

case class Mention(company: String, topic: String, impact: Int)
case class Document(id: Int, mentions: List[Mention])

object Document extends DefaultJsonProtocol {
  implicit val mentionFormat: RootJsonFormat[Mention] = jsonFormat3(Mention.apply)
  implicit val documentFormat: RootJsonFormat[Document] = jsonFormat2(Document.apply)
  def apply(json: String): Document = json.parseJson.convertTo[Document]
}
