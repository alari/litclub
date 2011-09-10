<head>
<title><g:message code='spring.security.ui.resetPassword.title'/></title>
<meta name='layout' content='register'/>
</head>

<body>
<mk:pageHeader>${message(code:'spring.security.ui.resetPassword.header')}</mk:pageHeader>

	<g:form action='resetPassword' name='resetPasswordForm' autocomplete='off'>
	<g:hiddenField name='t' value='${token}'/>

	<h4><g:message code='spring.security.ui.resetPassword.description'/></h4>

    <mk:formLine labelCode="resetPasswordCommand.password.label" bean="${command}" field="password">
      <g:passwordField name="password"/>
    </mk:formLine>

    <mk:formLine labelCode="resetPasswordCommand.password2.label" bean="${command}" field="password2">
      <g:passwordField name="password2"/>
    </mk:formLine>

    <mk:formActions>
      <g:submitButton class="btn primary" name="submit" value="${message(code:'spring.security.ui.resetPassword.submit')}"/>
    </mk:formActions>


	</g:form>

<script>
$(document).ready(function() {
	$('#password').focus();
});
</script>

</body>
