<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="position/company/edit.do" modelAttribute="position">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<form:hidden path="company"/>
	<form:hidden path="sponsorships"/>
	<form:hidden path="finders"/>
	<jstl:if test="${position.id == 0}"> 
	<form:hidden path="isFinal"/> 
	</jstl:if> 
	<form:hidden path="ticker"/> 
	
	<security:authorize access="hasRole('COMPANY')">
	
	<acme:textbox code="position.title" path="title"/> <br/>
	
	<acme:textbox code="position.description" path="description"/> <br/>
	
	<acme:textbox code="position.deadline" path="deadline"/> <br/>
	
	<acme:textbox code="position.profile" path="profile"/> <br/>
	
	<acme:textbox code="position.skills" path="skills"/> <br/>
	
	<acme:textbox code="position.technologies" path="technologies"/> <br/>
	
	<acme:textbox code="position.salary" path="salary"/> <br/>
	
	<jstl:if test="${position.id != 0}">
	
	<spring:message code="position.isFinal" />: <form:checkbox path="isFinal"/> <br/> 
	
	</jstl:if>
	
	<!-- <jstl:if test="${position.id != 0}">
	
	<form:label path="isFinal">
			<spring:message code="position.isFinal" />:
		</form:label>
		<form:select id="isFinal" path="isFinal">
			<form:option label="True" value="True"/>
			<form:option label="False" value="False"/>
		</form:select>  
		<form:errors cssClass="error" path="isFinal" />
		<br />
	</jstl:if> -->
	
	<acme:submit name="save" code="position.save"/>
	
	<jstl:if test="${position.id != 0}">
		<acme:submit name="delete" code="position.delete"/>
	</jstl:if>
	
	<acme:cancel url="position/company/list.do" code="position.cancel"/>
	<br />
	
	</security:authorize>

</form:form>