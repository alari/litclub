<%--
  @author Dmitry Kurinskiy
  @since 13.09.11 13:23
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta name="layout" content="mono"/>
    <title>Adding: ${type}</title></head>
  <body>

  <mk:pageHeader>New Creative Node</mk:pageHeader>

  <form method="POST">
    <mk:formLine bean="${command}" field="title" label="Title">
      <g:textField class="medium" size="20" name="title" bean="${command}"/>
    </mk:formLine>
    <mk:formLine bean="${command}" field="text" label="Text" isBlock="1">
      <textarea name="text" id="text" class="xxlarge">${command?.text}</textarea>
    </mk:formLine>
    <mk:formActions>
      <g:submitButton name="sbm" value="publish" class="btn primary"/>
    </mk:formActions>
  </form>

  </body>
</html>