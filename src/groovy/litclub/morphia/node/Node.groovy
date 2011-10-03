@Typed package litclub.morphia.node

import com.google.code.morphia.annotations.Entity
import org.bson.types.ObjectId
import com.google.code.morphia.annotations.Id
import com.google.code.morphia.annotations.PrePersist
import com.google.code.morphia.annotations.Version
import com.google.code.morphia.annotations.Reference
import com.google.code.morphia.annotations.Index
import com.google.code.morphia.annotations.Indexes
import litclub.morphia.subject.Subject

/**
 * @author Dmitry Kurinskiy
 * @since 08.09.11 14:39
 */
@Entity
@Indexes([
@Index(value = "subject,name", unique = true, dropDups = true)])
class Node {
  @Id ObjectId id

  @Reference(lazy=true)
  Subject subject

  NodeType type

  @Reference(lazy = true)
  NodeContent content

  String title

  String name

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
