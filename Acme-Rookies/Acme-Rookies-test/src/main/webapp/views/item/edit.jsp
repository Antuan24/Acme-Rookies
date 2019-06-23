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

<form:form action="item/provider/edit.do" modelAttribute="item">

	<security:authorize access="hasRole('PROVIDER')">
	
	<acme:textbox code="item.name" path="name"/>
	<br />
	
	<acme:textarea code="item.description" path="description"/>
	<br />
	
	<acme:textbox code="item.link" path="link"/>
	<br />
	
	<acme:textbox code="item.pictures" path="pictures"/>
	<br />
	
	<acme:submit name="save" code="item.save"/>
	<acme:cancel url="item/provider/list.do" code="item.cancel"/>
	<br />	
	
	</security:authorize>

</form:form>