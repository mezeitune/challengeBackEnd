package controllers

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import services.{IpCountryInformationService, IpDistancesService}
import javax.inject.{Inject, Singleton}
import model.FailureResponse

import scala.util.{Failure, Success}

/** Created by Matias Zeitune nov. 2019 **/
@Singleton
class IpCountryController @Inject()(ipCountryInformationService: IpCountryInformationService,
                                    ipDistancesService: IpDistancesService)
  extends Controller {

  get("/ip/:ipNumber") { request: Request => {
      val ip = request.getParam("ipNumber")
      ipCountryInformationService.ipInformation(ip) match {
        case Success(ipInformationResponse) => response.ok.json(ipInformationResponse)
        case Failure(exception) => response.internalServerError(FailureResponse(500, exception.getMessage))
      }
    }
  }

  get("/distances") { _: Request => {
      ipDistancesService.distancesInformation match {
        case Success(ipInformationResponse) => response.ok.json(ipInformationResponse)
        case Failure(exception) => response.internalServerError(FailureResponse(500, exception.getMessage))
      }
    }
  }
}