package litclub.morphia.talk

import org.bson.types.ObjectId
import com.google.code.morphia.annotations.Id
import com.google.code.morphia.annotations.PrePersist
import com.google.code.morphia.Key

/**
 * @author Dmitry Kurinskiy
 * @since 10/1/11 12:33 PM
 */
class Talk {
  @Id ObjectId id

  String topic

  Date dateCreated = new Date()
  Date lastUpdated = new Date()

  long minPersonId
  long maxPersonId

  String lastPhraseLine = ''
  long lastPhrasePersonId = 0
  boolean lastPhraseNew = true

  Key<TalkPhrase> lastPhrase

  @PrePersist
  void prePersist() {
    lastUpdated = new Date();
  }
}
