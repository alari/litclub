@Typed package litclub.morphia.talk

import org.bson.types.ObjectId
import com.google.code.morphia.annotations.Id
import com.google.code.morphia.annotations.PrePersist
import com.google.code.morphia.Key
import com.google.code.morphia.annotations.Entity
import litclub.morphia.subject.Person

/**
 * @author Dmitry Kurinskiy
 * @since 10/1/11 12:33 PM
 */
@Entity
class Talk {
  @Id ObjectId id

  String topic

  Date dateCreated = new Date()
  Date lastUpdated = new Date()

  Key<Person> minPerson
  Key<Person> maxPerson

  ObjectId getMinPersonId(){(ObjectId)minPerson.id}
  void setMinPersonId(ObjectId id){minPerson = new Key<Person>(Person, id)}
  void setMinPersonId(String id){setMinPersonId(new ObjectId(id))}

  ObjectId getMaxPersonId(){(ObjectId)maxPerson.id}
  void setMaxPersonId(ObjectId id){maxPerson = new Key<Person>(Person, id)}
  void setMaxPersonId(String id){setMaxPersonId(new ObjectId(id))}

  String lastPhraseLine = ''

  String lastPhrasePersonId
  boolean lastPhraseNew = true

  Key<TalkPhrase> lastPhrase
  void setLastPhrase(TalkPhrase phrase) {
    lastPhrase = new Key<TalkPhrase>(TalkPhrase, phrase.id)
  }
  ObjectId getLastPhraseId(){(ObjectId)lastPhrase.id}

  @PrePersist
  void prePersist() {
    lastUpdated = new Date();
  }
}
