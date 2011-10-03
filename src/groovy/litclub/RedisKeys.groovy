package litclub

import grails.util.Environment
import grails.plugin.redis.RedisService
import org.springframework.beans.factory.annotation.Autowired
import redis.clients.jedis.Jedis

/**
 * @author Dmitry Kurinskiy
 * @since 10/3/11 3:44 PM
 */
class RedisKeys {
  private static final String TALK_NEW_PHRASES_COUNT = "talks:new.phrases.count:"
  private static final String TALKS = "talks.person:"
  private static final String TALK_PHRASES = "talk:"
  private static final String TALK_NEW_PHRASES = "talk.new.phrases:"
  private final static String SUBJECT_DOMAINS = "subject.domains"

  private static final String PARTICIPATION_LEVELS = "union.levels:"
  private static final String PARTICIPANTS = "union.participants:"

  private String prefix = ""

  @Autowired RedisKeys(RedisService redisService){
    if(Environment.current == Environment.TEST) {
      System.out.println("Using TESTING RedisKeys")
      prefix = "TEST_"
      redisService.withRedis {Jedis redis->
        redis.keys(prefix+"*").each {
          redis.del(it)
        }
      }
    }
  }

  String talkNewPhrasesCount(String subjectId){
    prefix + TALK_NEW_PHRASES_COUNT + subjectId
  }
  String talks(String subjectId){
    prefix + TALKS + subjectId
  }
  String talkPhrases(String talkId){
    prefix + TALK_PHRASES + talkId
  }
  String talkNewPhrases(String subjectId, String talkId){
    prefix + TALK_NEW_PHRASES + subjectId + ":" + talkId
  }
  String subjectDomains(){
    prefix + SUBJECT_DOMAINS
  }
  String participants(String unionId){
    prefix + PARTICIPANTS + unionId
  }
  String partyLevels(String unionId){
    prefix + PARTICIPATION_LEVELS + unionId
  }
}
