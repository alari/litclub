
<%@ page import="litclub.PersonalMessageHead" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'personalMessageHead.label', default: 'PersonalMessageHead')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-personalMessageHead" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-personalMessageHead" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="personalMessageHead.body.label" default="Body" /></th>
					
						<g:sortableColumn property="dateCreated" title="${message(code: 'personalMessageHead.dateCreated.label', default: 'Date Created')}" />
					
						<th><g:message code="personalMessageHead.from.label" default="From" /></th>
					
						<g:sortableColumn property="title" title="${message(code: 'personalMessageHead.title.label', default: 'Title')}" />
					
						<th><g:message code="personalMessageHead.to.label" default="To" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${personalMessageHeadInstanceList}" status="i" var="personalMessageHeadInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${personalMessageHeadInstance.id}">${fieldValue(bean: personalMessageHeadInstance, field: "body")}</g:link></td>
					
						<td><g:formatDate date="${personalMessageHeadInstance.dateCreated}" /></td>
					
						<td>${fieldValue(bean: personalMessageHeadInstance, field: "from")}</td>
					
						<td>${fieldValue(bean: personalMessageHeadInstance, field: "title")}</td>
					
						<td>${fieldValue(bean: personalMessageHeadInstance, field: "to")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${personalMessageHeadInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
