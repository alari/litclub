package litclub

import litclub.morphia.node.NodeType
import litclub.morphia.node.Node
import litclub.morphia.subject.Person
import litclub.morphia.subject.Subject
import litclub.morphia.subject.PersonDAO
import org.apache.log4j.Logger

class RightsService {
  static transactional = false
  private Logger log = Logger.getLogger(getClass())

  def springSecurityService
  PersonDAO personDao

  private Person getPerson() {
    personDao.getById( springSecurityService.getPrincipal()?.id?.toString() )
  }

  boolean canAddNode(Subject context, NodeType type = null) {
    true
  }

  boolean canEdit(Node node) {
    true
  }

  boolean canMoveDraft(Node node) {
    true
  }

  boolean canDelete(Node node) {
    true
  }

  boolean canAdministrate(Subject context) {
    true
  }
}
