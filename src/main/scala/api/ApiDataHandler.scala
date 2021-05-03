package api

import com.fasterxml.jackson.databind.ObjectMapper
import model.dao.syslog.SyslogsDao
import model.{Data, DbHistogramMapper, HighlightText, Histogram, ReqData, ResponseData, ResponseHistogram, Syslog}

import java.util.ArrayList
import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait ApiDataHandler {

  def checkStatus() = {
    val mapper = new ObjectMapper
    val node = mapper.createObjectNode
    node.put("status", "ok")
    node
  }

  def getRowSize(data : Future[Int]) = {
    val mapper = new ObjectMapper
    val node = mapper.createObjectNode
    data.map(size => (node.put("size", size)))
  }

  def generateDataFromSyslogSeq(reqData: ReqData, syslogs: Seq[Syslog]) =
    ResponseData(
      syslogs.map {
        syslog =>
          Data(
            syslog.datetime,
            syslog.message,
            {
              val seqHighlight: ArrayList[HighlightText] = new ArrayList[HighlightText]()
              val text: String = syslog.message.toLowerCase()
              val phrase: String = reqData.phrase.toLowerCase()
              val phraseLength: Int = phrase.length
              var index: Int = 0
              var wordLength: Int = 0

              while (index != -1) {
                index = text.indexOf(phrase, index + wordLength)
                if (index != -1) {
                  //As index start from 0, so add extra one for real world position
                  seqHighlight.add(HighlightText(index + 1, index + phraseLength))
                }
                wordLength = phraseLength
              }
              seqHighlight.asScala.toList
            }
          )
      }.toList,
      reqData.datetimeFrom,
      reqData.datetimeUntil,
      reqData.phrase
    )

  def generateHistogramFromDbHistoMapper(reqData: ReqData, histos: Vector[DbHistogramMapper]) =
    ResponseHistogram(
      histos.map{
        histo =>
          Histogram(
            histo.date,
            {
              var counter = 0
              val text: String = histo.message.toLowerCase()
              val phrase: String = reqData.phrase.toLowerCase()
              val phraseLength: Int = phrase.length
              var index: Int = 0
              var wordLength: Int = 0

              while (index != -1) {
                index = text.indexOf(phrase, index + wordLength)
                if (index != -1) {
                  //As index start from 0, so add extra one for real world position
                  counter = counter + 1
                }
                wordLength = phraseLength
              }
              counter
            }
          )
      },
      reqData.datetimeFrom,
      reqData.datetimeUntil,
      reqData.phrase
    )

}
