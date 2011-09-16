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
  It's a draft: <g:formatBoolean boolean="${node.isDraft}"/>

  <mk:formActions>
      <nd:link node="${node}" action="draft">
        ${(node.isDraft ? "Publish node from Drafts" : "Move to drafts")}</nd:link>
    |
    <nd:link node="${node}" action="edit">Edit Node</nd:link>
    |
    <nd:link node="${node}" action="delete">Delete Node</nd:link>
  </mk:formActions>
  
  </body>
</html>