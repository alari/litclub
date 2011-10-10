<right:canJoin union="${subject}">
    <p><strong><g:link controller="subjectAdm" action="join"
                       params="[domain:subject.domain]">${message(code:"participation.join.title")}</g:link></strong></p>
</right:canJoin>
<sbj:ifParticipate in="${subject}">
    <p><i>${message(code:"participation.do.title")} <right:canLeave union="${subject}"><g:link controller="subjectAdm" action="leave"
                                                                               params="[domain:subject.domain]">${message(code:"participation.leave.title")}</g:link></right:canLeave></i>
    </p>
</sbj:ifParticipate>
<blockquote>
    <g:each in="${parties}" var="party">
        <p><sbj:link subject="${party.subject}"/> &ndash; ${message(code:"participation.level."+party.level)}</p>
    </g:each>
</blockquote>

<pre>
    ${info.frontText}
</pre>