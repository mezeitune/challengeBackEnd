import com.mezeitune.repository.DistancesRepository
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}
import org.scalatest.mockito.MockitoSugar

/** Created by Matias Zeitune dic. 2019 **/
class DistancesRepositoryTest extends FlatSpec
  with Matchers
  with MockitoSugar
  with BeforeAndAfter {


  it should "return distances" in {
    DistancesRepository.saveNewDistance(1.1,"ARG")
    DistancesRepository.getDistances.get.nearestDistanceFromBsAs.distance shouldBe 1.1
    DistancesRepository.getDistances.get.furthestDistanceFromBsAs.distance shouldBe 1.1
    DistancesRepository.saveNewDistance(2.2,"CHI")
    DistancesRepository.getDistances.get.nearestDistanceFromBsAs.distance shouldBe 1.1
    DistancesRepository.getDistances.get.furthestDistanceFromBsAs.distance shouldBe 2.2
    DistancesRepository.getDistances.get.averageDistanceFromBsAs shouldBe 1.6500000000000001
  }

}
