package litclub.morphia

import com.google.code.morphia.annotations.Embedded

/**
 * @author Dmitry Kurinskiy
 * @since 18.09.11 13:24
 */
@Embedded
class SubjectLinkage {
  long subjectId

  PartyLevel level

  String info
}
