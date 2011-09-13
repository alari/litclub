package litclub

class SubjectController {

  static defaultAction = "index"

  def nodeService

  def index = {
    [person: Person.get(request.subjectId)]
  }

  def addNode = {NodeFormCommand command->
    if(request.post) {
      // handle the command object
    }
    render view: "nodeForm", model: [command: request.post ? command : new NodeFormCommand()]
  }
}

class NodeFormCommand{
  String title
  String text

  static constraints = {
    title blank: false
    text blank: false
  }
}