package com.mezeitune.clients

import com.typesafe.scalalogging.StrictLogging
import com.mezeitune.config.RestConfig
import com.mezeitune.dtos.Ip2CountryResponse
import com.mezeitune.exceptions.Ip2CountryClientException
import javax.inject.Singleton
import sttp.client._
import com.mezeitune.utils.ObjectMapper

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

    if(response.code.isSuccess){
      logger.info("Ip2Country information was successfully obtained")
      objectMapper.readValue(response.body.right.get, classOf[Ip2CountryResponse])
    }else{
      logger.error("There was a problem retrieving Ip2Country information")
      throw Ip2CountryClientException("There was a problem retrieving Ip2Country information")
    }
  }

}
