/** Created by Matias Zeitune nov. 2019 **/
package $package$

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, HttpResponseFilter, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter
import controllers.MainController

object Server extends HttpServer {
  override val disableAdminHttpServer = true

  override val defaultHttpPort: String = ":9290"

  override protected def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .filter[HttpResponseFilter[Request]]
      .add[MainController]
  }

}

