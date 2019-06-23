<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('AUDITOR')">
	
	<fmt:formatDate value="${current1}" pattern="MM" var="month1"/>
	<fmt:formatDate value="${current2}" pattern="MM" var="month2"/>
	<jstl:set var="status" />
	
	<display:table name="quolets" id="row" requestURI="quolet/auditor/list.do" pagesize="5">
		
		<fmt:formatDate value="${row.publicationMoment}" pattern="MM" var="date"/>
		<jstl:if test="${month1 - date == 0}">
			<jstl:set var="status" value="MIN"/>
		</jstl:if>
		<jstl:if test="${month2 - date == 0}">
			<jstl:set var="status" value="MED"/>
		</jstl:if>
		<jstl:if test="${month1 - date != 0 && month2 - date != 0}">
			<jstl:set var="status" value="MAX"/>
		</jstl:if>
		<display:column> 
		<jstl:out value="${status}"/>
		
		</display:column>
		<display:column class="${status}">
			<a href="quolet/auditor/show.do?quoletId=${row.id}"><spring:message code="quolet.show"/></a><br/>
			<jstl:if test="${row.isFinal == false}">
				<a href="quolet/auditor/edit.do?quoletId=${row.id}"><spring:message code="quolet.edit"/></a><br/>
				<a href="quolet/auditor/delete.do?quoletId=${row.id}"><spring:message code="quolet.delete"/></a><br/>
			</jstl:if>
		</display:column>
		<display:column property="ticker" titleKey="quolet.ticker" class="${status}"/>
		<display:column property="body" titleKey="quolet.body" class="${status}"/>
		<spring:message code="position.moment.format" var="formatMoment"/>
		<display:column property="publicationMoment" titleKey="quolet.publicationMoment" format="{0,date,${formatMoment}}" class="${status}"/>
		<display:column property="audit.text" titleKey="quolet.audit" class="${status}"/>
		
	</display:table>

		
</security:authorize>
