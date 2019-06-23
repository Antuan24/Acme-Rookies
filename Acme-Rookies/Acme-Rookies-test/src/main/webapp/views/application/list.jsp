<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('ROOKIE')||hasRole('COMPANY')">
<form:form action="${requestURI}" modelAttribute="applicationFinderForm">
	
	<form:label path="status">
		<spring:message code="app.status" />
	</form:label>	
	<form:select path="status">
		<form:option value="0" label="----" />		
		<form:options items="${statuses}" />
	</form:select>
	<form:errors path="${path}" cssClass="error" />
	
	<acme:submit name="list" code="app.finder.search" />
	<acme:submit name="cancel" code="app.finder.cancel"/>
</form:form>
</security:authorize>
<security:authorize access="hasRole('ROOKIE')">

	<display:table name="apps" id="row" requestURI="application/rookie/list.do" pagesize="5">
		
		<display:column>
		<a href="application/rookie/show.do?appId=${row.id}"><spring:message code="app.show" /></a> <br/>
		<jstl:if test="${row.status=='PENDING'}">
		<a href="application/rookie/edit.do?appId=${row.id}">  <spring:message code="app.edit"   /></a> <br/>
		</jstl:if>
		</display:column>
		
		<spring:message code="app.moment.format" var="formatMoment"/>
		<display:column titleKey="app.moment" property="moment" format="{0,date,${formatMoment} }"/>
		<display:column property="status" titleKey="app.status"/>
		<display:column property="problem.title" titleKey="app.problem"/>
		<display:column property="curricula.name" titleKey="app.curricula"/>
		<display:column property="position.title" titleKey="app.position"/>
		
	</display:table>

</security:authorize>

<security:authorize access="hasRole('COMPANY')">

	<display:table name="apps" id="row" requestURI="application/company/list.do" pagesize="5">
		
		<display:column>
		<a href="application/company/show.do?appId=${row.id}"><spring:message code="app.show" /></a> <br/>
		<jstl:if test="${row.status=='SUBMITTED'}">
		<a href="application/company/edit.do?appId=${row.id}">  <spring:message code="app.edit"   /></a> <br/>
		</jstl:if>
		</display:column>
		
		<display:column property="moment" titleKey="app.moment"/>
		<display:column property="status" titleKey="app.status"/>
		<display:column property="problem.title" titleKey="app.problem"/>
		<display:column property="curricula.name" titleKey="app.curricula"/>
		<display:column property="position.title" titleKey="app.position"/>
		
	</display:table>

</security:authorize>
