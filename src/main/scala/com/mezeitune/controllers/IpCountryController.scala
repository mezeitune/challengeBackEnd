package com.mezeitune.controllers

import com.mezeitune.exceptions.{DistancesException, IpInvalidException}
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.mezeitune.services.{IpCountryInformationService, IpDistancesService}
import javax.inject.{Inject, Singleton}
import com.mezeitune.model.FailureResponse

import scala.util.{Failure, Success}

/** Created by Matias Zeitune nov. 2019 **/
@Singleton
class IpCountryController @Inject()(ipCountryInformationService: IpCountryInformationService,
                                    ipDistancesService: IpDistancesService)
  extends Controller {

  get("/ip/:ipNumber") { request: Request => {
      val ip = request.getParam("ipNumber")
      ipCountryInformationService(ip) match {
        case Success(ipInformationResponse) => response.ok.json(ipInformationResponse)
        case Failure(IpInvalidException(msg)) => response.badRequest(FailureResponse(400, msg))
        case Failure(exception) => response.internalServerError(FailureResponse(500, exception.getMessage))
      }
    }
  }

  get("/distances") { _: Request => {
      ipDistancesService.distancesInformation match {
        case Success(ipInformationResponse) => response.ok.json(ipInformationResponse)
        case Failure(DistancesException(msg)) => response.accepted(FailureResponse(202, msg))
        case Failure(exception) => response.internalServerError(FailureResponse(500, exception.getMessage))
      }
    }
  }
}