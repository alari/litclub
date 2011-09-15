package litclub

class SubjectController extends SubjectUtilController{

  static defaultAction = "index"

  def nodeService

  def index = {
    [person: subject]
  }

  def typeList = {
    // TODO: check inputs
    [nodes: nodeService.listSubjectNodes(subjectId, params.type, subjectId == currentPerson?.id), type: params.type]
  }

  def node = {
    // TODO: check if node exists and is viewable
    [node: nodeService.getByName(subjectId, params.node)]
  }
}

