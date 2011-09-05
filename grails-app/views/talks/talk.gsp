<%--
  @author Dmitry Kurinskiy
  @since 05.09.11 11:59
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>The Talk ${talk.topic}</title></head>

<body>
<h1>${talk.topic}</h1>
<h4><sbj:link id="${talk.minPersonId}"/>(${talk.minPersonId}) vs. <sbj:link
    id="${talk.maxPersonId}"/>(${talk.maxPersonId})</h4>

<g:each in="${phrases}" var="phrase">
  <p>From: <sbj:link subject="${phrase.person}"/> <g:if test="${newPhrases.contains(phrase.id)}">(new)</g:if><g:if
      test="${phrase.id == firstNew}">+first</g:if></p>

  <p>${phrase.text}</p>
</g:each>

<g:form action="sayPhrase">
  <textarea name="text"></textarea>
  <input type="hidden" name="talkId" value="${talk.id}"/>
  <g:submitButton name="submit" value="submit"/>
</g:form>
</body>
</html>