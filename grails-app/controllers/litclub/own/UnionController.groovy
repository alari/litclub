package litclub.own

import grails.plugins.springsecurity.Secured
import litclub.Person
import litclub.SubjectDomainService
import litclub.SubjectInfo
import litclub.Union
import litclub.morphia.PartyLevel
import org.springframework.beans.factory.annotation.Autowired

class UnionController {

  def springSecurityService
  def subjectDomainService
  def participationService

  //TODO: add UNION_CREATE permission check
  @Secured("ROLE_USER")
  def create = {CreateUnionCommand command ->
    if (request.post && !command.hasErrors()) {
      Person founder = (Person) springSecurityService.currentUser
      Union union = new Union(founder: founder, domain: command.domain,
          info: new SubjectInfo(frontText: command.text)).save(flush: true)
      // TODO: move it to afterInsert GORM event
      subjectDomainService.setDomain(union.id, union.domain)
      participationService.setParty(union, founder, PartyLevel.FOUNDER)
      redirect uri: "/" + union.domain
      return
    }

    [command: request.post ? command : new CreateUnionCommand()]
  }
}


class CreateUnionCommand {
  @Autowired
  SubjectDomainService subjectDomainService

  String domain
  String text = ""

  static constraints = {
    domain blank: false, validator: { value, command ->
      if (value) {
        if (command.subjectDomainService.checkDomainExists(value)) {
          return 'registerCommand.domain.unique'
        }
        if (!((String) value).matches('^[-_a-zA-Z0-9]{4,16}$')) {
          return "registerCommand.domain.invalid"
        }
      }
    }
    text blank: true, maxSize: 21845
  }
}