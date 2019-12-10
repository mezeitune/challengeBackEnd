package com.mezeitune.services

import com.mezeitune.clients.{Ip2CountryClient, RestCountryClient}
import com.typesafe.scalalogging.StrictLogging
import com.mezeitune.facades.FixerFacade
import javax.inject.{Inject, Singleton}
import com.mezeitune.model.{CountryCurrency, IpCountryInformationResponse}
import org.joda.time.{DateTime, DateTimeZone}
import com.mezeitune.repository.DistancesRepository
import com.mezeitune.utils.GeoDistanceUtil

import scala.util.Try

/** Created by Matias Zeitune nov. 2019 **/

@Singleton
class IpCountryInformationService @Inject()(ip2CountryClient: Ip2CountryClient,
                                            restCountryClient: RestCountryClient,
                                            fixerFacade: FixerFacade) extends StrictLogging {

  def ipInformation(ip: String): Try[IpCountryInformationResponse] = {
    logger.info(s"Obtaining information of $ip")
    for {
      ipCountry <- ip2CountryClient.getCountryInfo(ip)
      countryInformation <- restCountryClient.getCountryInfo(ipCountry.countryCode)
      quoteInformation <- Try(countryInformation.currencies.map(c => CountryCurrency(c.name,fixerFacade.getCurrency("USD",c.code).get)))
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
        currencies = quoteInformation
      )
    }
  }

  private def estimatedDistanceBetweenBsAsAnd(latCountry: Double, longCountry: Double): Double = {
    val bsAsLat = -34.603722
    val bsAsLong = -58.381592
    GeoDistanceUtil.distance(bsAsLat, latCountry, bsAsLong, longCountry)
  }

}
