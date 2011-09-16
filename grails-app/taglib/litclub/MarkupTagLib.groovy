package litclub

class MarkupTagLib {
  static namespace = "mk"

  def pageHeader = {attrs, body ->
    out << /<div class="page-header"><h1>/
    out << body()
    out << '</h1></div>'
  }

  def formLine = {attrs, body ->
    def bean = attrs.bean
    String field = attrs.field

    boolean isBlock = attrs.containsKey("isBlock")
    attrs.isBlock

    String label = ""
    if(attrs.labelCode) label = message(code:attrs.labelCode, default: attrs.label)
    else if(attrs.label) label = attrs.label
    else label = field

    String help = ""
    if(attrs.containsKey("helpCode") || attrs.containsKey("help")){
      help = attrs.containsKey("helpCode") ? message(code:attrs.helpCode, default: attrs.help) : attrs.help
    }

    String beanHasErrors = hasErrors(bean:bean, field:field, " error")
    String helpSpan = ""
    if(beanHasErrors || help) {
      helpSpan = """
        <span class="help-${isBlock?'block':'inline'}">
          ${fieldError(bean:bean,field:field)}
          ${help}
        </span>
      """
    }

    out << """
    <div class="clearfix${beanHasErrors}">
      <label for="${field}">${label}</label>
      <div class="input">${body()}
        ${helpSpan}
      </div>
    </div>
    """
  }

  def formActions = {attrs, body->
    out << /<div class="actions">/
    out << body()
    out << '</div>'
  }
}
