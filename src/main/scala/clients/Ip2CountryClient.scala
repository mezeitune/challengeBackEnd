package clients

import com.typesafe.scalalogging.StrictLogging
import config.RestConfig
import javax.inject.Singleton
/** Created by Matias Zeitune nov. 2019 **/
@Singleton
class Ip2CountryClient extends StrictLogging{

  val restConfig = RestConfig("ip2country")

}
