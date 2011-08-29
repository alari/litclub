<%--
  @author Dmitry Kurinskiy
  @since 27.08.11 22:20
--%>

<%@ page import="litclub.Person" contentType="text/html;charset=UTF-8" %>
  Hi ${Person.get(personId).domain},<br/>
<br/>
You (or someone pretending to be you) created an account with this email address.<br/>
<br/>
If you made the request, please click <g:link absolute="true" controller="register" action="verifyRegistration" params="[t:token]">here</g:link> to finish the registration.