@Typed package litclub.morphia.subject

import com.google.code.morphia.dao.BasicDAO
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import litclub.SubjectDomainService
import com.google.code.morphia.Key
import com.mongodb.WriteResult
import litclub.morphia.MorphiaDriver

/**
 * @author Dmitry Kurinskiy
 * @since 10/1/11 1:48 PM
 */
class SubjectDAO extends BasicDAO<Subject, ObjectId> {
  private transient SubjectDomainService subjectDomainService

  @Autowired
  SubjectDAO(MorphiaDriver morphiaDriver, SubjectDomainService subjectDomainService) {
    super(morphiaDriver.mongo, morphiaDriver.morphia, morphiaDriver.dbName)
    this.subjectDomainService = subjectDomainService
  }

  Subject getById(String id) {
    if (!ObjectId.isValid(id)) return null
    getById(new ObjectId(id))
  }

  Subject getById(ObjectId id) {
    get(id)
  }

  Subject getByDomain(String domain) {
    getById(subjectDomainService.getIdByDomain(domain))
  }

  Key<Subject> save(Subject subject) {
    if (subject.id == null) {
      Key<Subject> k = super.save(subject)
      if (subject.id) {
        subjectDomainService.setDomain(subject.id, subject.domain)
      }
      return k
    }
    super.save(subject)
  }

  Key<Subject> save(Subject subject, String oldDomain) {
    if (!subjectDomainService.setDomain(subject.id, subject.domain, oldDomain)) {
      subject.domain = oldDomain
    }
    save(subject)
  }

  WriteResult delete(Subject subject) {
    subjectDomainService.delDomain(subject.domain)
    super.delete(subject)
  }
}
