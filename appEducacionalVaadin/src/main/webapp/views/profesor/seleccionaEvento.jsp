<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table requestURI="app/profesor/evento/evaluar.do"  
	name="eventos" pagesize="4" class="table table-hover" id="row" 
	defaultsort="1" defaultorder="ascending" >
	
	<!-- Attributes -->

	<display:column class="col-lg-3" property="fecha" format="{0,date,dd/MM/yyyy}" title="Fecha del Evento" sortable="true" headerClass="sortable" >Ofgdfgdfgdfgdf
	</display:column>
	<spring:message code="alumno.apellidos" var="apellidosHeader" />
	<display:column class="col-lg-3" property="asignatura.nombre" title="Asignatura" sortable="true" headerClass="sortable" />

	<spring:message code="alumno.apellidos" var="apellidosHeader" />
	<display:column class="col-lg-3" property="itemEvaluable.class.simpleName" title="Tipo de Evento" sortable="true" headerClass="sortable" />
	
	<display:column class="col-lg-3" title=" " sortable="false" >
		<a href="${url}=${row.id}"><i class="fa fa-arrow-right"></i></a>
	</display:column>
	
</display:table>