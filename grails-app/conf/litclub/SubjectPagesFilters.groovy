package litclub

class SubjectPagesFilters {
  def subjectDomainService

  def filters = {
    subjectPage(controller: "subject"){
      before = {
        System.err.println(request.requestURI)
        request.subjectId = subjectDomainService.getIdByDomain(params.domain)
        if(!request.subjectId) {
          redirect uri: "/"
          return false
        }
      }
    }
  }
}
