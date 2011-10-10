<%--
  @author Dmitry Kurinskiy
  @since 14.09.11 20:19
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="mono"/>
    <title>TODO: nodes list</title></head>

<body>

<mk:pageHeader>${type}</mk:pageHeader>

<g:each in="${nodes}" var="node">
    <nd:link node="${node}"/>
    <br/>
</g:each>

</body>
</html>