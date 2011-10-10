<%--
  @author Dmitry Kurinskiy
  @since 14.09.11 20:53
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="mono"/>
    <title>${node.title}</title></head>

<body>

<mk:pageHeader>${node.title}</mk:pageHeader>

<p>${node.content.text}</p>

<g:formatDate date="${node.dateCreated}"/>

<br/>
<g:if test="${node.isDraft}"><b>${message(code:"node.draft.title")}</b></g:if>

<mk:formActions>
    <nd:link node="${node}" action="draft">
        ${message(code:(node.isDraft ? "node.draft.publish" : "node.draft.moveTo"))}</nd:link>
    |
    <nd:link node="${node}" action="edit">${message(code:"node.edit")}</nd:link>
    |
    <nd:link node="${node}" action="delete">${message(code:"node.edit")}</nd:link>
</mk:formActions>

</body>
</html>