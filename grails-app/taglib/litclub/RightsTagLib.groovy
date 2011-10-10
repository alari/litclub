package litclub

import litclub.morphia.subject.Subject
import litclub.morphia.subject.Union

class RightsTagLib {
  static namespace = "right"

  def rightsService

  def canAdministrate = {attrs, body ->
    Subject subject = attrs.subject
    if(rightsService.canAdministrate(subject)) out << body()
  }

  def canJoin = {attrs,body ->
    Union subject = attrs.union
    if(rightsService.canJoin(subject)) out << body()
  }

  def canLeave = {attrs,body ->
    Union subject = attrs.union
    if(rightsService.canLeave(subject)) out << body()
  }

  def canRevokeParticipant = {attrs,body ->
    Union subject = attrs.union
    if(rightsService.canRevokeParticipant(subject)) out << body()
  }
}
