@Typed package litclub.morphia.sec

import com.google.code.morphia.dao.BasicDAO
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import litclub.morphia.MorphiaDriver

/**
 * @author Dmitry Kurinskiy
 * @since 10/1/11 1:26 PM
 */
class RegistrationCodeDAO extends BasicDAO<RegistrationCode,ObjectId>{
  @Autowired RegistrationCodeDAO(MorphiaDriver morphiaDriver){
    super(morphiaDriver.mongo, morphiaDriver.morphia, morphiaDriver.dbName)
  }

  RegistrationCode getByToken(String token){
    createQuery().filter("token", token).get()
  }
}
