@Typed package litclub.morphia

import com.google.code.morphia.annotations.Entity
import org.bson.types.ObjectId
import com.google.code.morphia.annotations.Id
import com.google.code.morphia.annotations.PrePersist
import com.google.code.morphia.annotations.Version

/**
 * @author Dmitry Kurinskiy
 * @since 08.09.11 14:39
 */
@Entity
class Node {
  @Id ObjectId id

  Long subjectId

  Type type

  String title

  boolean isDraft

  @Version
  Long version;

  Date dateCreated = new Date();
  Date lastUpdated = new Date();

  @PrePersist
  void prePersist() {
    lastUpdated = new Date();
  }
}
