<%--
  @author Dmitry Kurinskiy
  @since 09.09.11 10:42
--%>

<%@ page import="litclub.I18n" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta charset="utf-8"/>
  <title><g:layoutTitle default="${message(code:'layout.title')}"/></title>
  <g:layoutHead/>
  <r:require module="jquery"/>
  <r:require module="twitterBootstrap"/>
  <r:layoutResources/>
  <style type="text/css">
  body{padding-top:54px}
  </style>
</head>

<body>
<div class="topbar">
  <div class="topbar-inner">
    <div class="container">
      <h3><g:link uri="/">${message(code: "layout.title")}</g:link></h3>
      <ul class="nav">

        <sec:ifLoggedIn>
          <li><sbj:link/></li>
          <li><g:link controller="logout">${message(code: "layout.logout")}</g:link></li>
          <li><g:link controller="talks">${message(code: "layout.talks")} (<talk:newCount/>)</g:link></li>
          <li class="dropdown">
            <a href="#" class="dropdown-toggle">Dropdown</a>
              <ul class="dropdown-menu">
                <li><a href="#">Secondary link</a></li>
                <li><a href="#">Something else here</a></li>
                <li class="divider"></li>
                <li><a href="#">Another link</a></li>
              </ul>
          </li>
        </sec:ifLoggedIn>
        <sec:ifNotLoggedIn>
          <li><g:link controller="register">${message(code: "layout.register")}</g:link></li>
          <li><g:link controller="login">${message(code: "layout.login")}</g:link></li>
        </sec:ifNotLoggedIn>

      </ul>
    </div></div></div>




<g:if test="${flash.message}">
  <div class="alert-message info">
    <a class="close" href="#">&times;</a>

    <p>${flash.message}</p>
  </div>
</g:if>
<g:if test="${flash.error}">
  <div class="alert-message error">
    <a class="close" href="#">&times;</a>

    <p>${flash.error}</p>
  </div>
</g:if>

<g:layoutBody/>

<div id="footer">
  <div class="inner">
    <div class="container">
    &copy; ${message(code: "layout.footer.copyright")}
    </div></div></div>

<r:layoutResources/>

</body>
</html>