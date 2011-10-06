package litclub

import litclub.morphia.subject.Subject

class RightsTagLib {
  static namespace = "right"

  def rightsService

  def canAdministrate = {attrs, body ->
    Subject subject = attrs.subject
    if(rightsService.canAdministrate(subject)) out << body()
  }
}
