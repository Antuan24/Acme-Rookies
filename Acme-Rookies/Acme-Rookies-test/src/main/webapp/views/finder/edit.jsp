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

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

	<!-- PARAMETERS FROM CONTROLLER: finder: Finder, finder a editar
									 positions: Collection<Position>
					 				 -->
									 

<form:form action="finder/rookie/filter.do" modelAttribute="finder">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:hidden path="moment" />
    <form:hidden path="rookie" />
	
	
	<security:authorize access="hasRole('ROOKIE')">
	
	<acme:textbox code="finder.keyword" path="keyword"/>
	<acme:textbox code="finder.minSalary" path="minSalary"/>
	<acme:textbox code="finder.maxSalary" path="maxSalary"/>
	<acme:textbox code="finder.maxDeadline" path="maxDeadline" placeholder="01/02/2010 12:00"/>
	
	<acme:submit name="filter" code="finder.search" />
	<acme:submit name="clear" code="finder.clear" />
	</security:authorize>

</form:form>
<br/>
<jstl:if test="${cachedMessage != null}">
	<p style="color: #3a3a3a"><spring:message code="${cachedMessage}" /></p>
	<p style="color: #3a3a3a"><spring:message code="finder.cachedMoment"/>
		<jstl:out value="${finder.moment}"/></p>
	<br/>
</jstl:if>
	
<display:table name="positions" id="row" requestURI="finder/rookie/filter.do" pagesize="5">
		<spring:message code="position.deadline.format" var="formatMoment"/>
		
		<display:column property="ticker" titleKey="position.ticker"/>
		<display:column property="title" titleKey="position.title"/>
		<display:column property="description" titleKey="position.description"/>
		<display:column property="deadline" titleKey="position.deadline" format="{0,date,${formatMoment} }"/>
		<display:column property="salary" titleKey="position.salary"/>
		<display:column titleKey="position.company">
		<a href="actor/show.do?actorId=${row.company.id}"><jstl:out value="${row.company.userAccount.username}"/></a>
		</display:column>

	</display:table>

