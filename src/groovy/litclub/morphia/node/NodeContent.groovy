package litclub.morphia.node

import org.bson.types.ObjectId
import com.google.code.morphia.annotations.Id

/**
 * @author Dmitry Kurinskiy
 * @since 13.09.11 13:20
 */
class NodeContent {
  @Id ObjectId id

  String text
}
