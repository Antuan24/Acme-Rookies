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
		<b><spring:message code="sponsorship.banner"/></b>: <a href="${sponsorship.banner}"><jstl:out value="${sponsorship.banner}"/></a> <br/>
		<img src="${sponsorship.banner}" height="100" width="1000"/><br/>
		
		<b><spring:message code="sponsorship.target"/></b>: <a href="${sponsorship.target}"><jstl:out value="${sponsorship.target}"/></a> <br/>		
		
		<b><spring:message code="sponsorship.provider"/></b>:<a href="actor/show.do?actorId=${sponsorship.provider.id}">
		<jstl:out value="${sponsorship.provider.userAccount.username}"/></a> <br/>	
			 				 
		<b><spring:message code="sponsorship.position"/></b>: <jstl:out value="${sponsorship.position.title}"/> <br/>
		
		<b><spring:message code="sponsorship.provider.make"/></b>: <jstl:out value="${sponsorship.provider.make}"/> <br/>				 				 
	</div>
	<br/>
		<acme:cancel url="" code="sponsorship.back"/>
	<br/>
