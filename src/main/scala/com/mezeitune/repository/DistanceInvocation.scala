package com.mezeitune.repository

/** Created by Matias Zeitune dic. 2019 **/
case class DistanceInvocation(furthestDistanceFromBsAs: Distance,
                              distances: Seq[Double],
                              nearestDistanceFromBsAs: Distance) {
  def averageDistanceFromBsAs: Double = {
    if(distances.nonEmpty) distances.sum / distances.length else 0
  }
}
case class Distance(distance: Double, country: String)