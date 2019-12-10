package com.mezeitune.dtos

import com.fasterxml.jackson.annotation.JsonProperty

/** Created by Matias Zeitune nov. 2019 **/
case class Ip2CountryResponse(@JsonProperty("countryCode3") countryCode: String,
                              countryName: String)
