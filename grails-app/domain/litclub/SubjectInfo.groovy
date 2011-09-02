package litclub

class SubjectInfo {

  static belongsTo = [subject:Subject]

  String frontText = ""

  static constraints = {
    frontText blank: true
  }
}
