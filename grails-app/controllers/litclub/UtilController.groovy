package litclub

abstract class UtilController {
  def springSecurityService

  protected Person getCurrentPerson() {
    (Person) springSecurityService.currentUser
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
