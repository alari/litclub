package litclub

import litclub.morphia.node.NodeType
import litclub.morphia.node.Node
import litclub.morphia.subject.Person
import litclub.morphia.subject.PersonDAO

class RightsService {

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
}
