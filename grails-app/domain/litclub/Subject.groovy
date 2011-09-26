package litclub

abstract class Subject {

  String domain

  Date dateCreated
  Date lastUpdated

  SubjectInfo info

  transient subjectDomainService

  def beforeDelete() {
    subjectDomainService.delDomain(domain)
  }

  def afterInsert(){
    // TODO: remove this hack
    if(!this instanceof Union) subjectDomainService.setDomain((long)this.getProperty("id"), domain)
  }

  def beforeUpdate() {
    if(isDirty("domain")) {
      if(!subjectDomainService.checkDomainExists(domain)) {
        subjectDomainService.setDomain(id, domain, (String)getPersistentValue("domain"))
      }
    }
  }

  static constraints = {
    domain unique: true, nullable: false, matches: '^[-_a-zA-Z0-9]{4,16}$'
    //info unique: true, nullable: false
  }
}
