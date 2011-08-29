package litclub

class MailSenderService {

  static rabbitQueue = "mailSenderQueue"

  void putMessage(message){
    System.out.println("Putting "+message)
    rabbitSend "mail", "mail", message
  }

  void handleMessage(message) {
    System.out.println(message)
  }
}
