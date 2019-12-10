package com.mezeitune.services

import com.mezeitune.clients.{FixerClient, Ip2CountryClient, RestCountryClient}
import com.mezeitune.dtos.Currencie
import com.mezeitune.exceptions.{FixerClientException, IpInvalidException}
import com.mezeitune.model.{CountryCurrency, IpCountryInformationResponse}
import com.mezeitune.repository.DistancesRepository
import com.mezeitune.utils.GeoDistanceUtil
import com.typesafe.scalalogging.StrictLogging
import javax.inject.{Inject, Singleton}
import org.joda.time.{DateTime, DateTimeZone}

import scala.util.{Failure, Try}

/** Created by Matias Zeitune nov. 2019 **/

@Singleton
class IpCountryInformationService @Inject()(ip2CountryClient: Ip2CountryClient,
                                            restCountryClient: RestCountryClient,
                                            fixerClient: FixerClient) extends StrictLogging {

  def apply(ip: String): Try[IpCountryInformationResponse] = {
    isIpValid(ip) match {
      case true => ipInformation(ip)
      case false => Failure(IpInvalidException(s"$ip is invalid"))
    }
  }

  private def isIpValid(ip: String): Boolean = {
    ip.matches("^(?=.*[^\\.]$)((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.?){4}$")
  }

  private def ipInformation(ip: String): Try[IpCountryInformationResponse] = {
    logger.info(s"Obtaining information of $ip")
    for {
      ipCountry <- ip2CountryClient.getCountryInfo(ip)
      countryInformation <- restCountryClient.getCountryInfo(ipCountry.countryCode)
      quoteInformation <- fixerClient.getCurrency("USD")
      quoteInformationMapped <- Try(quoteCurrencies(countryInformation.currencies, quoteInformation.rates))
    } yield {
      logger.info(s"Calculating Distance between BsAs and ${countryInformation.name}")
      val estimatedDistanceToBsAs = estimatedDistanceBetweenBsAsAnd(countryInformation.latlng.head, countryInformation.latlng(1))
      logger.info(s"Saving distance between BsAs and ${countryInformation.name}")
      DistancesRepository.saveNewDistance(estimatedDistanceToBsAs, countryInformation.name)
      IpCountryInformationResponse(
        countryName = countryInformation.name,
        isoCountryCode = ipCountry.countryCode,
        officialLanguages = countryInformation.languages.map(_.name),
        currentHours = countryInformation.timezones.map(timeZone => DateTime.now(DateTimeZone.forTimeZone(timeZone))),
        estimatedDistance = estimatedDistanceToBsAs,
        currencies = quoteInformationMapped
      )
    }
  }

  private def quoteCurrencies(currencies: Seq[Currencie], quoteInformation: Map[String, Double]): Seq[CountryCurrency] = {
    currencies
      .map(c =>
        CountryCurrency(
          localCurrency = c.name,
          euroQuote = quoteInformation.getOrElse(c.code, throw FixerClientException(s"There was a problem retrieving quotes information of $c.code"))
        )
      )
  }

  private def estimatedDistanceBetweenBsAsAnd(latCountry: Double, longCountry: Double): Double = {
    val bsAsLat = -34.603722
    val bsAsLong = -58.381592
    GeoDistanceUtil.distance(bsAsLat, latCountry, bsAsLong, longCountry)
  }

}
