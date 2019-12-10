package com.mezeitune.services

import com.typesafe.scalalogging.StrictLogging
import com.mezeitune.exceptions.DistancesException
import javax.inject.{Inject, Singleton}
import com.mezeitune.repository.{DistanceInvocation, DistancesRepository}

import scala.util.{Failure, Try}

/** Created by Matias Zeitune dic. 2019 **/
@Singleton
class IpDistancesService @Inject() () extends StrictLogging {

  def distancesInformation: Try[DistanceInvocation] = {
    logger.info("Obtaining distance information")
    DistancesRepository.getDistances match{
      case Some(distances) => Try(distances)
      case None => {
        logger.error("there are no charged distances")
        Failure(DistancesException("there are no charged distances"))
      }
    }
  }

}
