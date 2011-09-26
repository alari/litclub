package litclub.own

/**
 * @author Dmitry Kurinskiy
 * @since 14.09.11 0:30
 */
class NodeFormCommand {
  String title
  String text

  static constraints = {
    title blank: false
    text blank: false
  }
}
