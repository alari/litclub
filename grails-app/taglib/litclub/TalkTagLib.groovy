package litclub

class TalkTagLib {
  static namespace = "talk"

  def talkService
  def springSecurityService

  def newCount = {attrs->
    if(!springSecurityService.isLoggedIn()) return;

    def personId = springSecurityService.principal.id

    if(attrs.talkId) {
      out << talkService.getTalkNewCount(personId, attrs.talkId)
    } else {
      out << talkService.getNewCount(personId)
    }
  }
}
