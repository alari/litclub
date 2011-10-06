
            <p>Tell you a secret: email is <tt>${subject.email}</tt></p>

            <p>Locked: <g:formatBoolean boolean="${subject.accountLocked}"/></p>
        <blockquote>
            <g:each in="${parties}" var="party">
                <p>PARTY: ${party.level} in <sbj:link subject="${party.subject}"/></p>
            </g:each>
        </blockquote>

        <p>The text in info object is:</p>
        <hr/>
        ${info.frontText}