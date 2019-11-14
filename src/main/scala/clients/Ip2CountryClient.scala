package clients

import com.typesafe.scalalogging.StrictLogging
import config.RestConfig
import dtos.Ip2CountryResponse
import javax.inject.Singleton
import sttp.client._
import utils.ObjectMapper

import scala.util.Try
/** Created by Matias Zeitune nov. 2019 **/
@Singleton
class Ip2CountryClient extends StrictLogging{

  implicit val backend = HttpURLConnectionBackend()
  val restConfig = RestConfig("ip2country")
  val objectMapper = ObjectMapper.standardMapper

  def getCountryInfo(ip: String): Try[Ip2CountryResponse] = Try{
    val request = basicRequest.get(uri"${restConfig.url}/ip?$ip")
    val response = request.send()

    objectMapper.readValue(response.body.right.get, classOf[Ip2CountryResponse])
  }

}
