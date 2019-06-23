<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('COMPANY')">

	<display:table name="problems" id="row" requestURI="problem/company/list.do" pagesize="5">
		
		<display:column>
			<a href="problem/company/show.do?problemId=${row.id}"><spring:message code="problem.show"/></a><br/>
			<jstl:if test="${row.isFinal == false}">
				<a href="problem/company/edit.do?problemId=${row.id}"><spring:message code="problem.edit"/></a><br/>
				<a href="problem/company/delete.do?problemId=${row.id}"><spring:message code="problem.delete"/></a><br/>
			</jstl:if>
		</display:column>
		<display:column property="title" titleKey="problem.title"/>
		<display:column property="statement" titleKey="problem.statement"/>
		<display:column property="hint" titleKey="problem.hint"/>
		<display:column property="attachments" titleKey="problem.attachments"/>
		
	</display:table>

	<div>
	<a href="problem/company/create.do"> <spring:message code="problem.create" /> </a>
	</div>

</security:authorize>
