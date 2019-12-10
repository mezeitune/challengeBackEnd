import java.util.TimeZone

import com.mezeitune.clients.{FixerClient, Ip2CountryClient, RestCountryClient}
import com.mezeitune.dtos.{Currencie, FixerResponse, Ip2CountryResponse, Language, RestCountryResponse}
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
  val mockedFixerClient = mock[FixerClient]

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
    when(mockedFixerClient.getCurrency("USD")).thenReturn(Success(FixerResponse(Map("GBP" -> 2.0))))

  }

  val ipCountryInformationService: IpCountryInformationService = new IpCountryInformationService(mockedIp2CountryClient, mockedRestCountryClient, mockedFixerClient)

  it should "return information of england ip" in {
    val ipInfoResponse = ipCountryInformationService("185.86.151.11").get
    ipInfoResponse.countryName shouldBe "United Kingdom of Great Britain and Northern Ireland"
    ipInfoResponse.isoCountryCode shouldBe "GBR"
    ipInfoResponse.officialLanguages shouldBe Seq("English")
    ipInfoResponse.estimatedDistance shouldBe 7609.181870219288
    ipInfoResponse.currencies shouldBe Seq(CountryCurrency(localCurrency = "British pound", euroQuote = 2.0))
  }

  it should "return invalid ip" in {
    ipCountryInformationService.isIpValid("12") shouldBe false
  }

  it should "return valid ip" in {
    ipCountryInformationService.isIpValid("185.86.151.11") shouldBe true
  }

}
