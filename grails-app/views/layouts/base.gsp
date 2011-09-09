<%--
  @author Dmitry Kurinskiy
  @since 09.09.11 10:42
--%>

<%@ page import="litclub.I18n" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta charset="utf-8"/>
  <!--[if IE]><script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
  <title><g:layoutTitle default="${message(code:"layout.title")}"/></title>
  <!--[if lte IE 6]><link rel="stylesheet" href="${resource(dir: 'css/layout', file: 'ie.css')}" type="text/css" media="screen, projection" /><![endif]-->
  <link rel="stylesheet" href="${resource(dir: 'css/layout', file: 'base.css')}" type="text/css"
        media="screen, projection"/>
  <g:layoutHead/>
  <r:require module="jquery"/>
  <r:layoutResources/>
</head>

<body>

<div id="wrapper">

  <header id="header">
    <g:link uri="/">${message(code: "layout.title")}</g:link>
    <sec:ifLoggedIn><g:link controller="logout">${message(code: "layout.logout")}</g:link></sec:ifLoggedIn>
  </header><!-- #header-->

  <g:if test="${flash.message}">
    <div id="message"><p>${flash.message}</p></div>
    <jq:jquery>$("#message").dialog();</jq:jquery>
  </g:if>
  <g:if test="${flash.error}">
    <div id="error"><p>${flash.error}</p></div>
    <jq:jquery>$("#error").dialog();</jq:jquery>
  </g:if>

  <g:layoutBody/>

</div><!-- #wrapper -->

<footer id="footer">
&copy; ${message(code: "layout.footer.copyright")}
</footer><!-- #footer -->

<r:layoutResources/>

</body>
</html>