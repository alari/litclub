package litclub.morphia.subject

/**
 * @author Dmitry Kurinskiy
 * @since 10/6/11 9:24 PM
 */
public enum ParticipationPolicy {
  OPEN("open"),
  REQUEST("request"),
  CLOSED("closed");

  static private Map<String,ParticipationPolicy> byName = new HashMap<String, ParticipationPolicy>(3)

  static{
    values().each {
      byName.put(it.name, it)
    }
  }

  private final String name

  ParticipationPolicy(name) {
    this.name = name
  }

  String toString(){
    name
  }

  static ParticipationPolicy getByName(String name) {
    byName.get(name)
  }
}