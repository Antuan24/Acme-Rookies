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
		    <b><spring:message code="audit.moment"   /></b>: <jstl:out value="${audit.moment}"   /> <br/> 
		    <b><spring:message code="audit.text"   /></b>: <jstl:out value="${audit.text}"   /> <br/> 
		    <b><spring:message code="audit.score" /></b>: <jstl:out value="${audit.score}" /> <br/>
		    <b><spring:message code="audit.position" /></b>: <a href="position/show.do?positionId=${audit.position.id}"><jstl:out value="${audit.position.title}"/></a> <br/>
		</div>
		<br/>
		
		<acme:cancel url="${uri}" code="audit.back"/>
		
