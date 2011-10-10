<p>Tell you a secret: email is <tt>${subject.email}</tt></p>

<p>Locked: <g:formatBoolean boolean="${subject.accountLocked}"/></p>
<blockquote>
    <g:each in="${parties}" var="party">
        <p>${message(code:"participation.level."+party.level)}: <sbj:link subject="${party.subject}"/></p>
    </g:each>
</blockquote>

<hr/>
${info.frontText}