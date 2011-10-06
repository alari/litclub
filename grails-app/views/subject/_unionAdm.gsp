<%@ page import="litclub.morphia.subject.MembershipPolicy" %>
<%--
  @author Dmitry Kurinskiy
  @since 10/6/11 9:16 PM
--%>

<g:form controller="subjectAdm" action="membershipPolicy" params="[domain:subject.domain]" method="POST">
    <mk:formLine bean="${info}" field="membershipPolicy" labelCode="membershipPolicy.select">
        <select name="membershipPolicy">
            <g:each in="${MembershipPolicy.values()}" var="mp">
                <option value="${mp.toString()}"${info.membershipPolicy==mp?' selected':""}>${message(code:"membershipPolicy."+mp.toString())}</option>
            </g:each>
        </select>

    </mk:formLine>
    <mk:formActions>
         <g:submitButton name="publish" value="save changes" class="btn primary"/>
    </mk:formActions>

    <g:each in="${parties}" var="party">
        <p>PARTY: ${party.level} is <sbj:link subject="${party.subject}"/></p>
    </g:each>
</g:form>