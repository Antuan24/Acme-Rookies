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

<form:form action="audit/auditor/edit.do" modelAttribute="audit">

	<form:hidden path="id"/>
	<form:hidden path="version"/>

	<security:authorize access="hasRole('AUDITOR')">
	
	<acme:textbox code="audit.text" path="text"/>
	<br />
	
	<acme:textbox code="audit.score" path="score"/>
	<br />
	<jstl:if test="${audit.id!=0}">
		<acme:checkbox code="audit.isFinal" path="isFinal"/>
		<br />
	</jstl:if>
	<acme:select items="${positions}" itemLabel="title" code="audit.position" path="position"/>
	<br/>
	
	<acme:submit name="save" code="audit.save"/>
	<acme:cancel url="audit/auditor/list.do" code="audit.cancel"/>
	<br />	
	
	</security:authorize>

</form:form>