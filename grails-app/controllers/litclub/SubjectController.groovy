package litclub

class SubjectController {

  static defaultAction = "index"

  def index = {
    [person: Person.get(request.subjectId)]
  }
}
