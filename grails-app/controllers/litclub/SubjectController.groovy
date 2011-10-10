package litclub

import litclub.morphia.subject.SubjectInfoDAO
import org.springframework.beans.factory.annotation.Autowired
import litclub.morphia.subject.Union

class SubjectController extends SubjectUtilController {

  static defaultAction = "index"

  def nodeService
  def participationService
  @Autowired SubjectInfoDAO subjectInfoDao

  def index = {
    [
        subject: subject,
        parties: participationService.getLinkageParties(subject),
        info: subjectInfoDao.getBySubject(subject),
        participants: subject instanceof Union ? participationService.getParticipants(subject) : null]
  }

  def typeList = {
    // TODO: check inputs
    [nodes: nodeService.listSubjectNodes(subject, params.type, subjectId == currentPerson?.id?.toString()), type: params.type]
  }

  def node = {
    // TODO: check if node exists and is viewable
    [node: nodeService.getByName(subject, params.node)]
  }
}

