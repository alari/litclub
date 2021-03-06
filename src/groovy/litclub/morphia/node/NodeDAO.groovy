@Typed package litclub.morphia.node

import litclub.morphia.node.Node
import org.bson.types.ObjectId
import com.google.code.morphia.dao.BasicDAO
import litclub.morphia.MorphiaDriver
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author Dmitry Kurinskiy
 * @since 08.09.11 14:59
 */
class NodeDAO extends BasicDAO<Node, ObjectId> {
  @Autowired NodeDAO(MorphiaDriver morphiaDriver) {
    super(morphiaDriver.mongo, morphiaDriver.morphia, morphiaDriver.dbName)
  }

  Node getById(String id) {
    if (!ObjectId.isValid(id)) return null
    getById(new ObjectId(id))
  }

  Node getById(ObjectId id) {
    get(id)
  }
}
