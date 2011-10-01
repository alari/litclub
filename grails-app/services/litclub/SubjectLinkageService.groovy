package litclub

import litclub.morphia.linkage.SubjectLinkageBundleDAO
import org.springframework.beans.factory.annotation.Autowired
import litclub.morphia.linkage.SubjectLinkageBundle
import litclub.morphia.linkage.SubjectLinkage
import litclub.morphia.linkage.PartyLevel
import litclub.morphia.subject.Subject
import org.apache.log4j.Logger

class SubjectLinkageService {
  static transactional = false
  private Logger log = Logger.getLogger(getClass())

  @Autowired
  SubjectLinkageBundleDAO subjectLinkageBundleDao

  SubjectLinkageBundle getBundle(Subject subject) {
    subjectLinkageBundleDao.getBySubject(subject)
  }

  List<SubjectLinkage> getLinkages(Subject subject) {
    subjectLinkageBundleDao.getBySubject(subject).linkages.collect {it.value}
  }


  void setLinkage(Subject base, Subject rel, PartyLevel level) {
    subjectLinkageBundleDao.setLinkage(base, new SubjectLinkage(subject: rel, level: level, info: ""))
  }

  SubjectLinkage getLinkage(Subject base, Subject rel) {
    subjectLinkageBundleDao.getLinkage(base, rel)
  }

  void remLinkage(Subject base, Subject rel) {
    subjectLinkageBundleDao.remLinkage(base, rel)
  }
}
