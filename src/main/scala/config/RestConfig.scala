package config

/** Created by Matias Zeitune nov. 2019 **/
case class RestConfig(appRest: String) extends ConfigLoader{

  val restConfig = applicationConfig.getConfig(appRest)

  val url: String = restConfig.getString("url")
  val readTimeOut: String = restConfig.getString("read-timeout")

}
