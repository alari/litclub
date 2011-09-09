@Typed package litclub.morphia

import com.google.code.morphia.Morphia
import com.mongodb.Mongo

/**
 * @author Dmitry Kurinskiy
 * @since 08.09.11 15:04
 */
class MorphiaDriver {
  Morphia morphia = new Morphia()
  Mongo mongo = new Mongo()
  String dbName = "litclub"
}
