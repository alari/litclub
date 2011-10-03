@Typed package litclub.morphia.subject

import org.bson.types.ObjectId
import litclub.morphia.MorphiaDriver
import org.springframework.beans.factory.annotation.Autowired
import com.google.code.morphia.dao.BasicDAO

/**
 * @author Dmitry Kurinskiy
 * @since 10/1/11 2:06 PM
 */
class SubjectInfoDAO  extends BasicDAO<SubjectInfo, ObjectId> {
  @Autowired SubjectInfoDAO(MorphiaDriver morphiaDriver) {
    super(morphiaDriver.mongo, morphiaDriver.morphia, morphiaDriver.dbName)
  }

  SubjectInfo getById(String id) {
    if (!ObjectId.isValid(id)) return null
    getById(new ObjectId(id))
  }

  SubjectInfo getById(ObjectId id) {
    get(id)
  }

  SubjectInfo getBySubject(Subject subject) {
    SubjectInfo info = createQuery().filter("subject", subject).get()
    if(!info) {
      info = new SubjectInfo(subject: subject)
      save(info)
    }
    info
  }
}