package litclub

class TalkPhrase {

  static belongsTo = [talk:Talk]

  Person person

  String text

  boolean isNew = true

  Date dateCreated

  static constraints = {
    text maxSize: 21845
  }

  static mapping = {
    text sqlType: "TEXT"
  }
}
