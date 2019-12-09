package clients

import com.typesafe.scalalogging.StrictLogging
import config.RestConfig
import dtos.FixerResponse
import exceptions.FixerClientException
import javax.inject.Singleton
import sttp.client._
import utils.ObjectMapper

import scala.util.Try

/** Created by Matias Zeitune nov. 2019 **/
@Singleton
class FixerClient extends StrictLogging {

  implicit val backend = HttpURLConnectionBackend()
  val restConfig = RestConfig("fixer")
  val objectMapper = ObjectMapper.standardMapper

  def getCurrency(fromCurrency: String, toCurrency: String): Try[FixerResponse] = Try{
    val request = basicRequest.get(uri"${restConfig.url}/api/latest?access_key=147e1fc48f807d984f56409f29583d68")
    val response = request.send()

    if(response.code.isSuccess){
      logger.info("Quotes information was successfully obtained")
      objectMapper.readValue(response.body.right.get, classOf[FixerResponse])
    }else{
      logger.error("There was a problem retrieving quotes information")
      throw FixerClientException("There was a problem retrieving quotes information")
    }
  }
}
