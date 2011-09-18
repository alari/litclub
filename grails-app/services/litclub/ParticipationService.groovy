package litclub

import litclub.morphia.PartyLevel
import redis.clients.jedis.Jedis

class ParticipationService {

  private static final String KEY_LEVELS = "union.levels:"
  private static final String KEY_PARTICIPANTS = "union.participants:"

  def redisService

  PartyLevel getLevel(Union union, Person person){
    String level = PartyLevel.NOBODY.toString()
    redisService.withRedis {Jedis redis ->
      level = redis.hget(KEY_LEVELS+union.id, person.id.toString())
    }
    PartyLevel.getByName(level)
  }

  Set<long> getParticipants(Union union) {
    Set<long> participants = []
    redisService.withRedis {Jedis redis ->
      participants = redis.smembers(KEY_PARTICIPANTS+union.id).collect {it.toLong()}
    }
    participants
  }

  def setParty(Union union, Person person, PartyLevel level) {
    PartyLevel wasLevel = PartyLevel.NOBODY
    redisService.withRedis {Jedis redis ->
      wasLevel = PartyLevel.getByName( redis.hget(KEY_LEVELS+union.id, person.id.toString()) )
      if(wasLevel == level) return;
      redis.hset(KEY_LEVELS+union.id, person.id.toString(), level.toString())
      // participants cache
      if(level == PartyLevel.PARTICIPANT) {
        redis.sadd(KEY_PARTICIPANTS+union.id, person.id.toString())
      } else {
        redis.srem(KEY_PARTICIPANTS+union.id, person.id.toString())
      }
    }
    if(wasLevel == level) return;

    if(wasLevel.isSenior() && !level.isSenior()) {
      // remove from union mongo participation
    } else if(level.isSenior() && !wasLevel.isSenior()) {
      // add to union mongo participation
    }

    // if was not participant and now is, add to person's participation
    if(wasLevel.toInteger() <= 0 && level.toInteger() > 0) {

    }

  }

  def removeParty(Union union, Person person) {
    // take a look on redis; remove if present
    // if was senior, remove from mongo
    // remove from person mongo
  }

  def invite(Union union, Person person) {
    // take a look on redis
    // if there's a value, do nothing
    // otherwise add redis key
    // + should send notice
  }

  def request(Union union, Person person) {
    // take a look on redis
    // if there is no key, add a key with request mark
    // also check union settings!
    // maybe we may save the request right now
    // +add to requests redis key
  }
}
