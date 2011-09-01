import geb.spock.GebReportingSpec
import pages.RegisterPage
import spock.lang.Stepwise
/**
 * @author Dmitry Kurinskiy
 * @since 31.08.11 13:22
 */
@Stepwise
class RegistrationStorySpec extends GebReportingSpec {
  def "can sign up"(){
    when:
      to RegisterPage
      domainInput.value = "test"
      emailInput.value = "test@123.dl"
      pwdInput.value = "test123\$"
      pwd2Input.value = "test123\$"
      submit.click()
    then:
      $("form input").size() == 0
  }
}
