<%--
  @author Dmitry Kurinskiy
  @since 02.09.11 13:25
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="tabs"/>
    <title>${subject.domain}</title>
</head>

<body>
<mk:pageHeader><sbj:link subject="${subject}"/></mk:pageHeader>

<mk:tab active="1" link="#sbj-main" labelCode="layout.subject.tab.index.title">

    <g:if test="${subject.isPerson()}">
        <g:render template="personIndex" model="[subject: subject, parties: parties, info: info]"/>
    </g:if>
    <g:if test="${subject.isUnion()}">
        <g:render template="unionIndex" model="[subject: subject, parties: parties, info: info]"/>
    </g:if>

</mk:tab>

<right:canAdministrate subject="${subject}">
    <mk:tab labelCode="union.tab.adm.title" link="#sbj-adm">
        <g:render template="unionAdm" model="[subject: subject, info: info]"/>
    </mk:tab>
</right:canAdministrate>

<g:if test="${participants}">
    <mk:tab labelCode="participation.tab.title" link="#sbj-participants">
        <g:render template="unionParticipants" model="[subject: subject, participants: participants]"/>
    </mk:tab>
</g:if>

</body>
</html>