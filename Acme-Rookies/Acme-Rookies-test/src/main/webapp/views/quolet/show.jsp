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
		    <b><spring:message code="quolet.ticker"   /></b>: <jstl:out value="${quolet.ticker}"   /> <br/> 
		    <b><spring:message code="quolet.body"   /></b>: <jstl:out value="${quolet.body}"   /> <br/> 
		    <b><spring:message code="quolet.picture" /></b>: <jstl:out value="${quolet.picture}" /> <br/>
			<b><spring:message code="quolet.publicationMoment" /></b>: <jstl:out value="${quolet.publicationMoment}" /> <br/>
			<b><spring:message code="quolet.isFinal" /></b>: <jstl:out value="${quolet.isFinal}" /> <br/>
		</div>
		
		<acme:cancel url="/quolet/auditor/list.do" code="quolet.back"/>
		
