package parser

import model.SyslogParseModel

import java.sql.Timestamp
import java.util.regex.Pattern
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import java.util.{Date, Locale}
import java.util.regex.Matcher

@SerialVersionUID(100L)
class SyslogParser extends Serializable {

  private val dateTime = "([A-Z]{1}[a-z]{2} \\d{2} \\d{2}:\\d{2}:\\d{2})"
  private val message = "(.*)"

  private val regex = s"$dateTime $message"
  private val p = Pattern.compile(regex)

  def parseRecord(record: String): Option[SyslogParseModel] = {
    val matcher = p.matcher(record)
    if (matcher.find) {
      Some(buildAccessLogRecord(matcher))
    } else {
      None
    }
  }

  def parseRecordReturningNullObjectOnFailure(record: String): SyslogParseModel = {
    val matcher = p.matcher(record)
    if (matcher.find) {
      buildAccessLogRecord(matcher)
    } else {
      SyslogParser.nullObjectAccessLogRecord
    }
  }

  private def buildAccessLogRecord(matcher: Matcher) = {
    SyslogParseModel(
      matcher.group(1),
      matcher.group(2))
  }
}

object SyslogParser {

  val nullObjectAccessLogRecord = SyslogParseModel("", "")

  def parseDateField(field: String): Option[Timestamp] = {
    val regex: String = "([A-Z]{1}[a-z]{2}) (\\d+) (\\d+):(\\d+):(\\d+)"
    val pattern: Pattern = Pattern.compile(regex)
    val dateForYear: Date = new Date()
    var date: Date = null
    val m: Matcher = pattern.matcher(field)
    val parser: DateTimeFormatter =
      DateTimeFormatter.ofPattern("MMM").withLocale(Locale.ENGLISH)
    if (m.find()) {
      val month: Int = parser.parse(m.group(1)).get(ChronoField.MONTH_OF_YEAR)
      date = new Date(
        dateForYear.getYear,
        month - 1,
        java.lang.Integer.parseInt(m.group(2)),
        java.lang.Integer.parseInt(m.group(3)),
        java.lang.Integer.parseInt(m.group(4)),
        java.lang.Integer.parseInt(m.group(5))
      )
      val timestamp: Timestamp = new Timestamp(date.getTime)
      Some(timestamp)
    } else {
      None
    }
  }
}
