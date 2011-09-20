@Typed package litclub.morphia.dao

import com.google.code.morphia.dao.BasicDAO
import litclub.morphia.MorphiaDriver
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import litclub.morphia.NodeContent

/**
 * @author Dmitry Kurinskiy
 * @since 08.09.11 14:59
 */
class NodeContentDAO extends BasicDAO<NodeContent, ObjectId>{
  @Autowired NodeContentDAO(MorphiaDriver morphiaDriver){
    super(morphiaDriver.mongo, morphiaDriver.morphia, morphiaDriver.dbName)
  }

  NodeContent getById(String id) {
    if(!ObjectId.isValid(id)) return null
    getById(new ObjectId(id))
  }

  NodeContent getById(ObjectId id) {
    get(id)
  }
}
