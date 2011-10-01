package litclub.morphia.linkage

import org.bson.types.ObjectId
import com.google.code.morphia.dao.BasicDAO
import org.springframework.beans.factory.annotation.Autowired
import litclub.morphia.MorphiaDriver
import litclub.morphia.linkage.SubjectLinkageBundle
import litclub.Subject
import litclub.morphia.linkage.SubjectLinkage

/**
 * @author Dmitry Kurinskiy
 * @since 20.09.11 13:41
 */
class SubjectLinkageBundleDAO extends BasicDAO<SubjectLinkageBundle, ObjectId> {
  @Autowired
  SubjectLinkageBundleDAO(MorphiaDriver morphiaDriver) {
    super(morphiaDriver.mongo, morphiaDriver.morphia, morphiaDriver.dbName)
  }

  SubjectLinkageBundle getById(String id) {
    if (!ObjectId.isValid(id)) return null
    getById(new ObjectId(id))
  }

  SubjectLinkageBundle getById(ObjectId id) {
    get(id)
  }

  SubjectLinkageBundle getBySubject(Subject subject) {
    getBySubject(subject.id)
  }

  SubjectLinkageBundle getBySubject(long subjectId) {
    SubjectLinkageBundle p = createQuery().filter("subjectId", subjectId).get()
    if (!p) {
      p = new SubjectLinkageBundle()
      p.subjectId = subjectId
      p.linkages = [:]
      save(p)
    }
    p
  }

  SubjectLinkage getLinkage(Subject base, Subject rel) {
    getBySubject(base).linkages.get(rel.id.toString())
  }

  void setLinkage(Subject base, SubjectLinkage party) {
    SubjectLinkageBundle p = getBySubject(base)
    p.linkages.put(party.subjectId.toString(), party)
    save(p)
  }

  void remLinkage(Subject base, Subject rel) {
    SubjectLinkageBundle p = getBySubject(base)
    p.linkages.remove(rel.id.toString())
    save(p)
  }
}
