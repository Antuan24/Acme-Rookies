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
		    <b><spring:message code="problem.title"   /></b>: <jstl:out value="${problem.title}"   /> <br/> 
		    <b><spring:message code="problem.statement"   /></b>: <jstl:out value="${problem.statement}"   /> <br/> 
		    <b><spring:message code="problem.hint" /></b>: <jstl:out value="${problem.hint}" /> <br/>
			<b><spring:message code="problem.attachments" /></b>: <jstl:out value="${problem.attachments}" /> <br/>
		</div>
		
		<display:table name="positions" id="row" pagesize="5">
		
			<display:column property="ticker" titleKey="position.ticker"/>
			<display:column property="title" titleKey="position.title"/>
			<display:column property="description" titleKey="position.description"/>
			<display:column titleKey="position.company.commercialName">
			<a href="actor/show.do?actorId=${row.company.id}"><jstl:out value="${row.company.userAccount.username}"/></a>
			</display:column>
	        <spring:message code="position.moment.format" var="formatMoment"/>              
	        <display:column titleKey="position.deadline" property="deadline" format="{0,date,${formatMoment}}"/> 
		
		</display:table>
		
		<acme:cancel url="/problem/company/list.do" code="app.back"/>
		
