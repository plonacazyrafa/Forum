import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._
import java.sql.Timestamp
import scala.language.implicitConversions

trait Protocols extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val timestampFormat: JsonFormat[Timestamp] = jsonFormat[Timestamp](TimestampReader, TimestampWriter)
  implicit val errorMessageFormat = jsonFormat1(ErrorMessage.apply)
  implicit val topicFormat = jsonFormat7(Topic.apply)
  implicit val replyFormat = jsonFormat6(Reply.apply)
  implicit val topicWithReplyFormat = jsonFormat2(TopicWithReplies.apply)
  implicit val contentFormat = jsonFormat1(Content.apply)
}


object TimestampReader extends RootJsonReader[Timestamp] {
  import DateConversion._
    def read(json: JsValue): Timestamp = {
      json match {
        case time: JsValue => new java.util.Date
        case _ => throw DeserializationException("Wrong date format.")
      }
    }
}

object TimestampWriter extends RootJsonWriter[Timestamp] {
  def write(timestamp: Timestamp): JsValue = JsString(timestamp.toString)
}

