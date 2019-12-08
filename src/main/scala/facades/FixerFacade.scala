package facades

import clients.FixerClient
import com.typesafe.scalalogging.StrictLogging
import dtos.FixerResponse
import javax.inject.{Inject, Singleton}

import scala.util.Try

/** Created by Matias Zeitune nov. 2019 **/
@Singleton
class FixerFacade @Inject()(currencyConverterClient: FixerClient) extends StrictLogging {

  def getCurrency(fromCurrency: String, toCurrency: String): Try[Double] = {
    val currencyConverterResponse: Try[FixerResponse] = currencyConverterClient.getCurrency(fromCurrency, toCurrency)
    currencyConverterResponse.map {
      case quoteInfo: FixerResponse =>
        quoteInfo
          .rates
          .get(toCurrency)
          .getOrElse(throw new RuntimeException("There was a problem retrieving quotes information"))
    }
  }

}
