package com.mezeitune.config

/** Created by Matias Zeitune dic. 2019 **/
case class KeyConfig(keyClient: String) extends ConfigLoader{
  val key = applicationConfig.getConfig(keyClient).getString("key")
}

