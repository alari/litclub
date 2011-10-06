@Typed package litclub.morphia

import com.google.code.morphia.Morphia
import com.mongodb.Mongo
import grails.util.Environment
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author Dmitry Kurinskiy
 * @since 08.09.11 15:04
 */
class MorphiaDriver {
  Morphia morphia = new Morphia()
  Mongo mongo = new Mongo()
  String dbName = "litclub"

  MorphiaDriver(){
    if(Environment.getCurrent() == Environment.TEST) {
      System.out.println("Using TESTING MorphiaDriver")
      dbName = "litclubTest"
      mongo.getDB(dbName).dropDatabase()
    }
  }
}
