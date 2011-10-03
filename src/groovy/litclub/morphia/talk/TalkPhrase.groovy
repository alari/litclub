@Typed package litclub.morphia.talk

import com.google.code.morphia.annotations.Entity
import org.bson.types.ObjectId
import com.google.code.morphia.annotations.Id
import com.google.code.morphia.annotations.Reference
import com.google.code.morphia.Key
import litclub.morphia.subject.Person

/**
 * @author Dmitry Kurinskiy
 * @since 10/1/11 12:36 PM
 */
@Entity
class TalkPhrase {
  @Id ObjectId id

  @Reference(lazy=true)
  Talk talk

  private Key<Person> person

  void setPerson(Person person) {
    this.person = new Key<Person>(Person, person.id)
  }

  ObjectId getPersonId() {
    (ObjectId)person.id
  }

  void setPersonId(ObjectId id) {
    person = new Key<Person>(Person, id)
  }

  void setPersonId(String id) {
    setPersonId(new ObjectId(id))
  }

  String text

  Date dateCreated = new Date()
}
