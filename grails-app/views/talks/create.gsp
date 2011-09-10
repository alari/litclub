<%--
  @author Dmitry Kurinskiy
  @since 04.09.11 13:56
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta name="layout" content="mono"/>
  <title>Create talk</title></head>

<body>

<g:form action="create" method="POST">
  <fieldset>
    <legend>Create a New Message</legend>

    <mk:formLine label="Adresate' domain" bean="${command}" field="targetDomain">
      <div class="input-prepend">
          <span class="add-on">@</span>
          <g:textField class="medium" size="16" name="targetDomain" bean="${command}"/>
        </div>
    </mk:formLine>

    <mk:formLine label="Talk topic" bean="${command}" field="topic">
      <g:textField class="medium" size="16" name="topic" bean="${command}"/>
    </mk:formLine>

    <mk:formLine label="Message" bean="${command}" field="text" isBlock="1">
      <textarea class="xxxlarge" id="text" name="text">${command.text}</textarea>
    </mk:formLine>

    <mk:formActions>
      <g:submitButton class="btn primary" name="submit" value="Send Message"/>&nbsp;<button type="reset"
                                                                                            class="btn">Cancel</button>
    </mk:formActions>

  </fieldset>
</g:form>

</body>
</html>