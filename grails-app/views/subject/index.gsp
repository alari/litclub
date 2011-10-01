<%--
  @author Dmitry Kurinskiy
  @since 02.09.11 13:25
--%>

<%@ page import="litclub.morphia.subject.Person" contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta name="layout" content="mono"/>
    <title>@${subject.domain}</title>
  </head>
  <body>
  <h1><sbj:link subject="${subject}"/> frontpage</h1>
  <g:if test="${subject instanceof Person}">
  <p>Tell you a secret: email is <tt>${subject?.email}</tt></p>
      <p>Locked: <g:formatBoolean boolean="${subject.accountLocked}"/></p>
    </g:if>
    <blockquote>
      <g:each in="${parties}" var="party">
        <p>PARTY: ${party.level} in <sbj:link subject="${party.subject}"/> </p>
      </g:each>
    </blockquote>
  <p>The text in info object is:</p>
  <hr/>
  ${info.frontText}

  </body>
</html>