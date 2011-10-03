@Typed package litclub.morphia.linkage

import com.google.code.morphia.annotations.Embedded
import com.google.code.morphia.annotations.Reference
import litclub.morphia.subject.Subject

/**
 * @author Dmitry Kurinskiy
 * @since 18.09.11 13:24
 */
@Embedded
class SubjectLinkage {
  @Reference(lazy=true)
  Subject subject

  PartyLevel level

  String info
}
