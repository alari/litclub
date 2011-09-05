package litclub

class Talk {

  String topic

  Date dateCreated
  Date lastUpdated

  long minPersonId
  long maxPersonId

  String lastPhraseLine = ''
  long lastPhrasePersonId = 0
  boolean lastPhraseNew = true
  long lastPhraseId = 0

  static hasMany = [phrases:TalkPhrase]

  static constraints = {
    topic blank: true, maxSize: 127
    lastPhraseLine blank: true, maxSize: 255
  }
}