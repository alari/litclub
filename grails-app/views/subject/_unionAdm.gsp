<%@ page import="litclub.morphia.subject.ParticipationPolicy; litclub.morphia.subject.ParticipationPolicy" %>
<%--
  @author Dmitry Kurinskiy
  @since 10/6/11 9:16 PM
--%>

<g:form controller="subjectAdm" action="participationPolicy" params="[domain:subject.domain]" method="POST">
    <mk:formLine bean="${info}" field="participationPolicy" labelCode="participation.policy.select">
        <select name="participationPolicy">
            <g:each in="${ParticipationPolicy.values()}" var="mp">
                <option value="${mp.toString()}" ${info.participationPolicy == mp ? ' selected' : ""}>${message(code: "participation.policy." + mp.toString())}</option>
            </g:each>
        </select>

    </mk:formLine>
    <mk:formActions>
        <g:submitButton name="publish" value="${message(code:'participation.policy.save')}" class="btn primary"/>
    </mk:formActions>

    <g:each in="${parties}" var="party">
        <p><sbj:link subject="${party.subject}"/> &ndash; ${message(code:"participation.level."+party.level.toString())}</p>
    </g:each>
</g:form>