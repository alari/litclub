package litclub

class SubjectController {

  def index() {
    render request.subjectId
  }
}
