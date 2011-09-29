package litclub.own

import grails.plugins.springsecurity.Secured
import litclub.ServiceResponse
import litclub.SubjectUtilController
import litclub.morphia.Node
import litclub.morphia.NodeType

@Secured(["ROLE_USER"])
class SubjectNodeController extends SubjectUtilController {

  def nodeService

  private Node getCurrentNode() {
    nodeService.getByName(subjectId, params.node)
  }

  def draft = {
        Node node = currentNode
    if (!node?.id) {
      errorCode = "not found"
      redirect uri: "/"
      return
    }
    // TODO: nodeService.canManipulate(currentPerson, node)
    node.isDraft = !node.isDraft
    nodeService.save(node)
    redirect uri: nodeService.buildUri(node)
  }

  @Secured("ROLE_USER")
  def addNode = {NodeFormCommand command ->
    if (request.post && !command.hasErrors()) {
      ServiceResponse resp = nodeService.addNode(subject, currentPerson, NodeType.getByName(params.type), command, params.containsKey("draft"))
      if (resp.ok) {
        messageCode = resp.messageCode
        redirect uri: resp.redirectUri
        return
      } else {
        errorCode = resp.messageCode
      }
    }
    render view: "nodeForm", model: [command: request.post ? command : new NodeFormCommand()]
  }

  def edit = {NodeFormCommand command ->
      Node node = currentNode
    // TODO: check node rights?
    if (request.post && !command.hasErrors()) {
      ServiceResponse resp = nodeService.edit(node, currentPerson, command, params.containsKey("draft"))
      if (resp.ok) {
        messageCode = resp.messageCode
        redirect uri: resp.redirectUri
        return
      } else {
        errorCode = resp.messageCode
        if (resp.redirectUri) {
          redirect uri: resp.redirectUri
          return
        }
      }
    }
    render view: "nodeForm", model: [command: request.post ? command : new NodeFormCommand(
        title: node.title,
        text: node.content.text
    )]
  }

  def delete = {
      Node node = currentNode
    // TODO: check user rights
    def type = node.type
    nodeService.delete(node)
    redirect uri: "/" + subject.domain + "/" + type.toString()
  }
}
