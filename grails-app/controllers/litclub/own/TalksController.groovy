package litclub.own

import grails.plugins.springsecurity.Secured
import litclub.Person
import litclub.Talk
import litclub.TalkPhrase

@Secured(["ROLE_USER"])
class TalksController {

  def springSecurityService
  def talkService
  def subjectDomainService

  def index() {
    Person person = (Person) springSecurityService.currentUser

    [talks: talkService.getTalks(person.id, -20, -1)]
  }

  def talk(long id) {
    Talk talk = Talk.get(id)
    long personId = springSecurityService.currentUser.id
    if (!talk || !personId in [talk.maxPersonId, talk.minPersonId]) {
      flash.error = "error: ${talk}"
      redirect uri: "/"
      return
    }
    long targetId = personId == talk.maxPersonId ? talk.minPersonId : talk.maxPersonId
    List newPhrases = talkService.getTalkNewIds(personId, talk.id)
    long firstNew = newPhrases.size() ? newPhrases.last() as long : 0
    newPhrases.addAll(talkService.getTalkNewIds(targetId, talk.id))

    List<TalkPhrase> phrases = talkService.getPhrasesWithNew(id, firstNew, 10, 2)

    [phrases: phrases, talk: talk, firstNew: firstNew, newPhrases: newPhrases]
  }

  def create(CreateTalkCommand command) {
    Person person = (Person) springSecurityService.currentUser

    if (request.post && !command.hasErrors()) {
      // TODO: check if it is a person
      long targetId = subjectDomainService.getIdByDomain(command.targetDomain)
      if (targetId) {
        talkService.sendPhrase(command.text, person.id, targetId, command.topic)
        flash.message = "Phrase sent"
        redirect action: "index"
        return
      }
    }
    [command: request.post ? command : new CreateTalkCommand()]
  }

  def sayPhrase(SayPhraseCommand command) {
    Talk talk = Talk.get(command.talkId)

    if (!talkAccessable(talk)) {
      redirect uri: "/"
      return
    }

    talkService.sendPhrase(command.text, springSecurityService.currentUser.id as long, talk)
    redirect action: "talk", params: [id: talk.id]
  }

  private boolean talkAccessable(Talk talk) {
    springSecurityService.currentUser.id in [talk.minPersonId, talk.maxPersonId]
  }
}

class SayPhraseCommand {
  long talkId
  String text

  static constraints = {
    text maxSize: 21845, blank: false
    talkId nullable: false
  }
}

class CreateTalkCommand {
  String targetDomain
  String topic
  String text

  static constraints = {
    topic maxSize: 127
    text maxSize: 21845, blank: false
    targetDomain matches: '^[-_a-zA-Z0-9]{4,16}$'
  }
}