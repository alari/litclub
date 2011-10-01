package litclub

import litclub.morphia.linkage.SubjectLinkageBundleDAO
import org.springframework.beans.factory.annotation.Autowired
import litclub.morphia.linkage.SubjectLinkageBundle
import litclub.morphia.linkage.SubjectLinkage
import litclub.morphia.linkage.PartyLevel

class SubjectLinkageService {

  @Autowired
  SubjectLinkageBundleDAO subjectLinkageBundleDao

  SubjectLinkageBundle getBundle(long subjectId) {
    subjectLinkageBundleDao.getBySubject(subjectId)
  }

  SubjectLinkageBundle getBundle(Subject subject) {
    subjectLinkageBundleDao.getBySubject(subject)
  }

  List<SubjectLinkage> getLinkages(long subjectId) {
    subjectLinkageBundleDao.getBySubject(subjectId).linkages.collect {it.value}
  }

  List<SubjectLinkage> getLinkages(Subject subject) {
    getLinkages(subject.id)
  }


  void setLinkage(Subject base, Subject rel, PartyLevel level) {
    subjectLinkageBundleDao.setLinkage(base, new SubjectLinkage(subjectId: rel.id, level: level, info: ""))
  }

  SubjectLinkage getLinkage(Subject base, Subject rel) {
    subjectLinkageBundleDao.getLinkage(base, rel)
  }

  void remLinkage(Subject base, Subject rel) {
    subjectLinkageBundleDao.remLinkage(base, rel)
  }
}
