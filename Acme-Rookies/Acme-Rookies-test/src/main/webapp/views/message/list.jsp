<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<!-- 
	Recieves: List<Messages> messages, la lista de mensajes de un actor dado
			  MessageFinderForm messageFinderForm, formulario para buscar mensajes por tag
-->

<security:authorize access="isAuthenticated()">

	<form:form action="message/list.do" modelAttribute="messageFinderForm">
	
		<acme:textbox code="m.tag" path="keyword"/>
	
		<acme:submit name="list" code="m.finder.search" />
		<acme:submit name="cancel" code="m.finder.cancel"/>
	</form:form>
	<br/>
	
	<a href="message/create.do"><spring:message code='m.create' /></a><br>
	<jstl:if test="${isAdmin}">
	<a href="message/createBroadcast.do"><spring:message code='m.createBroadcast' /></a><br>
	</jstl:if>
	<jstl:if test="${isAdmin and notificationIsSent == false}">
	<a href="admin/notifyUpdate.do"> Send the users the nofication about the update</a><br>
	</jstl:if>
	

	<display:table name="messages" id="row" requestURI="message/list.do" pagesize="5">

		<display:column titleKey="m.messages">
			<table border='1' style="width:100%">
				<tr>
					<td>
						<spring:message code='m.sender' /><jstl:out value=" ${row.sender.userAccount.username}" /> <br>
						<spring:message code='m.subject' /><jstl:out value=" ${row.subject}" /><br>
						<spring:message code='m.moment'/><jstl:out value=" ${row.moment}"/>
					</td>
				</tr>
				
				<tr>
					<td>
						<jstl:out value="${row.body}" />
					</td>
				</tr>
				
				<tr>
					<td>
						<spring:message code='m.tags'/>: 
						<jstl:forEach items="${row.tags}" var="x">
							<jstl:out value="${x} , " />
						</jstl:forEach>
					</td>
				</tr>
			</table>
			<a href="message/delete.do?messageId=${row.id}"><spring:message code='m.delete' /></a>
		</display:column>

	</display:table><br>
	<a href="message/create.do"><spring:message code='m.create' /></a><br/>
	
	<input type="button" name="back"
		value="<spring:message code="m.back" />"
		onclick="javascript: window.location.replace('')" />
	<br />
</security:authorize>
