package repository

/** Created by Matias Zeitune dic. 2019 **/
case class DistanceInvocation(furthestDistanceFromBsAs: Invocation,
                              averageDistanceFromBsAs: Double,
                              nearestDistanceFromBsAs: Invocation)

case class Invocation(distance: Double, country: String)