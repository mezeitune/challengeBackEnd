package facades

import clients.FixerClient
import com.typesafe.scalalogging.StrictLogging
import dtos.FixerResponse
import exceptions.FixerClientException
import javax.inject.{Inject, Singleton}

import scala.util.Try

/** Created by Matias Zeitune nov. 2019 **/
@Singleton
class FixerFacade @Inject()(fixerClient: FixerClient) extends StrictLogging {

  def getCurrency(fromCurrency: String, toCurrency: String): Try[Double] = {
    val fixerResponse: Try[FixerResponse] = fixerClient.getCurrency(fromCurrency, toCurrency)
    logger.info(s"Obtaining quote information of $toCurrency")
    fixerResponse.map {
      case quoteInfo: FixerResponse =>
        quoteInfo
          .rates.getOrElse(toCurrency, {
          logger.error(s"There was a problem retrieving quotes information of $toCurrency")
          throw FixerClientException(s"There was a problem retrieving quotes information of $toCurrency")
        })
    }
  }

}
