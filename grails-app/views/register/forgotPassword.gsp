<head>
<title><g:message code='register.forgotPassword.title'/></title>
<meta name='layout' content='mono'/>
</head>

<body>

<mk:pageHeader>${message(code:'register.forgotPassword.header')}</mk:pageHeader>

	<g:if test='${emailSent}'>
    <div class="alert-message success">
	<g:message code='register.forgotPassword.sent'/>
    </div>
	</g:if>

	<g:else>

    <g:form action='forgotPassword' name="forgotPasswordForm" autocomplete='off'>

	<h4><g:message code='register.forgotPassword.description'/></h4>

      <mk:formLine labelCode="register.forgotPassword.username" field="domain">
        <g:textField name="domain" size="25" />
      </mk:formLine>

      <mk:formActions>
        <g:submitButton class="btn primary" name="submit" value="${message(code:'register.forgotPassword.submit')}"/>
      </mk:formActions>

      </g:form>

<script>
$(document).ready(function() {
	$('#username').focus();
});
</script>
	</g:else>

</body>
