package config


import com.typesafe.config.{Config, ConfigFactory}

/** Created by Matias Zeitune nov. 2019 **/
trait ConfigLoader {

  lazy val applicationConfig: com.typesafe.config.Config = ConfigFactory.load

}
