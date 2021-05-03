package service

import model.Syslog
import model.dao.syslog.SyslogsDao
import parser.SyslogParser

import java.sql.Timestamp
import java.util.{ArrayList, Date}
import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.io.Source

object LoadSyslogs extends Config {

  val loadSyslogs = Future {
    val parser = new SyslogParser
    val syslogs: ArrayList[Syslog] = new ArrayList[Syslog]()

    Source.fromFile(logFilePath).getLines().foreach {
      line => {
        val sysLog = parser.parseRecord(line)
        sysLog.foreach { res =>
          var date_out = new Timestamp(new Date().getTime)
          SyslogParser.parseDateField(res.dateTime).foreach(date => date_out = date)
          val syslog = Syslog(None, res.message, date_out)
          syslogs.add(syslog)
        }
      }
    }
    SyslogsDao.insertBulk(syslogs.asScala.toList)
  }
}
