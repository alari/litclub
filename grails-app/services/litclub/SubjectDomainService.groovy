package litclub

import redis.clients.jedis.Jedis

class SubjectDomainService {

  private final static String KEY = "subject:domains"

  def redisService

  long getIdByDomain(String domain) {
    if(!domain) return 0
    long id = 0
    redisService.withRedis {Jedis jedis ->
      id = jedis.hget(KEY, domain)?.toLong() ?: 0
    }
    id
  }

  Subject getSubjectByDomain(String domain) {
    long id = getIdByDomain(domain)
    id ? Subject.get(id) : null
  }

  boolean checkDomainExists(String domain) {
    if(!domain) return false
    boolean exists = true
    redisService.withRedis {Jedis jedis->
      exists = jedis.hexists(KEY, domain)
    }
    exists
  }

  void delDomain(String domain) {
    if(!domain) return;
    redisService.withRedis {Jedis jedis->
      jedis.hdel(KEY, domain)
    }
  }

  boolean setDomain(long subjectId, String domain, String oldDomain=null) {
    if(!subjectId || !domain) return false
    int ok = 0
    redisService.withRedis {Jedis jedis->
      ok = jedis.hsetnx(KEY, domain, subjectId.toString())
      if(ok && oldDomain) {
        delDomain(oldDomain)
      }
    }
    ok ? true : false
  }
}
