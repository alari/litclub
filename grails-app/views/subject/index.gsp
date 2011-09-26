<%--
  @author Dmitry Kurinskiy
  @since 02.09.11 13:25
--%>

<%@ page import="litclub.Person" contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta name="layout" content="mono"/>
    <title>@${subject.domain}</title>
  </head>
  <body>
  <h1><sbj:link subject="${subject}"/> frontpage</h1>
  <g:if test="${subject instanceof Person}">
  <p>Tell you a secret: email is <tt>${subject?.email}</tt></p>
    </g:if>
    <blockquote>
      <g:each in="${parties}" var="party">
        <p>PARTY: ${party.level} in <sbj:link id="${party.subjectId}"/> </p>
      </g:each>
    </blockquote>
  <p>The text in info object is:</p>
  <hr/>
  ${subject.info.frontText}
  </body>
</html>