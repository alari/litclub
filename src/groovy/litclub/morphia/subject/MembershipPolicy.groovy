package litclub.morphia.subject

/**
 * @author Dmitry Kurinskiy
 * @since 10/6/11 9:24 PM
 */
public enum MembershipPolicy {
  OPEN("open"),
  REQUEST("request"),
  CLOSED("closed");

  static private Map<String,MembershipPolicy> byName = new HashMap<String, MembershipPolicy>(3)

  static{
    values().each {
      byName.put(it.name, it)
    }
  }

  private final String name
  MembershipPolicy(name) {
    this.name = name
  }

  String toString(){
    name
  }

  static MembershipPolicy getByName(String name) {
    byName.get(name)
  }
}