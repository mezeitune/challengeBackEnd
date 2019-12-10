package com.mezeitune.dtos

import java.util.TimeZone

/** Created by Matias Zeitune nov. 2019 **/
case class RestCountryResponse(name: String,
                               capital: String,
                               languages: Seq[Language],
                               currencies: Seq[Currencie],
                               latlng: Seq[Double],
                               timezones: Seq[TimeZone])

case class Language(name: String)

case class Currencie(code: String,
                     name: String,
                     symbol: String)