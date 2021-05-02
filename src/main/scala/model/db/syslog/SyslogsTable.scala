package model.db.syslog

import model.{Syslog}
import slick.jdbc.PostgresProfile.api._

import java.sql.Timestamp

class SyslogsTable(tag: Tag) extends Table[Syslog](tag, "logs") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def message = column[String]("message")

  def datetime = column[Timestamp]("date")

  def * = (id.?, message, datetime) <> ((Syslog.apply _).tupled, Syslog.unapply)
}
