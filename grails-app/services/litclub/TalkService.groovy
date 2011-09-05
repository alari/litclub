package litclub

import redis.clients.jedis.Transaction
import redis.clients.jedis.Jedis
import groovyx.gpars.GParsPool

class TalkService {
  private static final String KEY_NEW = "talks:new.phrases.count:"
  private static final String KEY_TALKS = "talks.person:"
  private static final String KEY_PHRASES = "talk:"
  private static final String KEY_PHRASES_NEW = "talk.new.phrases:"

  def redisService

  TalkPhrase sendPhrase(String text, long personId, long targetId, String topic) {
    Talk talk = new Talk(topic: topic, lastPhrasePersonId: personId)
    talk.minPersonId = Math.min(personId, targetId)
    talk.maxPersonId = Math.max(personId, targetId)
    if (!talk.validate() || !talk.save()) {
      log.error talk.errors
      return null
    }
    sendPhrase(text, personId, talk)
  }

  TalkPhrase sendPhrase(String text, long personId, long talkId) {
    sendPhrase(text, personId, Talk.get(talkId))
  }

  TalkPhrase sendPhrase(String text, long personId, Talk talk) {
    TalkPhrase phrase = new TalkPhrase(
        talk: talk,
        text: text,
    )
    phrase.person = Person.get(personId)
    if (!phrase.validate()) {
      log.error phrase.errors
    }
    phrase.save()

    sendPhrase(phrase)
  }

  TalkPhrase sendPhrase(TalkPhrase phrase) {
    String talkId = phrase.talk.id.toString()

    redisService.withTransaction {Transaction t ->

      // add to phrases chain
      t.lpush(KEY_PHRASES + talkId, phrase.id.toString())

      // add to talks chains
      [phrase.talk.minPersonId, phrase.talk.maxPersonId].each {pid ->
        t.lrem(KEY_TALKS + pid, 1, talkId)
        t.lpush(KEY_TALKS + pid, talkId)
      }
      // update unread count
      String targetPerson = (phrase.personId == phrase.talk.minPersonId ? phrase.talk.maxPersonId : phrase.talk.minPersonId).toString()
      t.incr(KEY_NEW + targetPerson)

      // add to unread
      t.lpush(KEY_PHRASES_NEW + targetPerson + ":" + talkId, phrase.id.toString())
    }

    // update talk cache
    phrase.talk.lastPhraseLine = phrase.text.substring(0, Math.min(phrase.text.size(), 255))
    phrase.talk.lastPhrasePersonId = phrase.personId
    phrase.talk.lastPhraseId = phrase.id
    phrase.talk.lastPhraseNew = true

    phrase.talk.save()

    phrase
  }

  List getTalkNewIds(long personId, long talkId) {
    List newIds = []
    redisService.withRedis {Jedis redis ->
      newIds = redis.lrange("${KEY_PHRASES_NEW}${personId}:${talkId}", 0, -1).collect {Long.parseLong(it)}
    }
    newIds
  }

  int getTalkNewCount(long personId, long talkId) {
    int cnt = 0
    redisService.withRedis {Jedis redis -> cnt = redis.llen("${KEY_PHRASES_NEW}${personId}:${talkId}")}
    cnt
  }

  List<Talk> getTalks(long personId, int start, int end) {
    List<Talk> talks = []
    redisService.withRedis {Jedis redis ->
      redis.lrange(KEY_TALKS + personId, start, end).each {String talkId ->
        talks.add(Talk.get(talkId.toLong()))
      }
    }
    talks
  }

  List<Talk> getTalksWithNew(long personId, long firstNew, int min, int step) {
    List<Talk> talks = []
    redisService.withRedis {Jedis redis ->
      redis.lrange(KEY_TALKS + personId, -min, 0).each {String talkId ->
        talks.add(Talk.get(talkId.toLong()))
      }
      GParsPool.withPool {

      }
    }
    talks
  }

  List<TalkPhrase> getPhrases(long talkId, int start, int end) {
    List<TalkPhrase> phrases = []
    redisService.withRedis {Jedis redis ->
      redis.lrange(KEY_PHRASES + talkId, start, end).each {String phraseId ->
        phrases.add(TalkPhrase.get(phraseId.toLong()))
      }
    }
    phrases.reverse()
  }

  List<TalkPhrase> getPhrasesWithNew(long talkId, long firstNew, int minNum, int step) {
    List<TalkPhrase> phrases = []
    redisService.withRedis {Jedis redis ->
      redis.lrange(KEY_PHRASES + talkId, 0, minNum).each {String phraseId ->
        phrases.add(TalkPhrase.get(phraseId.toLong()))
      }
      if (firstNew) GParsPool.withPool {
        boolean pulled = phrases.size() ? true : false
        while (pulled && !phrases.anyParallel {it.id == firstNew}) {
          pulled = false
          redis.lrange(KEY_PHRASES + talkId, minNum, minNum + step).each {String phraseId ->
            phrases.add(TalkPhrase.get(phraseId.toLong()))
            pulled = true
          }
          minNum  += step
        }
      }
    }
    phrases.reverse()
  }

  void readPhrase(TalkPhrase phrase) {
    long personId = phrase.talk.minPersonId == phrase.personId ? phrase.talk.maxPersonId : phrase.talk.minPersonId
    if (phrase.talk.lastPhraseId == phrase.id) {
      phrase.talk.lastPhraseNew = false
      phrase.talk.save()
    }
    redisService.withRedis {Jedis redis ->
      redis.decr(KEY_NEW + personId)
      redis.lrem("${KEY_PHRASES_NEW}${personId}:${phrase.talk.id}", -1, phrase.id.toString())
    }
  }

  int getNewCount(long personId) {
    def nc = redisService."${KEY_NEW}${personId}"
    nc ? Integer.parseInt(nc) : 0
  }
}
