<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<a href="">
<div class="page-header" style="background: url(${banner}) center no-repeat; background-size: cover">
</div>
</a>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.system" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="admin/configuration.do"><spring:message code="master.page.configuration" /></a></li>
					<li><a href="admin/dashboard.do"><spring:message code="master.page.dashboard" /></a></li>
					<li><a href="admin/listActors.do"><spring:message code="master.page.listActors" /></a></li>
					<li><a href="message/createBroadcast.do"><spring:message code="master.page.broadcast" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv" href="admin/listActors.do"><spring:message	code="master.page.system.compute" /></a></li>
			<li><a class="fNiv" href=position/list.do><spring:message code="master.page.position.list" /></a></li>
			
			<li><a class="fNiv"><spring:message	code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href=actor/registerAdmin.do><spring:message	code="master.page.registerAdmin" /></a></li>
					<li><a href=actor/registerAuditor.do><spring:message	code="master.page.registerAuditor" /></a></li>
				</ul>
			</li>
			
			
		</security:authorize>
		
		
		<security:authorize access="hasRole('COMPANY')">
			<li><a class="fNiv"><spring:message	code="master.page.position.list" /></a>
				<ul>
					<li class="arrow"></li>
	   	    		<li><a href=position/company/list.do><spring:message code="master.page.position.company.list" /></a></li>
	   	    		<li><a href=position/list.do><spring:message code="master.page.position.list" /></a></li> 
	   	    	</ul>
			</li>
			<li><a class="fNiv" href=application/company/list.do><spring:message code="master.page.app.list" /></a></li>
			<li><a class="fNiv" href=problem/company/list.do><spring:message code="master.page.problem.list" /></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('ROOKIE')">
			<li><a class="fNiv" href="finder/rookie/filter.do"><spring:message code="master.page.finder" /></a></li>
			<li><a class="fNiv" href="application/rookie/list.do"><spring:message code="master.page.app.list" /></a></li>
			<li><a class="fNiv" href=position/list.do><spring:message code="master.page.position.list" /></a></li>
			<li><a class="fNiv" href=curricula/rookie/list.do><spring:message code="master.page.curricula.list" /></a></li>
		</security:authorize>

		<security:authorize access="hasRole('AUDITOR')">
			<li><a class="fNiv"><spring:message	code="master.page.audit" /></a>
				<ul>
					<li class="arrow"></li>
	   	    		<li><a href=audit/auditor/list.do><spring:message code="master.page.audit.list" /></a></li>
	   	    		<li><a href=audit/auditor/create.do><spring:message code="master.page.audit.create" /></a></li> 
	   	    	</ul>
			</li>
			<li><a class="fNiv" href="quolet/auditor/list.do"><spring:message	code="master.page.quolet.list" /></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('PROVIDER')">
			<li><a class="fNiv" href="item/provider/list.do"><spring:message	code="master.page.item.myList" /></a></li>
			<li><a class="fNiv" href="sponsorship/provider/list.do"><spring:message code="master.page.sponsorship.list" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/registerRookie.do"><spring:message code="master.page.registerRookie" /></a></li>
					<li><a href="actor/registerCompany.do"><spring:message code="master.page.registerCompany" /></a></li>
					<li><a href="actor/registerProvider.do"><spring:message code="master.page.registerProvider" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv" href=position/list.do><spring:message code="master.page.position.list" /></a></li>
			<li><a class="fNiv" href="item/list.do"><spring:message	code="master.page.item.list" /></a></li>
			<li><a class="fNiv" href=company/list.do><spring:message code="master.page.company.list" /></a></li>
			<li><a class="fNiv" href="provider/list.do"><spring:message	code="master.page.provider.list" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<%-- <li><a class="fNiv" href=position/list.do><spring:message code="master.page.position.list" /></a></li> --%>
			<li><a class="fNiv" href="item/list.do"><spring:message	code="master.page.item.list" /></a></li>
			<li><a class="fNiv" href=company/list.do><spring:message code="master.page.company.list" /></a></li>
			<li><a class="fNiv" href="provider/list.do"><spring:message	code="master.page.provider.list" /></a></li>
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>		
					<li><a href="message/list.do"><spring:message code="master.page.mailbox" /></a></li>
					<li><a href="actor/show.do"><spring:message code="master.page.profile" /></a></li>			
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

