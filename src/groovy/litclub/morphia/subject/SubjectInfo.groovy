@Typed package litclub.morphia.subject

import com.google.code.morphia.annotations.Entity
import org.bson.types.ObjectId
import com.google.code.morphia.annotations.Id
import com.google.code.morphia.annotations.Reference

/**
 * @author Dmitry Kurinskiy
 * @since 10/1/11 1:46 PM
 */
@Entity
class SubjectInfo {
  @Id ObjectId id

  String frontText = ""

  @Reference(lazy=true)
  Subject subject
}
