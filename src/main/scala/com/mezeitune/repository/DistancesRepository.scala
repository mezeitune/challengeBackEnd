package com.mezeitune.repository

/** Created by Matias Zeitune dic. 2019 **/
object DistancesRepository {

  private var distances: Option[DistanceInvocation] = None

  def saveNewDistance(distance: Double, country: String): Unit = {
    this.synchronized {
      val newDistances = distances.map{
        case distanceInvocation: DistanceInvocation =>
          val nearest = if(distanceInvocation.nearestDistanceFromBsAs.distance < distance) distanceInvocation.nearestDistanceFromBsAs else Distance(distance,country)
          val furthest = if(distanceInvocation.furthestDistanceFromBsAs.distance > distance) distanceInvocation.furthestDistanceFromBsAs  else Distance(distance,country)
          val distances = distanceInvocation.distances :+ distance
          DistanceInvocation(
            nearestDistanceFromBsAs = nearest,
            distances = distances,
            furthestDistanceFromBsAs = furthest)
      }.orElse{
        Option(DistanceInvocation(
          nearestDistanceFromBsAs = Distance(distance, country),
          distances = Seq(distance),
          furthestDistanceFromBsAs = Distance(distance, country)
        ))
      }
      distances = newDistances
    }
  }

  def getDistances: Option[DistanceInvocation] = this.distances
}
