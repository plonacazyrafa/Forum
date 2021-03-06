import slick.jdbc.H2Profile.api.Database
import slick.jdbc.PostgresProfile.api._
import java.sql.Timestamp
import scala.math.floor
import scala.util.Random.nextInt


class DatabaseSetup extends DatabaseScheme {
  val db: Database = Database.forConfig("mydb")

  def clearDB: Unit = {
    val removeTopics = topics.delete
    val removeReplies = replies.delete
    db.run(removeTopics andThen removeReplies)
  }

  def generateTopics: Seq[Topic] =
    for (i <- 0 to 200) yield
      Topic(None, "andrea" + i*2, "andrea@wp.pl", "Przedstawiam mój problem", "Problem", i, new Timestamp(nextInt))

  def generateReplies: Seq[Reply] =
    for {
      i <- 1 to 600
      topicID = floor(i / 3) toLong
    } yield Reply(None, topicID, "grazyna", "grazyna12@onet.pl", "To jest moja odpowiedz", i, new Timestamp(nextInt))


  def addData: Unit = {
    val insertTopics = topics ++= generateTopics
    val insertReplies = replies ++= generateReplies
    db.run(insertTopics andThen insertReplies)
  }

  def setupDB: Unit = {
    clearDB
    addData
  }
}
