package litclub

class Talk {

  String topic

  Date dateCreated
  Date lastUpdated

  TalkInfo info1
  TalkInfo info2

  static embedded = ['info1', 'info2']

  static hasMany = [phrases:TalkPhrase]

  static constraints = {
    topic blank: true, maxSize: 127
    'info1.line'(blank: true, maxSize: 255)
    'info2.line'(blank: true, maxSize: 255)
  }
}