<%--
 * edit.jsp
 *
 * Copyright (C) 2015 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="problem/company/edit.do" modelAttribute="problemForm">

	<form:hidden path="id"/>
	<jstl:if test="${problemForm.id == 0}">
		<form:hidden path="isFinal"/>
	</jstl:if>

	<security:authorize access="hasRole('COMPANY')">
	
	<acme:textbox code="problem.title" path="title"/>
	<br />
	
	<acme:textbox code="problem.hint" path="hint"/>
	<br />
	
	<acme:textbox code="problem.statement" path="statement"/>
	<br />
	
	<acme:textbox code="problem.attachments" path="attachments"/>
	<br />
	
	<acme:selectMult items="${positions}" itemLabel="title" code="problem.position" path="positions" multiple="true"/>
	<br/>
	
	<jstl:if test="${problemForm.id != 0}">
		<spring:message code="problem.isFinal"/><form:checkbox path="isFinal"/>
		<br/>
	</jstl:if>
	
	<acme:submit name="save" code="problem.save"/>
	<acme:cancel url="problem/company/list.do" code="problem.cancel"/>
	<br />	
	
	</security:authorize>

</form:form>