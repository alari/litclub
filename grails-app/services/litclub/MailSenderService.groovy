package litclub

import grails.gsp.PageRenderer

class MailSenderService {

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
  void putMessage(Map<String,Object> args){
    rabbitSend "mail", "mailSenderQueue", args
  }

  void handleMessage(Map<String,Object> message) {
    try {
      System.out.println( groovyPageRenderer.render(view: message.view, model: message.model) )
    } catch(Exception e){
      System.err.println(e)
    }
  }
}
