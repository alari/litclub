package litclub.own

import litclub.SubjectUtilController
import litclub.morphia.subject.SubjectInfoDAO
import org.springframework.beans.factory.annotation.Autowired
import litclub.morphia.subject.MembershipPolicy
import litclub.morphia.subject.Union
import litclub.morphia.subject.SubjectInfo

class SubjectAdmController extends SubjectUtilController{

  @Autowired SubjectInfoDAO subjectInfoDao
  def rightsService

  def membershipPolicy = {
    MembershipPolicy policy = MembershipPolicy.getByName(params.membershipPolicy)
    if(subject instanceof Union && rightsService.canAdministrate(subject)) {
      SubjectInfo info = subjectInfoDao.getBySubject(subject)
      info.membershipPolicy = policy
      subjectInfoDao.save(info)
    }
    redirect controller: "subject", params: [domain: subject.domain]
  }
}
