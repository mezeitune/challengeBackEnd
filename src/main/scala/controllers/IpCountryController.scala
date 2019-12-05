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
  Dada una IP obtenga info asociada a:
  ip-> encuentre pais al que pertenece ->
  +Nombre y codigo ISO del pais
  +Idiomas oficiales del pais
  +Horas actueles en el pais
  +Distancia estimanda entre Bs AS y el pais (km)
  +Moneda local , y su cotizacion actual en dolares (Optional)

  Estadisticas respecto a la info anterior:
  +Distancia mas lejana a Bs as desde la cual se haya consultado el servicio
  +Distancia mas cercana ...
  +Distancia promedio de todas las ejecucione sque se hayan hecho del servicio.
  -> Ver ejemplo en el enunciado

  APIS:
  Geolocalización de IPs: https://ip2country.info/
  Información de paises: http://restcountries.eu/
  Información sobre monedas: http://fixer.io/
   */
  get("/:ip") { request: Request => {
      val ip = request.getParam("ip")
      serv.ipInfo(ip) match {
        case Success(ipInformationResponse) => response.ok.json(ipInformationResponse)
        case Failure(_) => response.internalServerError()
      }
    }
  }
}