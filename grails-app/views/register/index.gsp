<head>
  <meta name='layout' content='mono'/>
  <title><g:message code='register.title'/></title>
</head>

<body>

<mk:pageHeader>${message(code: 'register.title')}</mk:pageHeader>

<g:form action='register' name='registerForm'>
  <fieldset>
    <legend>${message(code: 'register.description')}</legend>




  <g:if test='${emailSent}'>
    <div class="alert-message success">
      ${message(code: 'register.confirm.sent')}
        <g:if env="test">
            <g:link class="test verify-registration" controller="register" action="verifyRegistration" params="[t:token]">TEST:confirm</g:link>
        </g:if>
    </div>
  </g:if>
  <g:else>

    <mk:formLine labelCode="person.domain.label" bean="${command}" field="domain">
      <div class="input-prepend">
        <span class="add-on">@</span>
        <g:textField class="medium" size="16" name="domain" bean="${command}"/>
      </div>
    </mk:formLine>
    <mk:formLine labelCode="person.email.label" bean="${command}" field="email">
      <g:textField class="medium" type="email" size="16" name="email" bean="${command}"/>
    </mk:formLine>

    <mk:formLine labelCode="person.password.label" bean="${command}" field="password">
      <g:passwordField class="medium" size="16" name="password" bean="${command}"/>
    </mk:formLine>

    <mk:formLine labelCode="person.password2.label" bean="${command}" field="password">
      <g:passwordField class="medium" size="16" name="password2" bean="${command}"/>
    </mk:formLine>
    </fieldset>

    <mk:formActions>
      <g:submitButton class="btn primary" name="submit"
                      value="${message(code:'register.submit')}"/>&nbsp;<button type="reset"
                                                                                                   class="btn">${message(code:'register.cancel')}</button>
    </mk:formActions>

  </g:else>

</g:form>

<script>
  $(document).ready(function() {
    $('#domain').focus();
  });
</script>

</body>
