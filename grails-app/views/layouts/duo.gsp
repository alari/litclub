<%--
  @author Dmitry Kurinskiy
  @since 09.09.11 10:51
--%>

<%@ page import="litclub.I18n" contentType="text/html;charset=UTF-8" %>
<g:applyLayout name="base">
  <html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title><g:layoutTitle/></title>
    <g:layoutHead/>
  </head>

  <body>

  <section id="middle">

    <div id="container">
      <div id="content">

        <g:layoutBody/>
      </div><!-- #content-->
    </div><!-- #container-->

    <aside id="sideRight">
      right side
    </aside><!-- #sideRight -->

  </section><!-- #middle-->

  </body>
  </html></g:applyLayout>