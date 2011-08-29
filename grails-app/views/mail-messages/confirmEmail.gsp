<%--
  @author Dmitry Kurinskiy
  @since 27.08.11 22:20
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head><title>Simple GSP page</title></head>
  <body>
  Hi ${user.domain},<br/>
<br/>
You (or someone pretending to be you) created an account with this email address.<br/>
<br/>
If you made the request, please click <a href="${url}">here</a> to finish the registration.</body>
</html>