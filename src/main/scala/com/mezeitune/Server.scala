package com.mezeitune

import com.mezeitune.controllers.IpCountryController
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, HttpResponseFilter, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter

/** Created by Matias Zeitune dic. 2019 **/
object Server extends HttpServer {
  override val disableAdminHttpServer = true

  override val defaultHttpPort: String = ":9290"

  override protected def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .filter[HttpResponseFilter[Request]]
      .add[IpCountryController]
  }

}
