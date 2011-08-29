<%@ page import="litclub.PersonalMessageHead" %>



<div class="fieldcontain ${hasErrors(bean: personalMessageHeadInstance, field: 'body', 'error')} required">
	<label for="body">
		<g:message code="personalMessageHead.body.label" default="Body" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="body" name="body.id" from="${litclub.PersonalMessageBody.list()}" optionKey="id" required="" value="${personalMessageHeadInstance?.body?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personalMessageHeadInstance, field: 'from', 'error')} required">
	<label for="from">
		<g:message code="personalMessageHead.from.label" default="From" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="from" name="from.id" from="${litclub.Person.list()}" optionKey="id" required="" value="${personalMessageHeadInstance?.from?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personalMessageHeadInstance, field: 'title', 'error')} ">
	<label for="title">
		<g:message code="personalMessageHead.title.label" default="Title" />
		
	</label>
	<g:textField name="title" value="${personalMessageHeadInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personalMessageHeadInstance, field: 'to', 'error')} required">
	<label for="to">
		<g:message code="personalMessageHead.to.label" default="To" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="to" name="to.id" from="${litclub.Person.list()}" optionKey="id" required="" value="${personalMessageHeadInstance?.to?.id}" class="many-to-one"/>
</div>

