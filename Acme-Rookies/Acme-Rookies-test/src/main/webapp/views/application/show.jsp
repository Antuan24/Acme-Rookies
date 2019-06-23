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
        <b><spring:message code="app.moment" /></b>: <jstl:out value="${app.moment}" /> <br/>
		<b><spring:message code="app.answer"  /></b>: <jstl:out value="${app.answer}"  /> <br/> 
		<b><spring:message code="app.codeLink" /></b>: <jstl:out value="${app.codeLink}" /> <br/>
		<b><spring:message code="app.submitMoment" /></b>: <jstl:out value="${app.submitMoment}" /> <br/>
	    <b><spring:message code="app.status"  /></b>: <jstl:out value="${app.status}"  /> <br/>
	    <b><spring:message code="app.rookie"  /></b>: <a href="actor/show.do?actorId=${app.rookie.id}"><jstl:out value="${app.rookie.userAccount.username}"/></a><br/>
	    </div>
	    
	    <div>
	    <h2><spring:message code="app.problem"/>:</h2>
	    <b><spring:message code="app.problem.title"   /></b>: <jstl:out value="${app.problem.title}"   /> <br/> 
	    <b><spring:message code="app.problem.statement"   /></b>: <jstl:out value="${app.problem.statement}"   /> <br/> 
	    <b><spring:message code="app.problem.hint" /></b>: <jstl:out value="${app.problem.hint}" /> <br/>
		<b><spring:message code="app.problem.attachments" /></b>: <jstl:out value="${app.problem.attachments}" /> <br/>
		</div>
		
		<div>
		<h2><spring:message code="app.position"/>:</h2>
		<b><spring:message code="app.position.ticker" /></b>: <jstl:out value="${app.position.ticker}" /> <br/>
		<b><spring:message code="app.position.title"  /></b>: <jstl:out value="${app.position.title}"  /> <br/> 
		<b><spring:message code="app.position.description" /></b>: <jstl:out value="${app.position.description}" /> <br/>
		<b><spring:message code="app.position.deadline" /></b>: <jstl:out value="${app.position.deadline}" /> <br/>
	    <b><spring:message code="app.position.profile"  /></b>: <jstl:out value="${app.position.profile}"  /> <br/>
	    <b><spring:message code="app.position.skills"   /></b>: <jstl:out value="${app.position.skills}"   /> <br/> 
	    <b><spring:message code="app.position.salary"   /></b>: <jstl:out value="${app.position.salary}"   /> <br/> 
	    <b><spring:message code="app.position.technologies" /></b>: <jstl:out value="${app.position.technologies}" /> <br/>
		<b><spring:message code="app.position.company" /></b>: <jstl:out value="${app.position.company.commercialName}" /> <br/>
		</div>
		
		<security:authorize access="hasRole('ROOKIE')">
			<acme:cancel url="/application/rookie/list.do" code="app.back"/>
		</security:authorize>
		<security:authorize access="hasRole('COMPANY')">
			<acme:cancel url="/application/company/list.do" code="app.back"/>
		</security:authorize>
