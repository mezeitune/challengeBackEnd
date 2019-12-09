package services

import exceptions.DistancesException
import javax.inject.{Inject, Singleton}
import repository.{DistanceInvocation, DistancesRepository}

import scala.util.{Failure, Try}

/** Created by Matias Zeitune dic. 2019 **/
@Singleton
class IpDistancesService @Inject() (){

  def distancesInformation: Try[DistanceInvocation] = {
    DistancesRepository.getDistances match{
      case Some(distances) => Try(distances)
      case None => Failure(DistancesException("there are no charged distances"))
    }
  }

}
