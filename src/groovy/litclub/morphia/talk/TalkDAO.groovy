package litclub.morphia.talk

import org.bson.types.ObjectId
import com.google.code.morphia.dao.BasicDAO
import org.springframework.beans.factory.annotation.Autowired
import litclub.morphia.MorphiaDriver

/**
 * @author Dmitry Kurinskiy
 * @since 10/1/11 12:42 PM
 */
class TalkDAO extends BasicDAO<Talk, ObjectId> {
  @Autowired TalkDAO(MorphiaDriver morphiaDriver) {
    super(morphiaDriver.mongo, morphiaDriver.morphia, morphiaDriver.dbName)
  }

  Talk getById(String id) {
    if (!ObjectId.isValid(id)) return null
    getById(new ObjectId(id))
  }

  Talk getById(ObjectId id) {
    get(id)
  }
}
