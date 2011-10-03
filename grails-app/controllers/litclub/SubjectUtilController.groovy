package litclub

import org.springframework.beans.factory.annotation.Autowired
import litclub.morphia.subject.SubjectDAO
import litclub.morphia.subject.Subject

abstract class SubjectUtilController extends UtilController {
  @Autowired SubjectDAO subjectDao

  protected String getSubjectId() {
    request.subjectId
  }

  protected Subject getSubject() {
    subjectDao.getById(subjectId)
  }
}
