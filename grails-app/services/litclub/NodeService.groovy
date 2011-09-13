package litclub

import litclub.morphia.dao.NodeDAO
import org.springframework.beans.factory.annotation.Autowired
import litclub.morphia.Node
import litclub.morphia.NodeType
import litclub.morphia.NodeContent
import litclub.morphia.dao.NodeContentDAO

class NodeService {

  @Autowired
  NodeDAO nodeDao
  @Autowired
  NodeContentDAO nodeContentDAO

  def redisService

  ServiceResponse addNode(Subject context, Person author, NodeType type, NodeFormCommand command) {
    ServiceResponse resp = new ServiceResponse()
    // check context, author, their rights
    if(!context || !author || context.id!=author.id) {
      return resp.setAttributes(ok:false,messageCode: "wrong context")
    }

    Node node = new Node(
        subjectId: author.id,
        type: type,
        title: command.title,
        content: new NodeContent(text: command.text)
    )

    nodeContentDAO.save(node.content)
    nodeDao.save(node)

    resp.setAttributes(ok: true, redirectUri: "/", messageCode: "okay")
  }
}
