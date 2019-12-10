package com.mezeitune.model

import com.mezeitune.repository.Distance

/** Created by Matias Zeitune dic. 2019 **/
case class DistancesResponse(furthestDistanceFromBsAs: Distance,
                             averageDistanceFromBsAs: Double,
                             nearestDistanceFromBsAs: Distance)