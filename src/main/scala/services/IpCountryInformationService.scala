package services

import clients.{Ip2CountryClient, RestCountryClient}
import com.typesafe.scalalogging.StrictLogging
import facades.FixerFacade
import javax.inject.{Inject, Singleton}
import model.{CountryCurrency, IpCountryInformationResponse}
import org.joda.time.{DateTime, DateTimeZone}
import utils.GeoDistanceUtil

import scala.util.Try

/** Created by Matias Zeitune nov. 2019 **/

@Singleton
class IpCountryInformationService @Inject()(ip2CountryClient: Ip2CountryClient,
                                            restCountryClient: RestCountryClient,
                                            fixerFacade: FixerFacade) extends StrictLogging {

  def ipInformation(ip: String): Try[IpCountryInformationResponse] = {
    for {
      ipCountry <- ip2CountryClient.getCountryInfo(ip)
      countryInformation <- restCountryClient.getCountryInfo(ipCountry.countryCode3)
      quoteInformation <- Try(countryInformation.currencies.map(c => CountryCurrency(c.name,fixerFacade.getCurrency("USD",c.code).get)))
    } yield {
      val estimatedDistanceToBsAs = estimatedDistanceBetweenBsAsAnd(countryInformation.latlng.head, countryInformation.latlng(1))
      saveNewDistanceToBsAs(estimatedDistanceToBsAs, countryInformation.name)
      IpCountryInformationResponse(
        countryName = countryInformation.name,
        isoCountryCode = ipCountry.countryCode3,
        officialLanguages = countryInformation.languages.map(_.name),
        currentHours = countryInformation.timezones.map(timeZone => DateTime.now(DateTimeZone.forTimeZone(timeZone))),
        estimatedDistance = estimatedDistanceToBsAs,
        currencies = quoteInformation
      )
    }
  }


  def saveNewDistanceToBsAs(distance: Double, country: String): Unit = {

  }

  private def estimatedDistanceBetweenBsAsAnd(latCountry: Double, longCountry: Double): Double = {
    val bsAsLat = -34.603722
    val bsAsLong = -58.381592
    GeoDistanceUtil.distance(bsAsLat, latCountry, bsAsLong, longCountry)
  }

}
