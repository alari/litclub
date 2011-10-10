<%--
 * @author Dmitry Kurinskiy
 * @since 10/10/11 11:45 AM  
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<ul>
    <g:each in="${participants}" var="p">
        <li><sbj:link subject="${p}"/> <right:canRevokeParticipant union="${subject}"><g:link controller="subjectAdm" action="revokeParticipant" params="[domain:subject.domain, d:p.domain]">revoke</g:link> </right:canRevokeParticipant></li>
    </g:each>
</ul>