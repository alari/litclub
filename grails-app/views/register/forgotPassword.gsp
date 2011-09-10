<head>
<title><g:message code='spring.security.ui.forgotPassword.title'/></title>
<meta name='layout' content='mono'/>
</head>

<body>

<mk:pageHeader>${message(code:'spring.security.ui.forgotPassword.header')}</mk:pageHeader>

	<g:if test='${emailSent}'>
    <div class="alert-message success">
	<g:message code='spring.security.ui.forgotPassword.sent'/>
    </div>
	</g:if>

	<g:else>

    <g:form action='forgotPassword' name="forgotPasswordForm" autocomplete='off'>

	<h4><g:message code='spring.security.ui.forgotPassword.description'/></h4>

      <mk:formLine labelCode="spring.security.ui.forgotPassword.username" field="domain">
        <g:textField name="domain" size="25" />
      </mk:formLine>

      <mk:formActions>
        <g:submitButton class="btn primary" name="submit" value="${message(code:'spring.security.ui.forgotPassword.submit')}"/>
      </mk:formActions>

      </g:form>

<script>
$(document).ready(function() {
	$('#username').focus();
});
</script>
	</g:else>

</body>
