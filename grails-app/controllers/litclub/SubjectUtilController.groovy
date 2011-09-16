package litclub

abstract class SubjectUtilController {

  def springSecurityService

  protected long getSubjectId(){
    request.subjectId
  }
  protected Subject getSubject(){
    Subject.get(subjectId)
  }
  protected Person getCurrentPerson(){
    (Person)springSecurityService.currentUser
  }
  protected void setMessageCode(String code){
    flash.message = message(code: code)
  }
  protected void setErrorCode(String code){
    flash.error = message(code: code)
  }
}
