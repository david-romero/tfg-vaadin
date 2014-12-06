<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

	<!-- Cambio el title de la pagina -->
	<jstl:if test="${tipo == 'profesor'}">
		<script>
			jQuery('h1').html("Seleccione un profesor");
		</script>
	</jstl:if>

<display:table  requestURI="${requestURI}"  
	name="alumnos" pagesize="8" class="table table-hover col-lg-12 col-md-12 col-sm-12 col-xs-12" id="row" 
	defaultsort="1" defaultorder="ascending">
	
	
	
	<!-- Attributes -->

	<spring:message code="alumno.nombre" var="nameHeader" />
	<display:column property="nombre" title="${nameHeader}" sortable="true" headerClass="sortable" />

	<spring:message code="alumno.apellidos" var="apellidosHeader" />
	<display:column property="apellidos" title="${apellidosHeader}" sortable="true" headerClass="sortable" />

	<jstl:if test="${row.getClass().simpleName == 'Alumno'}">
		<spring:message code="alumno.foto" var="fotoHeader" />
		<display:column title="${fotoHeader}" sortable="false" >
			<img class="thumbnail" src="rest/profesor/alumno/foto.do?alumnoId=${row.id}" 
			style="margin: 0px 0px 0px 0px;height: 60px;" alt="Firma">
		</display:column>
	</jstl:if>
	
	<jstl:if test="${row.getClass().simpleName == 'Profesor'}">
		<display:column headerClass="sortable" sortable="true" title="Asignatura" property="asignaturas[0].nombre">
		</display:column>
	</jstl:if>
	
	<display:column title=" " sortable="false" >
		<a href="/appEducacional/app/tutor/${tipo}/${accion}.do?${tipo}Id=${row.id}"><i class="fa fa-arrow-right"></i></a>
	</display:column>
	
</display:table>