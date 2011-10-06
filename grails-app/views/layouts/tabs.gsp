<%--
  @author Dmitry Kurinskiy
  @since 06.10.11 10:50
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<g:applyLayout name="base">
    <html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title><g:layoutTitle/></title>
        <r:require module="twitterTabs"/>
        <g:layoutHead/>
    </head>

    <body>

    <div class="container">

        <ul class="tabs" data-tabs="tabs">
            <g:each in="${request.tabs}" var="tab">
                <li${tab.active ? / class="active"/ : ""}><a href="${tab.link}">${message(code: tab.labelCode)}</a></li>
            </g:each>
        </ul>

        <g:layoutBody/>

        <div class="pill-content" data-pills="pills">
            <g:each in="${request.tabs}" var="tab">
                <div${tab.active ? / class="active"/ : ""} id="${tab.link.substring(1)}">${tab.body}</div>
            </g:each>
        </div>

    </div>

    </body>
    </html></g:applyLayout>