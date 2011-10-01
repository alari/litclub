package litclub.morphia.talk

import com.google.code.morphia.annotations.Entity
import org.bson.types.ObjectId
import com.google.code.morphia.annotations.Id
import com.google.code.morphia.annotations.Reference

/**
 * @author Dmitry Kurinskiy
 * @since 10/1/11 12:36 PM
 */
@Entity
class TalkPhrase {
  @Id ObjectId id

  @Reference(lazy = true)
  Talk talk

  long personId

  String text

  Date dateCreated = new Date()
}
