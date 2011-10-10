package litclub.own

import litclub.SubjectUtilController
import litclub.morphia.subject.SubjectInfoDAO
import org.springframework.beans.factory.annotation.Autowired

import litclub.morphia.subject.Union
import litclub.morphia.subject.SubjectInfo
import litclub.morphia.linkage.PartyLevel
import litclub.morphia.subject.Person
import litclub.morphia.subject.ParticipationPolicy

class SubjectAdmController extends SubjectUtilController{

  @Autowired SubjectInfoDAO subjectInfoDao
  def rightsService
  def participationService

  def participationPolicy = {
    ParticipationPolicy policy = ParticipationPolicy.getByName(params.participationPolicy)
    if(subject instanceof Union && rightsService.canAdministrate(subject)) {
      SubjectInfo info = subjectInfoDao.getBySubject(subject)
      info.participationPolicy = policy
      subjectInfoDao.save(info)
    }
    redirect controller: "subject", params: [domain: subject.domain]
  }

  def join = {
    Union union = (Union)subject
    if(rightsService.canJoin(union)) {
      participationService.setParty(union, currentPerson, PartyLevel.PARTICIPANT)
      setMessageCode("participation.join.success")
    } else setErrorCode("participation.join.failed")
    redirect controller: "subject", params: [domain: subject.domain]
  }

  def leave = {
    Union union = (Union)subject
    if(rightsService.canLeave(union)) {
      participationService.removeParty(union, currentPerson)
      setMessageCode("participation.leave.success")
    } else setErrorCode("participation.leave.failed")
    redirect controller: "subject", params: [domain: subject.domain]
  }

  def revokeParticipant = {
    Union union = (Union)subject
      Person person = personDao.getByDomain(params.d)
    if(rightsService.canRevokeParticipant(union, person)) {
      participationService.removeParty(union, person)
      setMessageCode("participation.revoke.success")
    } else setErrorCode("participation.revoke.failed")
    redirect controller: "subject", params: [domain: subject.domain]
  }
}
