<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="actor/edit.do" modelAttribute="profileForm">

	<jstl:if test="${showPersonal}">
	
	<acme:textbox code="actor.name" path="name"/> <br/>
	<acme:textbox code="actor.surnames" path="surnames"/> <br/>
	<acme:textbox code="actor.vatNumber" path="vatNumber"/> <br/>
	<acme:textbox code="actor.photo" path="photo"/> <br/>
	<acme:textbox code="actor.email" path="email"/> <br/>
	<acme:textbox code="actor.phone" path="phone"/> <br/>
	<acme:textbox code="actor.address" path="address"/> <br/>
	
	<jstl:if test="${isCompany == true}">
	<acme:textbox code="actor.commercialName" path="commercialName"/> <br/>
	</jstl:if>
	<acme:submit name="savePersonal" code="actor.save"/>
	
	</jstl:if>
	
	
	<jstl:if test="${showAccount}">
	<acme:textbox code="actor.username" path="username"/> <br/>
	
	<acme:password code="actor.password" path="password" /> <br/>	
	<acme:password code="actor.password2" path="password2"/> <br/> 
	
	<jstl:if test="${passMatch == false}">
		<div class="error">passwords do not match</div>
	</jstl:if><br />
	
	<button type="submit" name="saveAccount" class="btn btn-primary" onclick="return confirm('This operation will log you out of the system, do you wanna continue?')">
	<spring:message code="actor.save" />
	</button>
	</jstl:if>
	
	<jstl:if test="${showCredit}">
	
	<spring:message code="actor.card"/>:<br/>
	<acme:textbox code="actor.holder" path="holder"/> <br />
	<acme:textbox code="actor.make" path="make"/> <br />
	<acme:textbox code="actor.number" path="number"/><br/>
	<acme:textbox code="actor.CVV" path="CVV"/>	<br/>
	
	<form:label path="expirationMonth">
		<spring:message code="actor.expirationMonth" />
	</form:label>
	<form:select path="expirationMonth" items="${months}"/>
	<form:errors path="expirationMonth" cssClass="error" />
	
	<br/>
	
	<form:label path="expirationYear">
		<spring:message code="actor.expirationYear" />
	</form:label>
	<form:select path="expirationYear" items="${years}"/>
	<form:errors path="expirationYear" cssClass="error" />
	
	<br/>
	
	<jstl:if test="${isExpired == true}">
		<div class="error">this is and expired date</div>
	</jstl:if><br />
	
	<acme:submit name="saveCredit" code="actor.save"/>
	</jstl:if>
	
	<acme:cancel url="actor/show.do" code="actor.cancel"/>
	
</form:form>
