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

<jstl:choose>

	<jstl:when test="${curricula!=null}">
		<h1>
			<spring:message code="curricula.name" />
		</h1>
		
		<jstl:out value="${curricula.name}"/> <br/>
		
		<h1>
			<spring:message code="curricula.personalData" />
		</h1>

		<display:table pagesize="5" class="displaytag" name="personalData"
			requestURI="${requestURI}" id="row">

			<display:column property="fullName" titleKey="personalData.fullName"
				sortable="false" />

			<display:column property="statement" titleKey="personalData.statement"
				sortable="false" />

			<display:column property="phone" titleKey="personalData.phone"
				sortable="false" />
				
			<display:column property="githubUrl" titleKey="personalData.githubUrl"
				sortable="false" />
				
			<display:column property="linkedInUrl" titleKey="personalData.linkedInUrl"
				sortable="false" />

			<security:authorize access="hasRole('ROOKIE')">
				<display:column>
					<jstl:if test="${rookieIsOwner == true}">
						<a
							href="curricula/personalData/edit.do?personalDataId=${row.id}">
							<spring:message code="data.edit" />
						</a>
						<br />
					</jstl:if>

				</display:column>
			</security:authorize>
		</display:table>



		<!--  Listing grid -->

		<h1>
			<spring:message code="curricula.positionDatas" />
		</h1>

		<display:table pagesize="5" class="displaytag"
			name="positionDatas" requestURI="${requestURI}" id="row">

			<!-- Attributes -->

			<display:column property="title" titleKey="positionData.title"
				sortable="false" />

			<display:column property="description" titleKey="positionData.description"
				sortable="false" />
				
			<display:column property="startDate" titleKey="positionData.startDate"
				sortable="false" />
				
			<display:column property="endDate" titleKey="positionData.endDate"
				sortable="false" />

			<security:authorize access="hasRole('ROOKIE')">
				<jstl:if test="${rookieIsOwner == true}">
					<display:column>
						<a
							href="curricula/positionData/delete.do?positionDataId=${row.id}">
							<spring:message code="data.delete" />
						</a>
					</display:column>
					<display:column>
						<a
							href="curricula/positionData/edit.do?positionDataId=${row.id}">
							<spring:message code="data.edit" />
						</a>
					</display:column>
				</jstl:if>


			</security:authorize>
		</display:table>



		<!-- ---------------Listing grid -->

		<h1>
			<spring:message code="curricula.educationDatas" />
		</h1>

		<display:table pagesize="5" class="displaytag" name="educationDatas"
			requestURI="${requestURI}" id="row">

			<!-- Attributes -->

			<display:column property="degree" titleKey="educationData.degree"
				sortable="false" />

			<display:column property="institution" titleKey="educationData.institution"
				sortable="false" />

			<display:column property="mark" titleKey="educationData.mark"
				sortable="false" />
				
			<display:column property="startDate" titleKey="educationData.startDate"
				sortable="false" />	
				
			<display:column property="endDate" titleKey="educationData.endDate"
				sortable="false" />	

			<security:authorize access="hasRole('ROOKIE')">
				<jstl:if test="${rookieIsOwner == true}">
					<display:column>
						<a href="curricula/educationData/delete.do?educationDataId=${row.id}">
							<spring:message code="data.delete" />
						</a>
					</display:column>
					<display:column>
						<a href="curricula/educationData/edit.do?educationDataId=${row.id}"> <spring:message
								code="data.edit" />
						</a>
					</display:column>
				</jstl:if>

			</security:authorize>
		</display:table>



		<!-- ---------------Listing grid -->

		<h1>
			<spring:message code="curricula.miscellaneousDatas" />
		</h1>

		<display:table pagesize="5" class="displaytag" name="miscellaneousDatas"
			requestURI="${requestURI}" id="row">

			<!-- Attributes -->

			<display:column property="text" titleKey="miscellaneousData.text"
				sortable="false" />

			<display:column property="attachments" titleKey="miscellaneousData.attachments"
				sortable="false" />

			<security:authorize access="hasRole('ROOKIE')">
				<jstl:if test="${rookieIsOwner == true}">
					<display:column>
						<a href="curricula/miscellaneousData/delete.do?miscellaneousDataId=${row.id}">
							<spring:message code="data.delete" />
						</a>
					</display:column>
					<display:column>
						<a href="curricula/miscellaneousData/edit.do?miscellaneousDataId=${row.id}">
							<spring:message code="data.edit" />
						</a>
					</display:column>
				</jstl:if>

			</security:authorize>
		</display:table>
		
		<jstl:if test="${rookieIsOwner == true}">
		<spring:message code="curricula.positionData.new" var="positionData" />
		<input type="button" name="positionData" value="${positionData}" onclick="javascript:relativeRedir('curricula/positionData/create.do?curriculaId=${curricula.id}');" />

		<spring:message code="curricula.educationData.new" var="educationRecord" />
		<input type="button" name="educationRecord" value="${educationRecord}" onclick="javascript:relativeRedir('curricula/educationData/create.do?curriculaId=${curricula.id}');" />

		<spring:message code="curricula.miscellaneousData.new" var="miscellaneousData" /> 
		<input type="button" name="miscellaneousData" value="${miscellaneousData}" onclick="javascript:relativeRedir('curricula/miscellaneousData/create.do?curriculaId=${curricula.id}');" />
			
		</jstl:if>	
		</jstl:when>
	<jstl:otherwise>

		<jstl:if test="${rookieIsOwner == true and hasPersonalData == false}">
			<spring:message code="curricula.personalData.new"
				var="personalData" />
			<input type="button" name="personalData"
				value="${personalData}"
				onclick="javascript:relativeRedir('curricula/personalData/create.do?curriculaId=${curricula.id}');" />
		</jstl:if>

		<jstl:if test="${rookieIsOwner == false and hasPersonalData == false}">
		This rookie doesn't have a personal data yet, come back later.
	</jstl:if>
	</jstl:otherwise>


</jstl:choose>

<input type="button" name="back"
	value="<spring:message code="curricula.show.back" />"
	onclick="javascript: window.location.replace('')" />
<br />
