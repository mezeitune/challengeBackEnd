package controllers

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import services.IpCountryService
import javax.inject.{Inject, Singleton}

import scala.util.{Failure, Success}

/** Created by Matias Zeitune nov. 2019 **/
@Singleton
class IpCountryController @Inject()(serv: IpCountryService) extends Controller {

  /*
    Estadisticas respecto a la info anterior:
    +Distancia mas lejana a Bs as desde la cual se haya consultado el servicio
    +Distancia mas cercana ...
    +Distancia promedio de todas las ejecucione sque se hayan hecho del servicio.
    -> Ver ejemplo en el enunciado
   */
  get("/ip/:ipNumber") { request: Request => {
      val ip = request.getParam("ipNumber")
      serv.ipInfo(ip) match {
        case Success(ipInformationResponse) => response.ok.json(ipInformationResponse)
        case Failure(_) => response.internalServerError()
      }
    }
  }
}