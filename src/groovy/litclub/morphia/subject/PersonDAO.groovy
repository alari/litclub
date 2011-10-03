package litclub.morphia.subject

import com.google.code.morphia.dao.BasicDAO
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import litclub.morphia.MorphiaDriver
import grails.plugins.springsecurity.SpringSecurityService
import com.mongodb.WriteResult
import com.google.code.morphia.Key

/**
 * @author Dmitry Kurinskiy
 * @since 10/1/11 2:12 PM
 */
class PersonDAO extends BasicDAO<Person, ObjectId> {
  private transient SpringSecurityService springSecurityService
  private transient SubjectDAO subjectDao

  @Autowired
  PersonDAO(MorphiaDriver morphiaDriver, SpringSecurityService springSecurityService, SubjectDAO subjectDao) {
    super(morphiaDriver.mongo, morphiaDriver.morphia, morphiaDriver.dbName)
    this.springSecurityService = springSecurityService
    this.subjectDao = subjectDao
  }

  Person getById(String id) {
    if (!ObjectId.isValid(id)) return null
    getById(new ObjectId(id))
  }

  Person getById(ObjectId id) {
    get(id)
  }

  Person getByDomain(String domain) {
    Subject p = subjectDao.getByDomain(domain)
    p instanceof Person ? (Person)p : null
  }

  Key<Person> save(Person person) {
    if(person.passwordChanged) {
      person.passwordHash = springSecurityService.encodePassword(person.password)
    }
    new Key<Person>(Person, subjectDao.save(person).id)
  }

  Key<Person> save(Person person, String oldDomain) {
    if(person.passwordChanged) {
      person.passwordHash = springSecurityService.encodePassword(person.password)
    }
    new Key<Person>(Person, subjectDao.save(person, oldDomain).id)
  }

  WriteResult delete(Person person) {
    subjectDao.delete(person)
  }
}
