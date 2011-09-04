package litclub

import redis.clients.jedis.Transaction
import redis.clients.jedis.Jedis

class TalkService {

  def redisService

  def sendPhrase(String text, long personId, long targetId, String topic) {
    Talk talk = new Talk(topic: topic, info1: new TalkInfo(), info2: new TalkInfo())
    talk.info1.personId = Math.min(personId, targetId)
    talk.info2.personId = Math.max(personId, targetId)
    if(!talk.validate() || !talk.save()) {
      return false
    }
    sendPhrase(text, personId, talk)
  }

  def sendPhrase(String text, long personId, long talkId){
    sendPhrase(text, personId, Talk.get(talkId))
  }

  def sendPhrase(String text, long personId, Talk talk){
    TalkPhrase phrase = new TalkPhrase(
        talk: talk,
        text: text,
        isNew: true,
    )
    phrase.personId = personId
    phrase.save()

    sendPhrase(phrase)
  }

  def sendPhrase(TalkPhrase phrase) {
    String talkId = phrase.talk.id.toString()

    redisService.withTransaction {Transaction t->

    // add to phrases chain
      t.lpush("talk:$talkId", phrase.id.toString())

      // add to talks chains
    [phrase.talk.info1.personId, phrase.talk.info2.personId].each {pid->
        t.lrem("person.talks:$pid", 1, talkId)
        t.lpush("person.talks:$pid", talkId)
    }
      // update unread count
      String targetPerson = (phrase.personId == phrase.talk.info1.personId ? phrase.talk.info2.personId : phrase.talk.info1.personId).toString()
      t.incr("person.talks.unread:"+targetPerson)
    }

    // update talk cache
    String line = phrase.text.substring(0, Math.min(phrase.text.size(), 255))
    if(phrase.personId == phrase.talk.info1.personId) {
      phrase.talk.info1.line = line
      phrase.talk.info1.isNew = true
      phrase.talk.info1.phraseId = phrase.id
    } else {
      phrase.talk.info2.line = line
      phrase.talk.info2.isNew = true
      phrase.talk.info2.phraseId = phrase.id
    }
    phrase.talk.save()
  }

  List<Talk> getTalks(long personId, long offset, int limit) {
    List<Talk> talks = []
    redisService.withRedis {Jedis redis->
      redis.lrange("person.talks:"+personId, offset, offset+limit-1).each{long talkId->
        talks.add(Talk.get(talkId))
      }
    }
    talks
  }

  List<TalkPhrase> getPhrases(long talkId, long offset, int limit) {
    List<TalkPhrase> phrases = []
    redisService.withRedis {Jedis redis->
      redis.lrange("talk:"+talkId, offset, offset+limit-1).each{long phraseId->
        phrases.add(TalkPhrase.get(phraseId))
      }
    }
    phrases
  }

  void readPhrase(TalkPhrase phrase) {
    phrase.isNew = false
    long personId = phrase.talk.info1.personId == phrase.personId ? phrase.talk.info2.personId : phrase.talk.info1.personId
    if(phrase.talk.info1.phraseId == phrase.id) {
      phrase.talk.info1.isNew = false
    } else if (phrase.talk.info2.phraseId == phrase.id) {
      phrase.talk.info2.isNew = false
    }
    phrase.talk.save()
    phrase.save()
    redisService.withRedis {Jedis redis->
      redis.decr("person.talks.unread:"+personId)
    }
  }

  int getUnreadCount(long personId) {
    return redisService."person.talks.unread:${personId}"
  }
}
