@Typed package litclub.morphia

/**
 * @author Dmitry Kurinskiy
 * @since 08.09.11 14:50
 */
public enum NodeType {
  PROSE("prose"),
  POETRY("poetry"),
  ARTICLE("article"),
  BLOGPOST("post"),
  THREAD("thread");

  static private Map<String,NodeType> byName = [:]

  static {
    values().each {byName.put(it.name, it)}
  }

  private String name

  private NodeType(String name){
    this.name = name
  }

  String toString(){
    name
  }

  static NodeType getByName(String name){
    byName.get(name)
  }
}