<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table requestURI="${requestURI}"  
	name="alumnos" pagesize="4" class="table table-hover" id="row" 
	defaultsort="1" defaultorder="ascending" >
	
	<!-- Attributes -->

	<spring:message code="alumno.nombre" var="nameHeader" />
	<display:column property="nombre" title="${nameHeader}" sortable="true" headerClass="sortable" >Ofgdfgdfgdfgdf
	</display:column>
	<spring:message code="alumno.apellidos" var="apellidosHeader" />
	<display:column property="apellidos" title="${apellidosHeader}" sortable="true" headerClass="sortable" />

	<spring:message code="alumno.foto" var="fotoHeader" />
	<display:column title="${fotoHeader}" sortable="false" >
		<img class="img-responsive img-thumbnail" src="rest/profesor/alumno/foto.do?alumnoId=${row.id}" 
		style="max-width: 75px;" alt="Firma">
	</display:column>
	
	<display:column title=" " sortable="false" >
		<a href="${url}=${row.id}"><i class="fa fa-arrow-right"></i></a>
	</display:column>
	
</display:table>

<script>
$("#footer").css("margin-top", function() { return $(this).outerHeight() });
</script>