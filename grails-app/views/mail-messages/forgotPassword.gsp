<%--
  @author Dmitry Kurinskiy
  @since 29.08.11 14:47
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
Hi ${username},<br/>
<br/>
You (or someone pretending to be you) requested that your password be reset.<br/>
<br/>
If you didn't make this request then ignore the email; no changes have been made.<br/>
<br/>
If you did make the request, then click <g:link absolute="true" controller="register" action="resetPassword" params="[t:token]">here</g:link> to reset your password.
