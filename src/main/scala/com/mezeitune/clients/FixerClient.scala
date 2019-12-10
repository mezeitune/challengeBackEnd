package com.mezeitune.clients

import com.mezeitune.config.{KeyConfig, RestConfig}
import com.mezeitune.dtos.FixerResponse
import com.mezeitune.exceptions.FixerClientException
import com.mezeitune.utils.ObjectMapper
import com.typesafe.scalalogging.StrictLogging
import javax.inject.Singleton
import sttp.client._

import scala.util.Try

/** Created by Matias Zeitune nov. 2019 **/
@Singleton
class FixerClient extends StrictLogging {

  private implicit val backend = HttpURLConnectionBackend()
  private val restConfig = RestConfig("fixer")
  private val fixerKey = KeyConfig("fixerKey").key
  private val objectMapper = ObjectMapper.standardMapper

  def getCurrency(fromCurrency: String): Try[FixerResponse] = Try{
    val request = basicRequest.get(uri"${restConfig.url}/api/latest?access_key=$fixerKey")
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
