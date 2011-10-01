package litclub

import litclub.morphia.node.NodeDAO
import litclub.morphia.node.Node
import org.springframework.beans.factory.annotation.Autowired

class NodeTagLib {
  static namespace = "nd"

  @Autowired
  NodeDAO nodeDao

  def nodeService

  def link = {attrs, body->
    Node node = attrs.node
    out << g.link(uri: nodeService.buildUri(node, attrs.action), body()?:node.title.encodeAsHTML())
  }

  def uri = {attrs->
    out << nodeService.buildUri(attrs.node, attrs.action)
  }
}
