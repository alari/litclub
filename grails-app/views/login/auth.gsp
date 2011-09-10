<%@ page import="org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils" %>
<head>
<title><g:message code='spring.security.ui.login.title'/></title>
<meta name='layout' content='mono'/>
</head>

<body>

	<form action='${postUrl}' method='POST' id="loginForm" name="loginForm" autocomplete='off'>
    <fieldset>
      <legend><g:message code='spring.security.ui.login.signin'/></legend>

      <mk:formLine labelCode="spring.security.ui.login.username" field="username">
        <input name="${SpringSecurityUtils.getSecurityConfig().apf.usernameParameter}" id="username" size="20" />
      </mk:formLine>
      <mk:formLine labelCode="spring.security.ui.login.password" field="password">
        <input type="password" name="${SpringSecurityUtils.getSecurityConfig().apf.passwordParameter}" id="password" size="20" />
      </mk:formLine>

      <div>
        				<input type="checkbox" class="checkbox" name="${rememberMeParameter}" id="remember_me" checked="checked" />
				<label for='remember_me'><g:message code='spring.security.ui.login.rememberme'/></label> |
				<span class="forgot-link">
					<g:link controller='register' action='forgotPassword'><g:message code='spring.security.ui.login.forgotPassword'/></g:link>
				</span>
      </div>
    </fieldset>

    <mk:formActions>
      <input type="submit" value="${message(code:'spring.security.ui.login.login')}" class="btn primary"/>
      <g:link controller="register" class="btn info">${message(code:'spring.security.ui.login.register')}</g:link>
    </mk:formActions>
  </form>

<script>
$(document).ready(function() {
	$('#username').focus();
});

<s2ui:initCheckboxes/>

</script>

</body>
