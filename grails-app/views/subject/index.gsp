<%--
  @author Dmitry Kurinskiy
  @since 02.09.11 13:25
--%>

<%@ page import="litclub.morphia.subject.Union; litclub.morphia.subject.Person" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="tabs"/>
    <title>@${subject.domain}</title>
</head>

<body>
<mk:pageHeader><sbj:link subject="${subject}"/> frontpage</mk:pageHeader>

<mk:tab active="1" link="#sbj-main" labelCode="Index Page">

    <g:if test="${subject instanceof Person}">
    <g:render template="personIndex" model="[subject: subject, parties: parties, info: info]"/>
        </g:if>
    <g:if test="${subject instanceof Union}">
    <g:render template="unionIndex" model="[subject: subject, parties: parties, info: info]"/>    
    </g:if>



</mk:tab>

<right:canAdministrate subject="${subject}">
    <mk:tab labelCode="Adm Tab" link="#sbj-adm">
        <g:render template="unionAdm" model="[subject: subject, info: info]"/>
    </mk:tab>
</right:canAdministrate>

</body>
</html>