<p>This is a UNION</p>
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