package litclub

import redis.clients.jedis.Jedis
import org.apache.log4j.Logger

class SubjectDomainService {
  static transactional = false
  private Logger log = Logger.getLogger(getClass())

  private final static String KEY = "subject.domains"

  def redisService

  String getIdByDomain(String domain) {
    if (!domain) return ""
    String id = ""
    redisService.withRedis {Jedis jedis ->
      id = jedis.hget(KEY, domain) ?: ""
    }
    id
  }

  boolean checkDomainExists(String domain) {
    if (!domain) return false
    boolean exists = true
    redisService.withRedis {Jedis jedis ->
      exists = jedis.hexists(KEY, domain)
    }
    exists
  }

  void delDomain(String domain) {
    if (!domain) return;
    redisService.withRedis {Jedis jedis ->
      jedis.hdel(KEY, domain)
    }
  }

  boolean setDomain(subjectId, String domain, String oldDomain = null) {
    if (!subjectId || !domain) return false
    int ok = 0
    redisService.withRedis {Jedis jedis ->
      ok = jedis.hsetnx(KEY, domain, subjectId.toString())
      if (ok && oldDomain) {
        delDomain(oldDomain)
      }
    }
    ok ? true : false
  }
}
