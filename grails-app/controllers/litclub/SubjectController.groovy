package litclub

import grails.plugins.springsecurity.Secured
import litclub.morphia.NodeType

class SubjectController {

  static defaultAction = "index"

  def nodeService
  def springSecurityService

  def index = {
    [person: Person.get(request.subjectId)]
  }

  @Secured("ROLE_USER")
  def addNode = {NodeFormCommand command->
    if(request.post && !command.hasErrors()) {
      Subject subject = Subject.get(request.subjectId)
      Person person = (Person)springSecurityService.currentUser
      ServiceResponse resp = nodeService.addNode(subject, person, NodeType.getByName(params.type), command, params.containsKey("draft"))
      if(resp.ok) {
        flash.message = message(code: resp.messageCode)
        redirect uri: resp.redirectUri
        return
      } else {
        flash.error = message(code: resp.messageCode)
      }
    }
    render view: "nodeForm", model: [command: request.post ? command : new NodeFormCommand()]
  }

  def typeList = {
    // TODO: check inputs
    [nodes: nodeService.listSubjectNodes(request.subjectId, params.type, request.subjectId == springSecurityService.currentUser?.id), type: params.type]
  }

  def node = {
    // TODO: check if node exists and is viewable
    [node: nodeService.getByName(request.subjectId, params.node)]
  }
}

