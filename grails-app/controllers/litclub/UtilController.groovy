package litclub

import litclub.morphia.subject.Person
import org.springframework.beans.factory.annotation.Autowired
import litclub.morphia.subject.PersonDAO

abstract class UtilController {
  def springSecurityService
  @Autowired PersonDAO personDao

  protected Person getCurrentPerson() {
    personDao.getById( springSecurityService.principal?.id?.toString() )
  }

  protected void setMessageCode(String code) {
    flash.message = message(code: code)
  }

  protected void setErrorCode(String code) {
    flash.error = message(code: code)
  }

  protected boolean hasNoRight(boolean rightCheck, String errCode = "permission.denied", String redirectUri = null) {
    if (!rightCheck) {
      errorCode = errCode
      redirect uri: redirectUri ?: "/"
      return true
    }
    false
  }
}
