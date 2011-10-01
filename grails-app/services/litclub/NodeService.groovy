package litclub

import litclub.morphia.node.NodeDAO
import org.springframework.beans.factory.annotation.Autowired
import litclub.morphia.node.Node
import litclub.morphia.node.NodeType
import litclub.morphia.node.NodeContent
import litclub.morphia.node.NodeContentDAO

import com.google.code.morphia.query.Query
import litclub.own.NodeFormCommand

class NodeService {

  @Autowired
  NodeDAO nodeDao
  @Autowired
  NodeContentDAO nodeContentDAO

  private static final List<String> forbiddenNames = [
      ""
  ] + NodeType.values().collect {it.toString()}

  def redisService

  ServiceResponse addNode(Subject context, Person author, NodeType type, NodeFormCommand command, boolean isDraft) {
    ServiceResponse resp = new ServiceResponse()
    // TODO: check context, author, their rights
    if (!context || !author || context.id != author.id) {
      return resp.setAttributes(ok: false, messageCode: "wrong context")
    }

    // TODO: create title from text, if title is blank
    String name = type.toString() + "-" + (countSubjectNodes(author.id, type) + 1)
    // TODO: create name from title, if it's available, otherwise from type & count

    Node node = new Node(
        subjectId: author.id,
        type: type,
        title: command.title,
        name: name,
        content: new NodeContent(text: command.text),
        isDraft: isDraft
    )

    nodeContentDAO.save(node.content)
    nodeDao.save(node)
    // TODO: check if we've saved the node correctly

    resp.setAttributes(ok: true, redirectUri: buildUri(node), messageCode: "okay, you've got it")
  }

  ServiceResponse edit(Node node, Person author, NodeFormCommand command, boolean isDraft) {
    ServiceResponse resp = new ServiceResponse()
    // TODO: check author, his rights
    if (!author || node.subjectId != author.id) {
      return resp.setAttributes(ok: false, messageCode: "wrong context")
    }

    node.title = command.title
    node.content.text = command.text
    node.isDraft = isDraft

    // TODO: add information about a new version in metadata

    nodeContentDAO.save(node.content)
    nodeDao.save(node)
    // TODO: check if we've saved the node correctly

    resp.setAttributes(ok: true, redirectUri: buildUri(node), messageCode: "okay, you've got it")
  }

  def delete(Node node) {
    nodeContentDAO.delete(node.content)
    nodeDao.delete(node)
  }

  long countSubjectNodes(long subjectId, NodeType type = null) {
    Query<Node> q = nodeDao.createQuery().filter("subjectId", subjectId)
    if (type) q.filter("type", type)
    q.countAll()
  }

  Node getByName(long subjectId, String name) {
    nodeDao.createQuery().filter("subjectId", subjectId).filter("name", name).get()
  }

  List<Node> listSubjectNodes(long subjectId, String type, boolean withDrafts = false) {
    Query<Node> q = nodeDao.createQuery().filter("subjectId", subjectId).filter("type", NodeType.getByName(type))
    if (!withDrafts) q.filter("isDraft", false)
    q.asList()
  }

  String buildUri(Node node, String action = null) {
    Subject subject = Subject.get(node.subjectId)
    "/" + subject.domain + "/" + node.name + (action ? "/" + action : "")
  }

  Node save(Node node) {
    nodeDao.save(node)
    node
  }
}
