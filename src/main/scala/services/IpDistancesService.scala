package services

import javax.inject.{Inject, Singleton}
import model.IpDistancesResponse

import scala.util.Try

/** Created by Matias Zeitune dic. 2019 **/
@Singleton
class IpDistancesService @Inject() (){

  def distancesInformation: Try[IpDistancesResponse] = Try {

    IpDistancesResponse (
      furthestDistanceFromBsAs = 3.4,
      averageDistanceFromBsAs = 4.5,
      nearestDistanceFromBsAs = 3.5
    )
  }

}
