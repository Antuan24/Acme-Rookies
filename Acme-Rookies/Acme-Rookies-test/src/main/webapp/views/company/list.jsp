<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="companies" id="row" requestURI="company/list.do" pagesize="5">
		<display:column titleKey="company.action">
			<a href="actor/show.do?actorId=${row.id}"><spring:message code="company.show"/></a><br>
			<a href="position/list.do?companyId=${row.id}"><spring:message code="company.showPosition"/> </a>
		</display:column>
		
		<display:column property="name" titleKey="company.name"/>
		<display:column property="surnames" titleKey="company.surnames"/>
		<display:column property="photo" titleKey="company.photo"/>
		<display:column property="commercialName" titleKey="company.commercialName"/>
		
		
	</display:table>