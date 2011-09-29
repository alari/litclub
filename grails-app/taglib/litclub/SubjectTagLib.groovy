package litclub

class SubjectTagLib {
  static namespace = "sbj"

  def springSecurityService

  def addNode = {attrs,body->
    attrs.params = [
        type: attrs.remove("type"),
        domain: attrs.remove("domain") ?: springSecurityService.currentUser.domain,
        ]
    attrs.controller = "subjectNode"
    attrs.action = "addNode"
    out << g.link(attrs, body)
  }

  def link = { attrs ->
    Subject subject = null
    if (attrs.subject instanceof Subject) subject = attrs.subject;
    else if (attrs.id) subject = Subject.get(attrs.id)
    else if (springSecurityService.isLoggedIn()) subject = (Person) springSecurityService.getCurrentUser()

    if (!subject) {
      out << "no subject to link"
    } else {
      String prefix = subject instanceof Person ? "@" : "#"
      out << g.link(controller: "subject", action: "", params: [domain: subject.domain], prefix + subject.domain)
    }
  }
}
