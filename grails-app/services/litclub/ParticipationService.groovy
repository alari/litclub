package litclub

import litclub.morphia.linkage.PartyLevel
import redis.clients.jedis.Jedis
import litclub.morphia.linkage.SubjectLinkageBundle

import litclub.morphia.linkage.SubjectLinkage
import litclub.morphia.subject.Person
import litclub.morphia.subject.Union
import litclub.morphia.subject.Subject
import litclub.morphia.subject.PersonDAO
import org.springframework.beans.factory.annotation.Autowired
import org.apache.log4j.Logger

class ParticipationService {
  static transactional = false
  private Logger log = Logger.getLogger(getClass())

  def redisService
  def subjectLinkageService
  @Autowired PersonDAO personDao
  @Autowired RedisKeys redisKeys

  private String keyParticipants(Union union) {
    redisKeys.participants(union.id.toString())
  }

  private String keyLevels(Union union) {
    redisKeys.partyLevels(union.id.toString())
  }

  private String keyLevels(String unionId) {
    redisKeys.partyLevels(unionId)
  }

  PartyLevel getLevel(String unionId, Person person) {
    String level = PartyLevel.NOBODY.toString()
    redisService.withRedis {Jedis redis ->
      level = redis.hget(keyLevels(unionId), person.id.toString())
    }
    PartyLevel.getByName(level)
  }

  PartyLevel getLevel(Union union, Person person) {
    String level = PartyLevel.NOBODY.toString()
    redisService.withRedis {Jedis redis ->
      level = redis.hget(keyLevels(union), person.id.toString())
    }
    PartyLevel.getByName(level)
  }

  Set<Long> getParticipants(Union union) {
    Set<Long> participants = []
    redisService.withRedis {Jedis redis ->
      participants = redis.smembers(keyParticipants(union)).collect {it.toLong()}
    }
    participants
  }

  SubjectLinkageBundle getLinkageBundle(Subject subject) {
    subjectLinkageService.getBundle(subject)
  }

  List<SubjectLinkage> getLinkageParties(Subject subject) {
    subjectLinkageService.getLinkages(subject)
  }

  void setParty(Union union, Person person, PartyLevel level) {
    PartyLevel wasLevel = PartyLevel.NOBODY
    redisService.withRedis {Jedis redis ->
      wasLevel = PartyLevel.getByName(redis.hget(keyLevels(union), person.id.toString()))
      if (wasLevel.is(level)) return;

      redis.hset(keyLevels(union), person.id.toString(), level.toString())

      // participants cache
      if (level.is(PartyLevel.PARTICIPANT)) {
        redis.sadd(keyParticipants(union), person.id.toString())
      } else {
        redis.srem(keyParticipants(union), person.id.toString())
      }
    }
    if (wasLevel.is(level)) return;

    if (wasLevel.hasSenior() && !level.hasSenior()) {
      subjectLinkageService.remLinkage(union, person)
    } else if (level.hasSenior() && !wasLevel.hasSenior()) {
      subjectLinkageService.setLinkage(union, person, level)
    }

    if (!wasLevel.hasParticipant() && level.hasParticipant()) {
      subjectLinkageService.setLinkage(person, union, level)
    }
  }

  void removeParty(Union union, Person person) {
    PartyLevel level = getLevel(union, person)
    if (level.is(PartyLevel.NOBODY)) return;

    redisService.withRedis {Jedis redis ->
      redis.hdel(keyLevels(union), person.id.toString())
    }
    if (level.hasSenior()) {
      subjectLinkageService.remLinkage(union, person)
    }
    if (level.hasParticipant()) {
      subjectLinkageService.remLinkage(person, union)
    }
  }

  void invite(Union union, Person person) {
    PartyLevel level = getLevel(union, person)
    if (!level.is(PartyLevel.NOBODY)) return;

    redisService.withRedis {Jedis redis ->
      redis.hset(keyLevels(union), person.id.toString(), PartyLevel.INVITED.toString())
    }

    // TODO: send notice
  }

  void request(Union union, Person person) {
    PartyLevel level = getLevel(union, person)
    if (!level.is(PartyLevel.NOBODY)) return;

    redisService.withRedis {Jedis redis ->
      redis.hset(keyLevels(union), person.id.toString(), PartyLevel.REQUESTED.toString())
    }

    // TODO: send notice to community
  }

  List<Person> listSomeParticipants(Union union, long max = 5) {
    List<Long> ids = []
    redisService.withRedis {Jedis redis ->
      max = Math.min(max, redis.scard(keyParticipants(union)))
      while (ids.size() < max) {
        long id = redis.srandmember(keyParticipants(union)).toLong()
        if (!ids.contains(id)) ids.add(id)
      }
    }
    ids.collect {personDao.getById(it.toString())}
  }
}
