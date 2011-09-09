<%--
  @author Dmitry Kurinskiy
  @since 02.09.11 13:25
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta name="layout" content="mono"/>
    <title>@${person.domain}</title>
  </head>
  <body>
  <h1><sbj:link subject="${person}"/> frontpage</h1>
  <p>Tell you a secret: email is <tt>${person.email}</tt></p>
  <p>The text in info object is:</p>
  <hr/>
  ${person.info.frontText}
  </body>
</html>