package controllers

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import services.SimpleService
import javax.inject.{Inject, Singleton}

/** Created by Matias Zeitune nov. 2019 **/
@Singleton
class MainController @Inject()(serv: SimpleService) extends Controller {

  get("/groups/:id") { request: Request =>
    response.ok.json("response body here")
    /*msgSvc(request.name).map(response.ok.json).run.handle {
      case _: Error => response.badRequest*/
  }
}