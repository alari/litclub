package litclub.own

import litclub.Person
import litclub.Talk

class TalksController {

  def springSecurityService
  def talkService
  def subjectDomainService

  def index() {
    Person person = (Person)springSecurityService.currentUser

    [talks: talkService.getTalks(person.id, 0, -1)]
  }

  def talk(long id){
    Talk talk = Talk.get(id)
    if(!talk || !springSecurityService.currentUser.id in [talk.maxPersonId, talk.minPersonId]) {
      flash.error = "error: ${talk}"
      redirect uri: "/"
      return
    }
    [phrases: talkService.getPhrases(id, 0, -1), talk: talk]
  }

  def create(CreateTalkCommand command){
    Person person = (Person)springSecurityService.currentUser

    if(command && request.post && command.validate()) {
      // TODO: check if it is a person
      long targetId = subjectDomainService.getIdByDomain(command.targetDomain)
      if(targetId) {
        talkService.sendPhrase(command.text, person.id, targetId, command.topic)
        flash.message = "Phrase sent"
        redirect action: "index"
        return
      }
    }
    if(!command) command = new CreateTalkCommand()

    [command: command]
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