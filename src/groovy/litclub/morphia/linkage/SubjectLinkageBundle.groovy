@Typed package litclub.morphia.linkage

import com.google.code.morphia.annotations.Entity
import org.bson.types.ObjectId
import com.google.code.morphia.annotations.Id
import com.google.code.morphia.annotations.Indexed
import com.google.code.morphia.annotations.Embedded
import com.google.code.morphia.annotations.Reference
import litclub.morphia.subject.Subject

/**
 * @author Dmitry Kurinskiy
 * @since 18.09.11 13:21
 */
@Entity
class SubjectLinkageBundle {
  @Id ObjectId id

  @Indexed
  @Reference(lazy=true)
  Subject subject

  @Embedded
  Map<String, SubjectLinkage> linkages = [:]
}

