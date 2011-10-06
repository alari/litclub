package litclub.own

import litclub.SubjectUtilController
import litclub.morphia.subject.SubjectInfoDAO
import org.springframework.beans.factory.annotation.Autowired
import litclub.morphia.subject.MembershipPolicy
import litclub.morphia.subject.Union
import litclub.morphia.subject.SubjectInfo
import litclub.morphia.linkage.PartyLevel

class SubjectAdmController extends SubjectUtilController{

  @Autowired SubjectInfoDAO subjectInfoDao
  def rightsService
  def participationService

  def membershipPolicy = {
    MembershipPolicy policy = MembershipPolicy.getByName(params.membershipPolicy)
    if(subject instanceof Union && rightsService.canAdministrate(subject)) {
      SubjectInfo info = subjectInfoDao.getBySubject(subject)
      info.membershipPolicy = policy
      subjectInfoDao.save(info)
    }
    redirect controller: "subject", params: [domain: subject.domain]
  }

  def join = {
    Union union = (Union)subject
    if(rightsService.canJoin(union)) {
      participationService.setParty(union, currentPerson, PartyLevel.PARTICIPANT)
      setMessageCode("member success")
    } else setErrorCode("membership unavailable, sry")
    redirect controller: "subject", params: [domain: subject.domain]
  }
}
