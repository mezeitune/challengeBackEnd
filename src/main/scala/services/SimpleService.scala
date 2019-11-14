package services

import clients.{FixerClient, Ip2CountryClient, RestCountryClient}
import javax.inject.{Inject, Singleton}

/** Created by Matias Zeitune nov. 2019 **/

@Singleton
class SimpleService @Inject()(ip2CountryClient: Ip2CountryClient,
                              restCountryClient: RestCountryClient,
                              fixerClient: FixerClient) {

  def ipInfo(ip: Int): String = {
    restCountryClient.getCountryInfo("col").get
  }

}
