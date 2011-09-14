<%--
  @author Dmitry Kurinskiy
  @since 14.09.11 20:53
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta name="layout" content="mono"/>
    <title>Node Page</title></head>
  <body>
  
  <mk:pageHeader>${node.title}</mk:pageHeader>
  
  <p>${node.content.text}</p>
  
  <g:formatDate date="${node.dateCreated}"/>
  
  <br/>
  It's a draft: <g:formatBoolean boolean="${node.isDraft}"/>

  <mk:formActions>
    <g:if test="${node.isDraft}">
      <a href="#" class="btn">Publish node from Drafts</a>
    </g:if>
    <g:else>
      <a href="#" class="btn">Move to drafts</a>
    </g:else>
  </mk:formActions>
  
  </body>
</html>