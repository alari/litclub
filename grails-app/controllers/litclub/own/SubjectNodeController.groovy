package litclub.own

import grails.plugins.springsecurity.Secured
import litclub.ServiceResponse
import litclub.SubjectUtilController
import litclub.morphia.node.Node
import litclub.morphia.node.NodeType

@Secured(["ROLE_USER"])
class SubjectNodeController extends SubjectUtilController {

  def nodeService
  def rightsService

  private Node getCurrentNode() {
    nodeService.getByName(subject, params.node)
  }

  private boolean nodeNotFound(Node node) {
    if (!node?.id) {
      errorCode = "node.error.notFound"
      redirect uri: "/"
      return true
    }
    false
  }

  def draft = {
    Node node = currentNode
    if (nodeNotFound(node)) return;
    if (hasNoRight(rightsService.canMoveDraft(node))) return;

    node.isDraft = !node.isDraft
    nodeService.save(node)
    redirect uri: nodeService.buildUri(node)
  }

  def addNode = {NodeFormCommand command ->
    if (hasNoRight(rightsService.canAddNode(subject, NodeType.getByName(params.type)))) return;

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
    if (nodeNotFound(node)) return;
    if (hasNoRight(rightsService.canEdit(node))) return;

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
    if (nodeNotFound(node)) return;
    if (hasNoRight(rightsService.canDelete(node))) return;

    def type = node.type
    nodeService.delete(node)
    redirect uri: "/" + subject.domain + "/" + type.toString()
  }
}
