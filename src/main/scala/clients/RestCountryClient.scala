package clients

import com.typesafe.scalalogging.StrictLogging
import config.RestConfig
import exceptions.RestCountryClientException
import sttp.client._
import javax.inject.Singleton

import scala.util.Try

/** Created by Matias Zeitune nov. 2019 **/
@Singleton
class RestCountryClient extends StrictLogging{

  implicit val backend = HttpURLConnectionBackend()
  val restConfig = RestConfig("restCountries")

  def getCountryInfo(countryCode: String): Try[String] = Try{
    val request = basicRequest.get(uri"${restConfig.url}/rest/v2/alpha/$countryCode")
    val response = request.send()

    if(response.code.isSuccess){
      logger.info("Country information was successfully obtained")
      response.body.right.get
    }else{
      logger.error("There was a problem retrieving country information")
      throw RestCountryClientException("There was a problem retrieving country information")
    }

  }

}
