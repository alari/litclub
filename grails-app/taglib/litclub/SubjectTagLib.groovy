package litclub

import litclub.morphia.subject.Person
import litclub.morphia.subject.Subject
import org.springframework.beans.factory.annotation.Autowired
import litclub.morphia.subject.SubjectDAO
import litclub.morphia.subject.PersonDAO

class SubjectTagLib {
  static namespace = "sbj"

  def springSecurityService
  @Autowired SubjectDAO subjectDao
  @Autowired PersonDAO personDao

  def addNode = {attrs,body->
    attrs.params = [
        type: attrs.remove("type"),
        domain: attrs.remove("domain") ?: springSecurityService.principal.username
        ]
    attrs.controller = "subjectNode"
    attrs.action = "addNode"
    out << g.link(attrs, body)
  }

  def link = { attrs ->
    Subject subject = null
    if (attrs.subject instanceof Subject) subject = attrs.subject;
    else if (attrs.id) subject = subjectDao.getById(attrs.id.toString())
    else if (springSecurityService.isLoggedIn()) subject = personDao.getById(springSecurityService.principal.id?.toString())

    if (!subject) {
      out << "no subject to link"
    } else {
      String prefix = subject instanceof Person ? "@" : "#"
      out << g.link(controller: "subject", action: "", params: [domain: subject.domain], prefix + subject.domain)
    }
  }
}
