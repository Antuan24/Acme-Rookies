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

<form:form action="socialProfile/edit.do" modelAttribute="socialProfile">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:textbox code="actor.socialProfile.nick" path="nick" /> <br/>

	<acme:textbox code="actor.socialProfile.socialNetwork" path="socialNetwork" /> <br/>

	<acme:textbox code="actor.socialProfile.link" path="link" /> <br/>

	<acme:submit code="actor.save" name="save" />
	
	<jstl:if test="${socialProfile.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="actor.delete" />"
			onclick="return confirm('<spring:message code="actor.confirm.delete" />')" />&nbsp;
	</jstl:if>
	
	<acme:cancel code="actor.cancel" url="actor/show.do" />

</form:form>