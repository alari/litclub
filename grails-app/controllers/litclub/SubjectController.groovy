package litclub

class SubjectController {

  static defaultAction = "index"

  def nodeService

  def index = {
    [person: Person.get(request.subjectId)]
  }

  def addNode = {NodeFormCommand command->
    render view: "nodeForm", model: [command:command]
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