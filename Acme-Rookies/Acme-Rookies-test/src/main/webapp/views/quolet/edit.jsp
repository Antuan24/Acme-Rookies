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

<form:form action="quolet/auditor/edit.do" modelAttribute="quolet"> 

	<security:authorize access="hasRole('AUDITOR')">
	
	<acme:textbox code="quolet.body" path="body"/>
	<br />
	
	<acme:textbox code="quolet.picture" path="picture"/>
	<br />
	
	<jstl:if test="${quolet.id != 0}">
		<spring:message code="quolet.isFinal"/><form:checkbox path="isFinal"/>
		<br/>
	</jstl:if>
	
	<acme:submit name="save" code="quolet.save"/>
	<acme:cancel url="quolet/auditor/list.do" code="quolet.cancel"/>
	<br />	
	
	</security:authorize>

</form:form>