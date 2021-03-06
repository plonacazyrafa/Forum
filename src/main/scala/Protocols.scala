import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._
import java.sql.Timestamp
import scala.language.implicitConversions

case class DeleteRequest(id: Long, secret: Long)
case class UpdateRequest(id: Long, secret: Long, content: String)
case class ErrorMessage(message: String)

object ErrorMessage {
  val topicNotFound = "There is no topic with this id ."
  val wrongTopicFormat = "Wrong topic format."
  val replyNotFound = "There is no reply with this id."
  val wrongReplyFormat = "There is no such reply."
  val wrongSecret = "Secret do not match."
  val page0 = "Pages start from 1."
}

trait Protocols extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val timestampFormat: JsonFormat[Timestamp] = jsonFormat[Timestamp](TimestampReader, TimestampWriter)
  implicit val errorMessageFormat = jsonFormat1(ErrorMessage.apply)
  implicit val topicFormat = jsonFormat7(Topic.apply)
  implicit val replyFormat = jsonFormat7(Reply.apply)
  implicit val deleteRequestFormat = jsonFormat2(DeleteRequest.apply)
  implicit val updateRequestFormat = jsonFormat3(UpdateRequest.apply)
}

object TimestampReader extends RootJsonReader[Timestamp] {
  import DateConversion._

  def read(json: JsValue): Timestamp = json match {
      case time: JsValue => new java.util.Date
      case _ => throw DeserializationException("Wrong date format.")
    }
}

object TimestampWriter extends RootJsonWriter[Timestamp] {
  def write(timestamp: Timestamp): JsValue = JsString(timestamp.toString)
}

