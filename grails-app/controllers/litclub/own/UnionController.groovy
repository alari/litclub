package litclub.own

import grails.plugins.springsecurity.Secured
import litclub.morphia.subject.Person
import litclub.SubjectDomainService
import litclub.morphia.subject.SubjectInfo
import litclub.morphia.subject.Union
import litclub.morphia.linkage.PartyLevel
import org.springframework.beans.factory.annotation.Autowired
import litclub.UtilController
import litclub.morphia.subject.SubjectDAO
import litclub.morphia.subject.SubjectInfoDAO

class UnionController extends UtilController {

  def subjectDomainService
  def participationService
  def rightsService

  @Autowired SubjectDAO subjectDao
  @Autowired SubjectInfoDAO subjectInfoDao

  @Secured(["ROLE_USER", "ROLE_CREATE_UNION"])
  def create = {CreateUnionCommand command ->

    if (request.post && !command.hasErrors()) {
      Person founder = currentPerson
      Union union = new Union(founder: founder, domain: command.domain)
      subjectDao.save(union)

      subjectInfoDao.save( new SubjectInfo(frontText: command.text, subject: union) )

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