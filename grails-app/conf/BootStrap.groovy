import grails.util.Environment
import redis.clients.jedis.Jedis

class BootStrap {

    def init = { servletContext ->
      if(Environment.isDevelopmentMode()){
        new Jedis("localhost").flushDB()
      }
    }
    def destroy = {
    }
}
