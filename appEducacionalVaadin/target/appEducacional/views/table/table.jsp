<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->

<display:table name="alumnos" pagesize="5" class="displaytag"
	requestURI="app/profesor/alumno/buscarAlumnosPorCurso.do" id="row">
	
	<!-- Attributes -->

	<spring:message code="alumno.nombre" var="nameHeader" />
	<display:column property="nombre" title="${nameHeader}" sortable="true" />

	<spring:message code="alumno.apellidos" var="apellidosHeader" />
	<display:column property="apellidos" title="${apellidosHeader}" sortable="true" />
	<form:form>
	<spring:message code="alumno.noAsiste" var="noAsisteHeader" />
	<display:column title="${noAsisteHeader}" sortable="false" >
		<input type="radio" name="${row.id}" value="Noasiste">
	</display:column>

	<spring:message code="alumno.asiste" var="asisteHeader" />
	<display:column title="${asisteHeader}" sortable="false" >
		<input type="radio" name="${row.id}" value="asiste">
	</display:column>
	
	<spring:message code="alumno.retraso" var="retrasoHeader" />
	<display:column title="${retrasoHeader}" sortable="false" >
		<input type="radio" name="${row.id}"  value="retraso">
	</display:column>
	</form:form>
</display:table>