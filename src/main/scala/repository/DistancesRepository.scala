package repository
import java.util.concurrent.ConcurrentHashMap

/** Created by Matias Zeitune dic. 2019 **/
object DistancesRepository {

  private var distances: Option[DistanceInvocation] = None

  def saveNewDistance(distance: Double, country: String): Unit = {
    this.synchronized {
      val newDistances = distances.map{
        case distanceInvocation: DistanceInvocation =>
          val nearest = if(distanceInvocation.nearestDistanceFromBsAs.distance < distance) Invocation(distanceInvocation.nearestDistanceFromBsAs.distance, distanceInvocation.nearestDistanceFromBsAs.country) else Invocation(distance,country)
          val furthest = if(distanceInvocation.furthestDistanceFromBsAs.distance > distance) Invocation(distanceInvocation.furthestDistanceFromBsAs.distance, distanceInvocation.furthestDistanceFromBsAs.country) else Invocation(distance,country)
          val average = (distanceInvocation.averageDistanceFromBsAs + distance) / 2
          DistanceInvocation(nearest,average,furthest)
      }.orElse{
        Option(DistanceInvocation(
          nearestDistanceFromBsAs = Invocation(distance, country),
          averageDistanceFromBsAs = distance,
          furthestDistanceFromBsAs = Invocation(distance, country)
        ))
      }
      distances = newDistances
    }
  }

  def getDistances: Option[DistanceInvocation] = this.distances
}
