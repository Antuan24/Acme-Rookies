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
		
		<form:form action="application/rookie/edit.do" modelAttribute="appForm">

			<form:hidden path="id"/>
			
			<acme:textbox code="app.answer" path="answer"/>
			<br />
			
			<acme:textbox code="app.codeLink" path="codeLink"/>
			<br />
			
			<acme:select items="${problems}" itemLabel="title" code="app.problem" path="problem"/>
			<br/>
			
			<acme:select items="${curriculas}" itemLabel="name" code="app.curricula" path="curricula"/>
			<br/>
			
			<acme:submit name="save" code="app.save"/>
			<acme:cancel url="/application/rookie/list.do" code="app.cancel"/>
			<br />
		
		</form:form>
		
	</security:authorize>
	
	<security:authorize access="hasRole('COMPANY')">
		
		<form:form action="application/company/edit.do" modelAttribute="appForm">

			<form:hidden path="id"/>
		
			<form:select path="status">
				<form:option label="ACCEPTED" value="ACCEPTED"/>
				<form:option label="REJECTED" value="REJECTED"/>
			</form:select>
			<form:errors cssClass="error" path="status" />
			<br />
				
			<acme:submit name="save" code="app.save"/>
			<acme:cancel url="/application/company/list.do" code="app.cancel"/>
			<br />
			
		</form:form>
		
	</security:authorize>
	
