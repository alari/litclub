<p>This is a UNION</p>
<right:canJoin union="${subject}">
    <p><strong><g:link controller="subjectAdm" action="join" params="[domain:subject.domain]">YOU MAY JOIN</g:link></strong></p>
</right:canJoin>
<sbj:ifParticipate in="${subject}">
    <p><i>You do participate there. <right:canLeave union="${subject}"><g:link controller="subjectAdm" action="leave" params="[domain:subject.domain]">YOU MAY LEAVE</g:link></right:canLeave> </i></p>
</sbj:ifParticipate>
        <blockquote>
            <g:each in="${parties}" var="party">
                <p>PARTY: ${party.level} in <sbj:link subject="${party.subject}"/></p>
            </g:each>
        </blockquote>

        <p>The text in info object is:</p>
        <hr/>
<pre>
        ${info.frontText}
</pre>