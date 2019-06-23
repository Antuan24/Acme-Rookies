<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('PROVIDER')">

	<display:table name="sponsorships" id="row" pagesize="5">

		<display:column titleKey="sponsorship.options">			
				<a href="sponsorship/provider/show.do?sponsorshipId=${row.id}">
					<spring:message	code="sponsorship.show" />
				</a><br/>
			<jstl:if test="${row.provider == isProvider}">
				<a href="sponsorship/provider/edit.do?sponsorshipId=${row.id}">
					<spring:message	code="sponsorship.edit" />
				</a><br/>
			</jstl:if>		
			<jstl:if test="${row.provider == isProvider}">
				<a href="sponsorship/provider/delete.do?sponsorshipId=${row.id}">
					<spring:message	code="sponsorship.delete" />
				</a><br/>		
			</jstl:if>		
		</display:column>

		<display:column property="banner" titleKey="sponsorship.banner" />		
		<display:column property="target" titleKey="sponsorship.target" />
		<display:column property="provider.make" titleKey="sponsorship.provider.make" />					
		<display:column property="position.title" titleKey="sponsorship.position" />

	</display:table>

	<a href="sponsorship/provider/create.do"><spring:message code="sponsorship.create"/></a>

	</security:authorize>
