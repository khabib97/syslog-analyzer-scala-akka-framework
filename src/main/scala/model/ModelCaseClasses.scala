package model


import com.fasterxml.jackson.annotation.JsonFormat

import java.sql.Timestamp

case class SyslogParseModel(
                       dateTime: String,
                       message: String
                     )

case class Syslog(
                    id: Option[Long],
                    message: String,
                    datetime: Timestamp)

case class ReqData(
                    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
                    datetimeFrom: Timestamp,
                    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
                    datetimeUntil: Timestamp,
                    phrase: String)

case class HighlightText(
                          fromPosition: Int,
                          toPosition: Int
                        )

case class Data(
                 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
                 datetime: Timestamp,
                 message: String,
                 highlightText: List[HighlightText]
               )

case class ResponseData(
                      data: List[Data],
                      @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
                      datetimeFrom: Timestamp,
                      @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
                      datetimeUntil: Timestamp,
                      phrase: String
                    )

case class Histogram(
                      @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
                      datetime: Timestamp,
                      counts: Long
                    )

case class ResponseHistogram(
                           histogram: Vector[Histogram],
                           @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
                           datetimeFrom: Timestamp,
                           @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
                           datetimeUntil: Timestamp,
                           phrase: String
                         )

case class DbHistogramMapper(
                          date: Timestamp,
                          message: String
                        )
