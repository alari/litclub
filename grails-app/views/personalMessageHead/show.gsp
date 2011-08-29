
<%@ page import="litclub.PersonalMessageHead" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'personalMessageHead.label', default: 'PersonalMessageHead')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-personalMessageHead" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-personalMessageHead" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list personalMessageHead">
			
				<g:if test="${personalMessageHeadInstance?.body}">
				<li class="fieldcontain">
					<span id="body-label" class="property-label"><g:message code="personalMessageHead.body.label" default="Body" /></span>
					
						<span class="property-value" aria-labelledby="body-label"><g:link controller="personalMessageBody" action="show" id="${personalMessageHeadInstance?.body?.id}">${personalMessageHeadInstance?.body?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${personalMessageHeadInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="personalMessageHead.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${personalMessageHeadInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${personalMessageHeadInstance?.from}">
				<li class="fieldcontain">
					<span id="from-label" class="property-label"><g:message code="personalMessageHead.from.label" default="From" /></span>
					
						<span class="property-value" aria-labelledby="from-label"><g:link controller="person" action="show" id="${personalMessageHeadInstance?.from?.id}">${personalMessageHeadInstance?.from?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${personalMessageHeadInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="personalMessageHead.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${personalMessageHeadInstance}" field="title"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${personalMessageHeadInstance?.to}">
				<li class="fieldcontain">
					<span id="to-label" class="property-label"><g:message code="personalMessageHead.to.label" default="To" /></span>
					
						<span class="property-value" aria-labelledby="to-label"><g:link controller="person" action="show" id="${personalMessageHeadInstance?.to?.id}">${personalMessageHeadInstance?.to?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${personalMessageHeadInstance?.id}" />
					<g:link class="edit" action="edit" id="${personalMessageHeadInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
