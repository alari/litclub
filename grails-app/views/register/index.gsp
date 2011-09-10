<head>
  <meta name='layout' content='mono'/>
  <title><g:message code='spring.security.ui.register.title'/></title>
</head>

<body>

<g:form action='register' name='registerForm'>
  <fieldset>
    <legend>${message(code: 'spring.security.ui.register.description')}</legend>




  <g:if test='${emailSent}'>
    <div class="alert-message success">
      ${message(code: 'spring.security.ui.register.sent')}
    </div>
  </g:if>
  <g:else>

    <mk:formLine labelCode="user.domain.label" bean="${command}" field="domain">
      <div class="input-prepend">
        <span class="add-on">@</span>
        <g:textField class="medium" size="16" name="domain" bean="${command}"/>
      </div>
    </mk:formLine>
    <mk:formLine labelCode="user.email.label" bean="${command}" field="email">
      <g:textField class="medium" size="16" name="email" bean="${command}"/>
    </mk:formLine>

    <mk:formLine labelCode="user.password.label" bean="${command}" field="password">
      <g:passwordField class="medium" size="16" name="password" bean="${command}"/>
    </mk:formLine>

    <mk:formLine labelCode="user.password2.label" bean="${command}" field="password">
      <g:passwordField class="medium" size="16" name="password2" bean="${command}"/>
    </mk:formLine>
    </fieldset>

    <mk:formActions>
      <g:submitButton class="btn primary" name="submit"
                      value="${message(code:'spring.security.ui.register.submit')}"/>&nbsp;<button type="reset"
                                                                                                   class="btn">Cancel</button>
    </mk:formActions>

  </g:else>

</g:form>

<script>
  $(document).ready(function() {
    $('#domain').focus();
  });
</script>

</body>
