<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<display:table name="providers" id="row" requestURI="provider/list.do" pagesize="5">
		
		<display:column>
			<a href="item/listId.do?providerId=${row.id}"><spring:message code="provider.item.list"/></a><br/>
		</display:column>
		<display:column property="name" titleKey="provider.name"/>
		<display:column property="surnames" titleKey="provider.surnames"/>
		<display:column property="email" titleKey="provider.email"/>
		<display:column property="phone" titleKey="provider.phone"/>
		
	</display:table>
