<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('AUDITOR')">

	<display:table name="audits" id="row" requestURI="audit/auditor/list.do" pagesize="5">
		
		<display:column>
			<a href="audit/show.do?auditId=${row.id}"><spring:message code="audit.show"/></a><br/>
			<jstl:if test="${row.isFinal == true}">
				<a href="quolet/auditor/create.do?auditId=${row.id}"> <spring:message code="audit.quolet.create" /></a><br/>
			</jstl:if>
			<jstl:if test="${row.isFinal == false}">
				<a href="audit/auditor/edit.do?auditId=${row.id}"><spring:message code="audit.edit"/></a><br/>
				<a href="audit/auditor/delete.do?auditId=${row.id}"><spring:message code="audit.delete"/></a><br/>
			</jstl:if>
		</display:column>
		<spring:message code="audit.moment.format" var="formatMoment"/>
		<display:column titleKey="audit.moment" property="moment" format="{0,date,${formatMoment} }"/>
		<display:column property="score" titleKey="audit.score"/>
		<display:column titleKey="audit.position">
			<a href="position/show.do?positionId=${row.position.id}"><jstl:out value="${row.position.title}"/></a>
		</display:column>
		
	</display:table>

	<div>
	<a href="audit/auditor/create.do"> <spring:message code="audit.create" /> </a>
	</div>

</security:authorize>
