package com.mezeitune.utils

/** Created by Matias Zeitune dic. 2019 **/
object GeoDistanceUtil {

  def distance(lat1: Double, lat2: Double, lon1: Double, lon2: Double): Double =
  {
    val radiansLon1 = Math.toRadians(lon1);
    val radiansLon2 = Math.toRadians(lon2);
    val radiansLat1 = Math.toRadians(lat1);
    val radiansLat2 = Math.toRadians(lat2);

    // Haversine formula
    val dlon: Double = radiansLon2 - radiansLon1;
    val dlat: Double = radiansLat2 - radiansLat1;
    val a: Double = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2),2)

    val c: Double = 2 * Math.asin(Math.sqrt(a))

    // Radius of earth in kilometers. Use 3956
    // for miles 6371
    val r: Double = 3956

    // calculate the result
    c * r
  }

}
