@Typed package litclub.morphia.talk

import org.bson.types.ObjectId
import litclub.morphia.MorphiaDriver
import org.springframework.beans.factory.annotation.Autowired
import com.google.code.morphia.dao.BasicDAO

/**
 * @author Dmitry Kurinskiy
 * @since 10/1/11 12:49 PM
 */
class TalkPhraseDAO extends BasicDAO<TalkPhrase, ObjectId> {
  @Autowired TalkPhraseDAO(MorphiaDriver morphiaDriver) {
    super(morphiaDriver.mongo, morphiaDriver.morphia, morphiaDriver.dbName)
  }

  TalkPhrase getById(String id) {
    if (!ObjectId.isValid(id)) return null
    getById(new ObjectId(id))
  }

  TalkPhrase getById(ObjectId id) {
    get(id)
  }

  List<TalkPhrase> getByTalk(Talk talk) {
    createQuery().filter("talk", talk).asList()
  }
}

