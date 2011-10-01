package litclub.own

import grails.plugins.springsecurity.Secured
import litclub.Person
import litclub.morphia.talk.Talk
import litclub.morphia.talk.TalkPhrase
import litclub.UtilController

@Secured(["ROLE_USER"])
class TalksController extends UtilController {

  def talkService
  def subjectDomainService
  def rightsService

  def index() {
    Person person = currentPerson

    [talks: talkService.getTalks(person.id, -20, -1)]
  }

  def talk(String id) {
    Talk talk = talkService.getTalk(id)
    long personId = currentPerson.id
    // TODO: correct errors & redirects
    if (!talk || !talkAccessable(talk)) {
      errorCode = "error: ${talk}"
      redirect uri: "/"
      return
    }
    long targetId = personId == talk.maxPersonId ? talk.minPersonId : talk.maxPersonId
    List newPhrases = talkService.getTalkNewIds(personId, talk.id)
    String firstNew = newPhrases.size() ? newPhrases.last() : ""
    newPhrases.addAll(talkService.getTalkNewIds(targetId, talk.id))

    List<TalkPhrase> phrases = talkService.getPhrasesWithNew(id, firstNew, 10, 2)

    [phrases: phrases, talk: talk, firstNew: firstNew, newPhrases: newPhrases]
  }

  @Secured(["ROLE_USER", "ROLE_TALK"])
  def create(CreateTalkCommand command) {
    Person person = currentPerson

    if (request.post && !command.hasErrors()) {
      // TODO: check if it is a person
      long targetId = subjectDomainService.getIdByDomain(command.targetDomain)
      if (targetId) {
        talkService.sendPhrase(command.text, person.id, targetId, command.topic)
        // TODO: flash.message
        messageCode = "Phrase sent"
        redirect action: "index"
        return
      }
    }
    [command: request.post ? command : new CreateTalkCommand()]
  }

  @Secured(["ROLE_USER", "ROLE_TALK"])
  def sayPhrase(SayPhraseCommand command) {
    Talk talk = talkService.getTalk(command.talkId)

    if (!talkAccessable(talk)) {
      redirect uri: "/"
      return
    }

    talkService.sendPhrase(command.text, currentPerson.id as long, talk)
    redirect action: "talk", params: [id: talk.id]
  }

  private boolean talkAccessable(Talk talk) {
    currentPerson.id in [talk.minPersonId, talk.maxPersonId]
  }
}

class SayPhraseCommand {
  String talkId
  String text

  static constraints = {
    text maxSize: 21845, blank: false
    talkId nullable: false, minSize: 24, maxSize: 24
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