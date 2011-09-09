<%--
  @author Dmitry Kurinskiy
  @since 04.09.11 13:44
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta name="layout" content="mono"/>
  <title>Talks</title>
</head>

<body>
<h1>Your talks</h1>

<h2><g:link action="create">Create a new one</g:link></h2>
<g:each in="${talks}" var="talk">
  <div>
    <h4><g:link action="talk" id="${talk.id}">${talk.topic}</g:link> (<talk:newCount talkId="${talk.id}"/>)</h4>

    <p><sbj:link id="${talk.lastPhrasePersonId}"/> ${talk.lastPhraseLine} | <g:formatBoolean
        boolean="${talk.lastPhraseNew}"/></p>
  </div>
  <hr/>
</g:each>
</body>
</html>