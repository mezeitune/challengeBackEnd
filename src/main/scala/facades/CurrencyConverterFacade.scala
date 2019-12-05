package facades

import clients.CurrencyConverterClient
import com.typesafe.scalalogging.StrictLogging
import dtos.CurrencyConverterValue
import javax.inject.{Inject, Singleton}

import scala.util.Try

/** Created by Matias Zeitune nov. 2019 **/
@Singleton
class CurrencyConverterFacade @Inject()(currencyConverterClient: CurrencyConverterClient) extends StrictLogging {

  def getCurrency(fromCurrency: String, toCurrency: String): Try[CurrencyConverterValue] = {
    val key = s"${fromCurrency}_${toCurrency}"
    val currencyConverterResponse: Try[Map[String, CurrencyConverterValue]] = currencyConverterClient.getCurrency(fromCurrency, toCurrency)
    currencyConverterResponse.map {
      case quoteInfo: Map[String, CurrencyConverterValue] =>
        quoteInfo.get(key)
          .getOrElse(throw new RuntimeException("There was a problem retrieving quotes information"))
    }
  }

}
