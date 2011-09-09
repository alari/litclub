<%--
  @author Dmitry Kurinskiy
  @since 04.09.11 13:56
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta name="layout" content="mono"/>
    <title>Create talk</title></head>
  <body>

  <g:form action="create" method="POST">
    <g:textField name="targetDomain" bean="${command}"/>
    <g:textField name="topic" bean="${command}"/>
    <textarea name="text"></textarea>
    <g:submitButton name="submit" value="submit"/>
  </g:form>

  </body>
</html>