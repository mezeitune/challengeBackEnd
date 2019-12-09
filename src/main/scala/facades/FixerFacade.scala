package facades

import clients.FixerClient
import com.typesafe.scalalogging.StrictLogging
import dtos.FixerResponse
import exceptions.FixerClientException
import javax.inject.{Inject, Singleton}

import scala.util.Try

/** Created by Matias Zeitune nov. 2019 **/
@Singleton
class FixerFacade @Inject()(currencyConverterClient: FixerClient) extends StrictLogging {

  def getCurrency(fromCurrency: String, toCurrency: String): Try[Double] = {
    val fixerResponse: Try[FixerResponse] = currencyConverterClient.getCurrency(fromCurrency, toCurrency)
    fixerResponse.map {
      case quoteInfo: FixerResponse =>
        quoteInfo
          .rates
          .get(toCurrency)
          .getOrElse(throw FixerClientException("There was a problem retrieving quotes information"))
    }
  }

}
