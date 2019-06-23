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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<!-- 
	Recibe: Message userMessage: el mensaje a editar.
			List<Actor> actors: todos los actores del sistema.
			boolean isBroadcast: indica si el mensaje a crear es de braodcast o no.
 -->

<form:form action="message/edit.do" modelAttribute="messageForm">

	<jstl:if test="${isBroadcast==true}">
		<form:hidden path="recipients"/>
	</jstl:if>
	
	<security:authorize access="isAuthenticated()">
	
	
	<jstl:if test="${isBroadcast==false}">
	
	systems user accounts:
	<jstl:forEach items="${actors}" var="a">
		<jstl:out value="${a.userAccount.username}, "/>
	</jstl:forEach>
	<br/>
	
	<form:hidden path="isBroadcast"/>
	
	<acme:textbox code="m.recipients" path="recipients" placeholder="useraccount1,useraccount2, useraccount3 "/>
	<br />
	</jstl:if>
	
	<acme:textbox code="m.subject" path="subject"/>
	<br />	
	
	<acme:textarea code="m.body" path="body"/>
	<br />
	
	<jstl:if test="${isBroadcast==false}"> 
	<acme:textbox code="m.tags" path="tags" placeholder="tag1,tag2, tag3, tag4"/>
	</jstl:if>
	
	<jstl:if test="${isBroadcast}"> 
	<acme:textbox code="m.tags" path="tags" placeholder="tag1,tag2, tag3, tag4" readonly="true"/>
	</jstl:if> 
	
	<br />
	
	<acme:submit name="save" code="m.save"/>
	<acme:cancel url="message/list.do" code="m.cancel"/>
	<br />
	
	</security:authorize>

</form:form>