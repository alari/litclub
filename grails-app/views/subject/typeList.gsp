<%--
  @author Dmitry Kurinskiy
  @since 14.09.11 20:19
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="mono"/>
    <title>Simple GSP page</title></head>

<body>

<mk:pageHeader>list of ${type}</mk:pageHeader>

<g:each in="${nodes}" var="node">
    <nd:link node="${node}"/>
    <br/>
</g:each>

</body>
</html>