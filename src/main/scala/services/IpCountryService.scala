package services

import clients.{Ip2CountryClient, RestCountryClient}
import com.typesafe.scalalogging.StrictLogging
import facades.CurrencyConverterFacade
import javax.inject.{Inject, Singleton}
import model.{CountryCurrency, IpCountryInformationResponse}
import org.joda.time.{DateTime, DateTimeZone}

import scala.util.Try

/** Created by Matias Zeitune nov. 2019 **/

@Singleton
class IpCountryService @Inject()(ip2CountryClient: Ip2CountryClient,
                                 restCountryClient: RestCountryClient,
                                 currencyConverterFacade: CurrencyConverterFacade) extends StrictLogging {
  /*
    Dada una IP obtenga info asociada a:
      ip-> encuentre pais al que pertenece ->
       +Nombre y codigo ISO del pais
       +Idiomas oficiales del pais
       +Horas actueles en el pais
       +Distancia estimanda entre Bs AS y el pais (km)
       +Moneda local , y su cotizacion actual en dolares (Optional)
   */
  def ipInfo(ip: String): Try[IpCountryInformationResponse] = {

    for {
      ipCountry <- ip2CountryClient.getCountryInfo(ip)
      countryInformation <- restCountryClient.getCountryInfo(ipCountry.countryCode3)
      quoteInformation <- Try(countryInformation.currencies.map(c => CountryCurrency(c.name,currencyConverterFacade.getCurrency("USD",c.code).get.currency)))
    } yield {
      IpCountryInformationResponse(
        countryName = countryInformation.name,
        isoCountryCode = ipCountry.countryCode3,
        officialLanguages = countryInformation.languages.map(_.name),
        currentHours = countryInformation.timezones.map(timeZone => DateTime.now(DateTimeZone.forTimeZone(timeZone))),
        estimatedDistance = "s",
        currencies = quoteInformation
      )
    }

  }

}
