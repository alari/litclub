@Typed package litclub.morphia

/**
 * @author Dmitry Kurinskiy
 * @since 08.09.11 14:50
 */
public enum Type {
  PROSE("prose"),
  POETRY("poetry"),
  ARTICLE("article"),
  BLOGPOST("post"),
  THREAD("thread");

  static private Map<String,Type> byName = [:]

  static {
    values().each {byName.put(it.name, it)}
  }

  private String name

  private Type(String name){
    this.name = name
  }

  String toString(){
    name
  }

  static Type getByName(String name){
    byName.get(name)
  }
}