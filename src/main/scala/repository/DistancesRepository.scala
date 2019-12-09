package repository
import java.util.concurrent.ConcurrentHashMap

/** Created by Matias Zeitune dic. 2019 **/
object DistancesRepository {

  private val distances: ConcurrentHashMap[String, DistanceInvocation] = new ConcurrentHashMap[String, DistanceInvocation]()

  def saveNewDistance(distance: Double, country: String): DistanceInvocation = {
    val invocations: Int = getDistances.getOrDefault(country, DistanceInvocation(0.0, 0)).invocations
    val distanceInvocation = DistanceInvocation(distance, invocations + 1)
    distances.put(country, distanceInvocation)
  }

  def getDistances: ConcurrentHashMap[String, DistanceInvocation] = distances
}
