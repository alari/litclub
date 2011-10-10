<%--
 * @author Dmitry Kurinskiy
 * @since 10/10/11 11:45 AM  
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<mk:pageHeader>
    ${message(code:"participation.list.title")}
</mk:pageHeader>
<ul>
    <g:each in="${participants}" var="p">
        <li><sbj:link subject="${p}"/> <right:canRevokeParticipant union="${subject}"><g:link controller="subjectAdm"
                                                                                              action="revokeParticipant"
                                                                                              params="[domain:subject.domain, d:p.domain]">${message(code:"participation.revoke.title")}</g:link></right:canRevokeParticipant></li>
    </g:each>
</ul>