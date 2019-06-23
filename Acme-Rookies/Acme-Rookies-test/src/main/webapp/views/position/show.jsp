<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

		<div>
        <b><spring:message code="position.ticker" /></b>: <jstl:out value="${position.ticker}" /> <br/>
		<b><spring:message code="position.title"  /></b>: <jstl:out value="${position.title}"  /> <br/> 
		<b><spring:message code="position.description" /></b>: <jstl:out value="${position.description}" /> <br/>
		<spring:message    code="position.moment.format" var="formatMoment"/>
		<b><spring:message code="position.deadline" /></b>: <jstl:out value="${position.deadline}" /> <br/>
	    <b><spring:message code="position.profile"  /></b>: <jstl:out value="${position.profile}"  /> <br/>
	    <b><spring:message code="position.skills"   /></b>: <jstl:out value="${position.skills}"   /> <br/> 
	    <b><spring:message code="position.salary"   /></b>: <jstl:out value="${position.salary}"   /> <br/> 
	    <b><spring:message code="position.technologies" /></b>: <jstl:out value="${position.technologies}" /> <br/>
		<b><spring:message code="position.company" /></b>: <jstl:out value="${position.company.commercialName}" /> <br/>
		</div>
	
	<h3><spring:message code="position.audits"/></h3>
	<display:table name="audits" id="row" requestURI="position/show.do" pagesize="5">
		
		<display:column>
			<a href="audit/show.do?auditId=${row.id}"><spring:message code="audit.show"/></a><br/>
		</display:column>
		<spring:message code="audit.moment.format" var="formatMoment"/>
		<display:column titleKey="audit.moment" property="moment" format="{0,date,${formatMoment} }"/>
		<display:column property="score" titleKey="audit.score"/>
		<display:column titleKey="audit.position">
			<a href="position/show.do?positionId=${row.position.id}"><jstl:out value="${row.position.title}"/></a>
		</display:column>
	</display:table>
		
		<jstl:if test="${sponsorship != null}">
			<a href="${sponsorship.target}" target="_blank"><img src="${sponsorship.banner}" height="100" width="1000"/></a>
		</jstl:if>
		
		<jstl:if test="${sponsorship == null}">No active sponsorships were found</jstl:if>
		
	
	<br/>
		<acme:cancel url="/position/list.do" code="position.back"/>
		
