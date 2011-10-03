package litclub

import grails.gsp.PageRenderer
import org.apache.log4j.Logger

class MailSenderService {
  static transactional = false
  private Logger log = Logger.getLogger(getClass())

  static rabbitQueue = "mailSenderQueue"

  PageRenderer groovyPageRenderer

  /**
   * @arg to
   * @arg from
   * @arg view
   * @arg model
   * @arg body
   * @arg subject
   */
  void putMessage(Map<String, Object> args) {
    rabbitSend "mail", "mailSenderQueue", args
  }

  void handleMessage(Map<String, Object> message) {
    String s
    try {
      s = sesMail {
        to message.to
        if (message.from) from message.from
        subject message.subject
        html groovyPageRenderer.render(view: message.view, model: message.model)
      } ?: groovyPageRenderer.render(view: message.view, model: message.model)
    } catch (Exception e) {
      System.out.println(e)
    }
    System.out.println(s)
  }
}
