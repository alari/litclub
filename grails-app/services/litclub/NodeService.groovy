package litclub

import litclub.morphia.dao.NodeDAO
import org.springframework.beans.factory.annotation.Autowired

class NodeService {

  @Autowired
  NodeDAO nodeDao

  def redisService

  def serviceMethod() {

  }
}
