package model

/** Created by Matias Zeitune nov. 2019 **/
case class IpCountryInformationResponse(countryName: String,
                                        isoCountryCode: String,
                                        officialLanguages: Seq[String],
                                        currentHours: Seq[String],
                                        estimatedDistance: String,
                                        localCurrency: String,
                                        dolarQuote: String)
