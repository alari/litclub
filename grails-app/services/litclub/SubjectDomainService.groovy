package litclub

import redis.clients.jedis.Jedis
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired

class SubjectDomainService {
  static transactional = false
  private Logger log = Logger.getLogger(getClass())

  def redisService
  @Autowired
  RedisKeys redisKeys

  String getIdByDomain(String domain) {
    if (!domain) return ""
    String id = ""
    redisService.withRedis {Jedis jedis ->
      id = jedis.hget(redisKeys.subjectDomains(), domain) ?: ""
    }
    id
  }

  boolean checkDomainExists(String domain) {
    if (!domain) return false
    boolean exists = true
    redisService.withRedis {Jedis jedis ->
      exists = jedis.hexists(redisKeys.subjectDomains(), domain)
    }
    exists
  }

  void delDomain(String domain) {
    if (!domain) return;
    redisService.withRedis {Jedis jedis ->
      jedis.hdel(redisKeys.subjectDomains(), domain)
    }
  }

  boolean setDomain(subjectId, String domain, String oldDomain = null) {
    if (!subjectId || !domain) return false
    int ok = 0
    redisService.withRedis {Jedis jedis ->
      ok = jedis.hsetnx(redisKeys.subjectDomains(), domain, subjectId.toString())
      if (ok && oldDomain) {
        delDomain(oldDomain)
      }
    }
    ok ? true : false
  }
}
