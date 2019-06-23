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


	
	<security:authorize access="hasRole('ROOKIE')">
		
		<form:form action="curricula/rookie/editForm.do" modelAttribute="form">
			
			Curricula:<br>
			<acme:textbox code="curricula.name" path="name"/>
			<br>
			
			Personal Data:<br>
			<acme:textbox code="personalData.fullName" path="fullName"/>
			<br>
			
			<acme:textbox code="personalData.statement" path="statement"/>
			<br>
			
			<acme:textbox code="personalData.phone" path="phone"/>
			<br>
			
			<acme:textbox code="personalData.githubUrl" path="githubUrl"/>
			<br>
			
			<acme:textbox code="personalData.linkedInUrl" path="linkedInUrl"/>
			<br>
			
			<acme:submit name="save" code="data.save"/>
			<acme:cancel url="/curricula/rookie/list.do" code="data.cancel"/>
			<br />
		
		</form:form>
		
	</security:authorize>
	
