package litclub.morphia.linkage

/**
 * @author Dmitry Kurinskiy
 * @since 18.09.11 13:39
 */
enum PartyLevel {
  FOUNDER("founder", 4),
  HEAD("head", 3),
  SENIOR("senior", 2),
  PARTICIPANT("participant", 1),
  NOBODY("nobody", 0),
  INVITED("invited", -1),
  REQUESTED("requested", -2);

  static private Map<String, PartyLevel> byName = [:]

  static {
    values().each {byName.put(it.name, it)}
  }

  private String name
  private short level

  private PartyLevel(String name, int level) {
    this.name = name
    this.level = level
  }

  String toString() {
    name
  }

  Integer toInteger() {
    level
  }

  boolean hasSenior() {
    level >= 2
  }

  boolean hasParticipant() {
    level >= 1
  }

  boolean is(PartyLevel level) {
    this == level
  }

  static PartyLevel getByName(String name) {
    byName.get(name) ?: NOBODY
  }
}
