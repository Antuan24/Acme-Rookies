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

<security:authorize access="hasRole('ADMIN')">

		<spring:message code="admin.datatable"/>
	<table style="width:'100%' border='0' align='center' ">
			<tr>
				<th><spring:message code="admin.type"/></th>
				<th><spring:message code="admin.positionsPerCompany"/></th>
				<th><spring:message code="admin.applicationsPerRookie"/></th>
				<th><spring:message code="admin.salaryPerPosition"/></th>
				<th><spring:message code="admin.curriculasPerRookie"/></th>
				<th><spring:message code="admin.resultsPerFinder"/></th>
				<th><spring:message code="admin.auditScorePerPosition"/></th>
				<th><spring:message code="admin.auditScorePerCompany"/></th>
				<th><spring:message code="admin.itemsPerProvider"/></th>
				<th><spring:message code="admin.sponsorshipsPerProvider"/></th>
				<th><spring:message code="admin.sponsorshipsPerPosition"/></th>
			</tr>
			<tr>
				<td><spring:message code="admin.average"/></td>
				<td><jstl:out value="${avgPositionsPerCompany}"/></td>
				<td><jstl:out value="${avgApplicationsPerRookie}"/></td>
				<td><jstl:out value="${avgSalaryPerPosition}"/></td>	
				<td><jstl:out value="${avgCurriculasPerRookie}"/></td>	
				<td><jstl:out value="${avgResultsPerFinder}"/></td>	
				<td><jstl:out value="${avgAuditScorePerPosition}"/></td>		
				<td><jstl:out value="${avgAuditScorePerCompany}"/></td>
				<td><jstl:out value="${avgItemsPerProvider}"/></td>	
				<td><jstl:out value="${avgSponsorshipsPerProvider}"/></td>	
				<td><jstl:out value="${avgSponsorshipsPerPosition}"/></td>			
			</tr>
			<tr>
				<td><spring:message code="admin.minimum"/></td>
				<td><jstl:out value="${minPositionsPerCompany}"/></td>
				<td><jstl:out value="${minApplicationsPerRookie}"/></td>
				<td><jstl:out value="${minSalaryPerPosition}"/></td>
				<td><jstl:out value="${minCurriculasPerRookie}"/></td>	
				<td><jstl:out value="${minResultsPerFinder}"/></td>
				<td><jstl:out value="${minAuditScorePerPosition}"/></td>
				<td><jstl:out value="${minAuditScorePerCompany}"/></td>
				<td><jstl:out value="${minItemsPerProvider}"/></td>	
				<td><jstl:out value="${minSponsorshipsPerProvider}"/></td>	
				<td><jstl:out value="${minSponsorshipsPerPosition}"/></td>	
			</tr>	
			<tr>
				<td><spring:message code="admin.maximum"/></td>
				<td><jstl:out value="${maxPositionsPerCompany}"/></td>
				<td><jstl:out value="${maxApplicationsPerRookie}"/></td>
				<td><jstl:out value="${maxSalaryPerPosition}"/></td>	
				<td><jstl:out value="${maxCurriculasPerRookie}"/></td>
				<td><jstl:out value="${maxResultsPerFinder}"/></td>
				<td><jstl:out value="${maxAuditScorePerPosition}"/></td>
				<td><jstl:out value="${maxAuditScorePerCompany}"/></td>
				<td><jstl:out value="${maxItemsPerProvider}"/></td>	
				<td><jstl:out value="${maxSponsorshipsPerProvider}"/></td>	
				<td><jstl:out value="${maxSponsorshipsPerPosition}"/></td>	
			</tr>
			<tr>
				<td><spring:message code="admin.stdv"/></td>
				<td><jstl:out value="${stdevPositionsPerCompany}"/></td>
				<td><jstl:out value="${stdevApplicationsPerRookie}"/></td>
				<td><jstl:out value="${stdevSalaryPerPosition}"/></td>
				<td><jstl:out value="${stdevCurriculasPerRookie}"/></td>
				<td><jstl:out value="${stdevResultsPerFinder}"/></td>
				<td><jstl:out value="${stdevAuditScorePerPosition}"/></td>
				<td><jstl:out value="${stdevAuditScorePerCompany}"/></td>
				<td><jstl:out value="${stdevItemsPerProvider}"/></td>	
				<td><jstl:out value="${stdevSponsorshipsPerProvider}"/></td>	
				<td><jstl:out value="${stdevSponsorshipsPerPosition}"/></td>	
			</tr>
	</table>
	
		<table style="width:'100%' border='0' align='center' ">
		<tr>
			<th><spring:message code="admin.type"/></th>
			<th><spring:message code="admin.ratio"/></th>
		</tr>
		<tr>
			<th><spring:message code="admin.rEmptyFinders"/></th>
			<th><jstl:out value="${ratioEmptyFinders}"/></th>
		</tr>
	</table>

	<b><spring:message code="admin.maxPositionsCompanies"/></b>
	<jstl:if test="${empty maxPositionsCompanies}"><spring:message code="admin.empty"/></jstl:if>
	<table style="width:'100%' border='0' align='center' ">
		<jstl:forEach var="i" items="${maxPositionsCompanies}">
		<tr>
			<td><jstl:out value="${i.commercialName}"/> (<a href="actor/show.do?actorId=${i.id}"><jstl:out value="${i.userAccount.username}"/></a>)  </td>
		</tr>			
		</jstl:forEach>
	</table>
	
	<b><spring:message code="admin.maxApplicationsRookies"/></b>
	<jstl:if test="${empty maxApplicationsRookies}"><spring:message code="admin.empty"/></jstl:if>
	<table style="width:'100%' border='0' align='center' ">
		<jstl:forEach var="i" items="${maxApplicationsRookies}">
		<tr>
			<td><jstl:out value="${i.name}"/> <jstl:out value="${i.surnames}"/> (<a href="actor/show.do?actorId=${i.id}"><jstl:out value="${i.userAccount.username}"/></a>)</td>
		</tr>			
		</jstl:forEach>
	</table>
	
	<b><spring:message code="admin.bestSalaryPosition"/></b>
		<jstl:if test="${bestSalaryPosition==null}"><spring:message code="admin.empty"/></jstl:if>
	
	<table style="width:'100%' border='0' align='center' ">
		<tr>
			<td><jstl:out value="${bestSalaryPosition.title}"/></td>
		</tr>			
	</table>
	
	<b><spring:message code="admin.worstSalaryPosition"/></b>
	<jstl:if test="${worstSalaryPosition==null}"><spring:message code="admin.empty"/></jstl:if>
	<table style="width:'100%' border='0' align='center' ">
		<tr>
			<td><jstl:out value="${worstSalaryPosition.title}"/></td>
		</tr>			
	</table>
	
	<b><spring:message code="admin.maxAuditScoreCompanies"/></b>
	<jstl:if test="${empty maxAuditScoreCompanies}"><spring:message code="admin.empty"/></jstl:if>
	<table style="width:'100%' border='0' align='center' ">
		<jstl:forEach var="i" items="${maxAuditScoreCompanies}">
		<tr>
			<td><jstl:out value="${i.name}"/> <jstl:out value="${i.surnames}"/> (<a href="actor/show.do?actorId=${i.id}"><jstl:out value="${i.userAccount.username}"/></a>)</td>
		</tr>			
		</jstl:forEach>
	</table>
	
	<b><spring:message code="admin.providersWMoreSponsorshipsThanAvg"/></b>
	<jstl:if test="${empty providersWMoreSponsorshipsThanAvg}"><spring:message code="admin.empty"/></jstl:if>	
	<table style="width:'100%' border='0' align='center' ">
		<jstl:forEach var="i" items="${providersWMoreSponsorshipsThanAvg}">
		<tr>
			<td><jstl:out value="${i.name}"/> <jstl:out value="${i.surnames}"/> (<a href="actor/show.do?actorId=${i.id}"><jstl:out value="${i.userAccount.username}"/></a>)</td>
		</tr>			
		</jstl:forEach>		
	</table>
	
	<b><spring:message code="admin.topProvidersItems"/></b>
	<jstl:if test="${empty topProvidersItems}"><spring:message code="admin.empty"/></jstl:if>	
	<table style="width:'100%' border='0' align='center' ">
		<jstl:forEach var="i" items="${topProvidersItems}">
		<tr>
			<td><jstl:out value="${i.name}"/> <jstl:out value="${i.surnames}"/> (<a href="actor/show.do?actorId=${i.id}"><jstl:out value="${i.userAccount.username}"/></a>)</td>
		</tr>			
		</jstl:forEach>		
	</table>
	
	<b><spring:message code="admin.avgSalaryPositionsHighestScore"/></b>
	<table style="width:'100%' border='0' align='center' ">
		<tr>
			<td><jstl:out value="${avgSalaryPositionsHighestScore}"/></td>
		</tr>			
	</table>
	
</security:authorize>

