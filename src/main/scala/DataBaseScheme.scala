import slick.lifted.Tag
import slick.jdbc.PostgresProfile.api._
import java.sql.Timestamp

import org.joda.time.DateTime
import org.joda.time.DateTimeZone.UTC
import slick.sql.SqlProfile.ColumnOption.SqlType


final case class Topic(id: Long, alias: String, email: String, content: String, topic: String, secret: Long, timestamp: Timestamp)
final case class Reply(id: Long, topicId: Long, alias: String, email: String, content: String, timestamp: Timestamp)
final case class TopicWithReplies(topic: Topic, replies: List[Reply])
final case class Secret(secret: Long)

trait DataBaseScheme {
  class RepliesTable(tag: Tag) extends Table[Reply](tag, "replies") {
    def id = column[Long]("reply_id", O.PrimaryKey)
    def topicId = column[Long]("topic_id")
    def alias = column[String]("alias")
    def email = column[String]("email")
    def content = column[String]("content")
    def timestamp = column[Timestamp]("timestamp")
    def * = (id, topicId, alias, email, content, timestamp) <> (Reply.tupled, Reply.unapply)
  }
  val replies = TableQuery[RepliesTable]

  class TopicsTable(tag: Tag) extends Table[Topic] (tag, "topics") {
    def id = column[Long]("topic_id", O.PrimaryKey)
    def alias = column[String]("alias")
    def email = column[String]("email")
    def content = column[String]("content")
    def topic = column[String]("topic")
    def secret = column[Long]("secret")
    def timestamp = column[Timestamp]("timestamp")
    def * = (id, alias, email, content, topic, secret, timestamp) <> (Topic.tupled, Topic.unapply)
  }
  val topics = TableQuery[TopicsTable]

}
