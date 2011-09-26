package litclub.morphia.dao

import org.bson.types.ObjectId
import com.google.code.morphia.dao.BasicDAO
import org.springframework.beans.factory.annotation.Autowired
import litclub.morphia.MorphiaDriver
import litclub.morphia.Participation
import litclub.Subject
import litclub.morphia.Party
import litclub.morphia.PartyLevel

/**
 * @author Dmitry Kurinskiy
 * @since 20.09.11 13:41
 */
class ParticipationDAO extends BasicDAO<Participation, ObjectId>{
  @Autowired
  ParticipationDAO(MorphiaDriver morphiaDriver){
    super(morphiaDriver.mongo, morphiaDriver.morphia, morphiaDriver.dbName)
  }

  Participation getById(String id) {
    if(!ObjectId.isValid(id)) return null
    getById(new ObjectId(id))
  }

  Participation getById(ObjectId id) {
    get(id)
  }

  Participation getBySubject(Subject subject){
    getBySubject(subject.id)
  }

  Participation getBySubject(long subjectId){
    Participation p = createQuery().filter("subjectId", subjectId).get()
    if(!p) {
      p = new Participation()
      p.subjectId = subjectId
      p.parties = [:]
      save(p)
    }
    p
  }

  Party getParty(Subject base, Subject rel) {
    getBySubject(base).parties.get(rel.id.toString())
  }

  void setParty(Subject base, Party party) {
    Participation p = getBySubject(base)
    p.parties.put(party.subjectId.toString(), party)
    save(p)
  }

  void setParty(Subject base, Subject rel, PartyLevel level) {
    setParty(base, new Party(subjectId: rel.id, level: level, info: ""))
  }

  void remParty(Subject base, Subject rel) {
    Participation p = getBySubject(base)
    p.parties.remove(rel.id.toString())
    save(p)
  }
}
