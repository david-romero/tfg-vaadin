<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table requestURI="app/tutor/alumno/justificarFalta.do"  
	name="faltasAlumno" pagesize="8" class="table table-hover col-lg-12" id="row" 
	defaultsort="1" defaultorder="ascending" >
	
	<!-- Attributes -->

	<display:column class="col-lg-3" property="fecha" format="{0,date,dd/MM/yyyy}" title="Fecha del Evento" sortable="true" headerClass="sortable" >Ofgdfgdfgdfgdf
	</display:column>
	<spring:message code="alumno.apellidos" var="apellidosHeader" />
	<display:column class="col-lg-2" property="asignatura.nombre" title="Asignatura" sortable="true" headerClass="sortable" />
	
	<display:column class="col-lg-7" title="Motivo" sortable="false" >
		<form action="app/tutor/alumno/justificarFalta.do" method="POST">
		<input type="hidden" name="falta" value="${row.id}">
		<input type="hidden" name="alumnId" value="${alumnoId}">
		<div class="col-lg-11">
			<input type="text" name="motivo" class="form-control" />
		</div>
		<div class="col-lg-1">
			<button type="submit"  style="margin-top: -1%;" class="btn btn-danger" ><i class="fa fa-trash-o"></i></button>
		</div>
		</form>
	</display:column>
	

	
</display:table>