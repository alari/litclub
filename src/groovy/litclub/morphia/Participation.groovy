package litclub.morphia

import com.google.code.morphia.annotations.Entity
import org.bson.types.ObjectId
import com.google.code.morphia.annotations.Id
import com.google.code.morphia.annotations.Indexed
import com.google.code.morphia.annotations.Embedded

/**
 * @author Dmitry Kurinskiy
 * @since 18.09.11 13:21
 */
@Entity
class Participation {
  @Id ObjectId id

  @Indexed
  long subjectId

  @Embedded
  Map<String,Party> parties = [:]
}

