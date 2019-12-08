package model

import org.joda.time.DateTime

/** Created by Matias Zeitune nov. 2019 **/
case class IpCountryInformationResponse(countryName: String,
                                        isoCountryCode: String,
                                        officialLanguages: Seq[String],
                                        currentHours: Seq[DateTime],
                                        estimatedDistance: Double,
                                        currencies: Seq[CountryCurrency]) //Countries like Cuba can have more than one currency

case class CountryCurrency(localCurrency: String,
                           euroQuote: Double)
