import java.util.TimeZone

import com.mezeitune.clients.{Ip2CountryClient, RestCountryClient}
import com.mezeitune.dtos.{Currencie, Ip2CountryResponse, Language, RestCountryResponse}
import com.mezeitune.facades.FixerFacade
import com.mezeitune.model.CountryCurrency
import com.mezeitune.services.IpCountryInformationService
import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

import scala.util.Success

/** Created by Matias Zeitune ago. 2019 **/
class IpInformationTest
  extends FlatSpec
    with Matchers
    with MockitoSugar
    with BeforeAndAfter {

  val mockedIp2CountryClient = mock[Ip2CountryClient]
  val mockedRestCountryClient = mock[RestCountryClient]
  val mockedFixerFacade = mock[FixerFacade]

  behavior of "ip country information"

  before {
    when(mockedIp2CountryClient.getCountryInfo("185.86.151.11")).thenReturn(Success(Ip2CountryResponse("GBR","United Kingdom of Great Britain and Northern Ireland")))
    when(mockedRestCountryClient.getCountryInfo("GBR")).thenReturn(
      Success(
        RestCountryResponse("United Kingdom of Great Britain and Northern Ireland",
          "London",
          Seq(Language("English")),
          Seq(Currencie("GBP","British pound","£")),
          Seq(54, -2),
          Seq(TimeZone.getTimeZone("England/London"))
        )
      )
    )
    when(mockedFixerFacade.getCurrency("USD","GBP")).thenReturn(Success(2.0))

  }

  val ipCountryInformationService: IpCountryInformationService = new IpCountryInformationService(mockedIp2CountryClient,mockedRestCountryClient,mockedFixerFacade)

  it should "return information of england ip" in {
    val ipInfoResponse = ipCountryInformationService.ipInformation("185.86.151.11").get
    ipInfoResponse.countryName shouldBe "United Kingdom of Great Britain and Northern Ireland"
    ipInfoResponse.isoCountryCode shouldBe "GBR"
    ipInfoResponse.officialLanguages shouldBe Seq("English")
    ipInfoResponse.estimatedDistance shouldBe 7609.181870219288
    ipInfoResponse.currencies shouldBe Seq(CountryCurrency(localCurrency = "British pound", euroQuote = 2.0))
  }


}
