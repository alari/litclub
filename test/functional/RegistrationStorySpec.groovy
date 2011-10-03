import geb.spock.GebReportingSpec
import pages.RegisterPage
import spock.lang.Stepwise
import pages.RootPage
/**
 * @author Dmitry Kurinskiy
 * @since 31.08.11 13:22
 */
@Stepwise
class RegistrationStorySpec extends GebReportingSpec {
  def "can open reg page"(){
    when:
      to RegisterPage
    then:
      at RegisterPage
  }

  def "can sign up"(){
    when:
      to RegisterPage
      domainInput.value( "test" )
      emailInput.value( "test@123.dl" )
      pwdInput.value( "test123\$" )
      pwd2Input.value( "test123\$" )
      submit.click()
    then:
      $("form input").size() == 0
  }

  def "verify registration"(){
    when:
      verifyLink.click()
    then:
      at RootPage
      topNav[0].text() == "@test"
  }

  def "sign out"(){
    when:
      signInOut.click()
    then:
      at RootPage
      topNav[0].text() != "@test"
  }
}
