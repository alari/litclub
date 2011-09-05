package litclub

class SubjectTagLib {
  static namespace = "sbj"

  def springSecurityService

  def talkService

  def link = { attrs ->
    Subject subject = null
    if(attrs.subject instanceof Subject) subject = attrs.subject;
    else if(attrs.id) subject = Subject.get(attrs.id)
    else if(springSecurityService.isLoggedIn()) subject = (Person)springSecurityService.getCurrentUser()

    if(!subject) {
      out << "no subject to link"
    } else {
      out << g.link(controller: "subject", action: "", params:[domain: subject.domain], "@"+subject.domain)
    }
  }

  def talkNewPhrases = {attrs->
    if(springSecurityService.isLoggedIn()) {
      Person person = (Person)springSecurityService.getCurrentUser()
      out << talkService.getNewCount(person.id)
    }
  }
}
