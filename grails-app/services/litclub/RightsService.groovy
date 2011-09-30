package litclub

import litclub.morphia.NodeType
import litclub.morphia.Node

class RightsService {

  def springSecurityService

  private Person getPerson(){
    (Person)springSecurityService.getCurrentUser()
  }

  boolean canAddNode(Subject context, NodeType type=null) {
    true
  }

  boolean canEdit(Node node){
    true
  }

  boolean canMoveDraft(Node node){
    true
  }

  boolean canDelete(Node node) {
    true
  }
}
