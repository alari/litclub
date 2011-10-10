package litclub

import litclub.morphia.node.NodeType
import litclub.morphia.node.Node
import litclub.morphia.subject.Person
import litclub.morphia.subject.Subject
import litclub.morphia.subject.PersonDAO
import org.apache.log4j.Logger
import litclub.morphia.subject.Union
import litclub.morphia.subject.SubjectInfoDAO
import litclub.morphia.linkage.PartyLevel
import litclub.morphia.subject.ParticipationPolicy

class RightsService {
  static transactional = false
  private Logger log = Logger.getLogger(getClass())

  def springSecurityService
  def participationService
  PersonDAO personDao
  SubjectInfoDAO subjectInfoDao

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

  boolean canJoin(Union union) {
    if(!springSecurityService.isLoggedIn()) return false
    if(participationService.getLevel(union, person) != PartyLevel.NOBODY) return false
    if(subjectInfoDao.getBySubject(union).participationPolicy != ParticipationPolicy.OPEN) return false
    true
  }

  boolean canLeave(Union union) {
    if(!springSecurityService.isLoggedIn()) return false
    PartyLevel level = participationService.getLevel(union, person)
    if(level.is(PartyLevel.FOUNDER)) return false
    return level.hasParticipant()
  }

  boolean canRevokeParticipant(Union union, Person person) {
    if(!springSecurityService.isLoggedIn()) return false
    PartyLevel level = participationService.getLevel(union, person)
    level.is(PartyLevel.FOUNDER)
  }
}
