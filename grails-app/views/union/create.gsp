<%--
  @author Dmitry Kurinskiy
  @since 16.09.11 15:15
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="mono"/>
    <title>Create New Union</title></head>

<body>

<mk:pageHeader>create new union</mk:pageHeader>

<g:form action="create">
    <mk:formLine bean="${command}" field="domain" label="domain">
        <div class="input-prepend">
            <span class="add-on">#</span>
            <g:textField class="medium" size="16" name="domain" bean="${command}"/>
        </div>
    </mk:formLine>
    <mk:formLine bean="${command}" field="text" label="front text">
        <textarea name="text" class="xxlarge">${command.text.encodeAsHTML()}</textarea>
    </mk:formLine>

    <mk:formActions>
        <g:submitButton class="btn primary" name="submit"
                        value="${message(code:'register.submit')}"/>
        &nbsp;<button type="reset" class="btn">${message(code: 'register.cancel')}</button>
    </mk:formActions>
</g:form>

</body>
</html>