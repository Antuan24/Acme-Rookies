<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

 <form:form action="${requestURI}" modelAttribute="positionFinderForm">
	
	<form:hidden path="companyId"/>
	<acme:textbox code="position.keyword" path="keyword"/>
	
	<acme:submit name="list" code="position.finder.search" />
	<acme:submit name="cancel" code="position.finder.cancel"/>
</form:form>
<br/> 

	<display:table name="positions" id="row" requestURI="${requestURI}" pagesize="5">
	
		<display:column>
		
		<a href="position/show.do?positionId=${row.id}"><spring:message code="position.show" /></a> <br/>
		
		<security:authorize access="hasRole('ROOKIE')">
			<jstl:if test="${row.isFinal==true}">
				<a href="application/rookie/create.do?positionId=${row.id}"><spring:message code="position.application"   /></a> <br/>
			</jstl:if>
		</security:authorize>
		
		<security:authorize access="hasRole('COMPANY')">
			<jstl:if test="${row.isFinal == false}"> <%-- and "${isLogged}" == true --%>
				<a href="position/company/edit.do?positionId=${row.id}">  <spring:message code="position.edit"   /></a> <br/>
				<a href="position/company/delete.do?positionId=${row.id}"><spring:message code="position.delete" /></a> <br/>
			</jstl:if>
		</security:authorize>
		
		</display:column>
		
		<display:column property="ticker" titleKey="position.ticker"/>
		<display:column property="title" titleKey="position.title"/>
		<display:column property="description" titleKey="position.description"/>

		
		<display:column titleKey="position.company.commercialName">
		<a href="actor/show.do?actorId=${row.company.id}"><jstl:out value="${row.company.userAccount.username}"/></a>
		</display:column>
   <%-- <display:column property="company" titleKey="position.company.commercialName"/> --%>
        <spring:message code="position.moment.format" var="formatMoment"/>              
        <display:column titleKey="position.deadline" property="deadline" format="{0,date,${formatMoment}}"/> 
   <%-- <display:column property="deadline" titleKey="position.deadline"/>         --%>
   <%-- <display:column property="profile" titleKey="position.profile"/>           --%>
   <%-- <display:column property="skills" titleKey="position.skills"/>             --%>
   <%-- <display:column property="technologies" titleKey="position.technologies"/> --%>
   <%-- <display:column property="salary" titleKey="position.salary"/>             --%>
		
	</display:table>
	
	<security:authorize access="hasRole('COMPANY')">
	<div>
	<a href="position/company/create.do"> <spring:message code="position.create" /> </a>
	</div>
	</security:authorize>
