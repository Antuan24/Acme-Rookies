<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<display:table name="items" id="row" requestURI="item/list.do" pagesize="5">
		
		
			<display:column>
				<security:authorize access="hasRole('PROVIDER')">
				<a href="item/provider/show.do?itemId=${row.id}"><spring:message code="item.show"/></a><br/>
				<jstl:if test="${row.provider.id == principal.id }">
					<a href="item/provider/edit.do?itemId=${row.id}"><spring:message code="item.edit"/></a><br/>
					<a href="item/provider/delete.do?itemId=${row.id}"><spring:message code="item.delete"/></a><br/>
				</jstl:if>
				</security:authorize>
				<a href="actor/show.do?actorId=${row.provider.id}"><jstl:out value="${row.provider.userAccount.username}"/></a>
			</display:column>
		
		
		<display:column property="name" titleKey="item.name"/>
		<display:column property="description" titleKey="item.description"/>
		<display:column property="link" titleKey="item.link"/>
	</display:table>

	<div>
	<a href="item/provider/create.do"> <spring:message code="item.create" /> </a>
	</div>
