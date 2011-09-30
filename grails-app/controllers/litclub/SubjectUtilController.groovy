package litclub

abstract class SubjectUtilController extends UtilController {
  protected long getSubjectId() {
    request.subjectId
  }

  protected Subject getSubject() {
    Subject.get(subjectId)
  }
}
