package litclub

abstract class Subject {

  String domain

  Date dateCreated
  Date lastUpdated

  static constraints = {
    domain unique: true, nullable: false, matches: '^[-_a-zA-Z0-9]{4,16}$'
  }
}
