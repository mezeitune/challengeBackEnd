package com.mezeitune.utils

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper, SerializationFeature}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

/** Created by Matias Zeitune nov. 2019 **/
object ObjectMapper {

  val standardMapper = {
    val m: ObjectMapper = new ObjectMapper with ScalaObjectMapper
    m.registerModule(DefaultScalaModule)
    m.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    m.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    m.configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, false)

    m.registerModules(
      DefaultScalaModule
    )
    m
  }
}