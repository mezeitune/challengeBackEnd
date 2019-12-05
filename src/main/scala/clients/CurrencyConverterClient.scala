package clients

import com.typesafe.scalalogging.StrictLogging
import config.RestConfig
import dtos.CurrencyConverterValue
import javax.inject.Singleton
import sttp.client._
import utils.ObjectMapper

import scala.util.Try

/** Created by Matias Zeitune nov. 2019 **/
@Singleton
class CurrencyConverterClient extends StrictLogging {

  implicit val backend = HttpURLConnectionBackend()
  val restConfig = RestConfig("currencyConverter")
  val objectMapper = ObjectMapper.standardMapper

  def getCurrency(fromCurrency: String, toCurrency: String): Try[Map[String, CurrencyConverterValue]] = Try{
    val request = basicRequest.get(uri"${restConfig.url}/api/v5/convert?q=${fromCurrency}_${toCurrency}&compact=y&apiKey=642916bdd9898bbfe8af")
    val response = request.send()

    if(response.code.isSuccess){
      logger.info("Quotes information was successfully obtained")
      val jsonResponse = response.body.right.get.replace("val", "currency")
      objectMapper.readValue(jsonResponse, classOf[Map[String, CurrencyConverterValue]])
    }else{
      logger.error("There was a problem retrieving quotes information")
      throw new RuntimeException("There was a problem retrieving quotes information")
    }

  }

}
