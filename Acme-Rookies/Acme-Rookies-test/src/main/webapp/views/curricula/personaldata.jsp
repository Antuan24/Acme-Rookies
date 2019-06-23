<%--
 * edit.jsp
 *
 * Copyright (C) 2015 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<security:authorize access="hasRole('ROOKIE')">

	<form:form action="curricula/personalData/edit.do"
		modelAttribute="personalData">

		<form:hidden path="id" />
		<form:hidden path="version" />

		<acme:textbox code="personalData.fullName" path="fullName" />
		<acme:textbox code="personalData.statement" path="statement" />
		<acme:textbox code="personalData.phone" path="phone" />
		<acme:textbox code="personalData.githubUrl" path="githubUrl" />
		<acme:textbox code="personalData.linkedInUrl" path="linkedInUrl" />


		<acme:submit code="data.save" name="save" />

		<input type="button" name="back"
			value="<spring:message code="data.back" />"
			onclick="javascript: window.location.replace('')" />
		<br />
	</form:form>
</security:authorize>

