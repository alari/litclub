package litclub

class SubjectTagLib {
  static namespace = "sbj"

  def springSecurityService

  def link = { attrs ->
    Subject subject = null
    if(attrs.subject instanceof Subject) subject = attrs.subject;
    else if(springSecurityService.isLoggedIn()) subject = (Person)springSecurityService.getCurrentUser()

    if(!subject) {
      out << "no subject to link"
    } else {
      out << g.link(controller: "subject", action: "", params:[domain: subject.domain], "@"+subject.domain)
    }
  }
}
