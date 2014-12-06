<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
	<div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
		<label style="margin-top: 5%;" class="form-label">
		<spring:message var="label" code='pasarLista.curso'/>
		<jstl:out value="${label}" />
		</label>
	</div>
	<div class="col-lg-10 col-md-10 col-sm-8 col-xs-8">
		<form:select cssClass="form-control" path="cursos" id="cursos" onchange="javascript: reloadAlumnos()">
			<form:option value="NONE" label="--- Seleccionar Curso ---" />
			<form:options items="${cursos}" itemValue="id"  />
		</form:select>
	</div>
</div>


<display:table requestURI="app/profesor/alumno/buscarAlumnosPorCurso.do?cursoId=${cursoId}" name="alumnos" pagesize="4" class="table table-responsive table-hover col-lg-12 col-md-12 col-sm-12 col-xs-12" id="row"  defaultsort="1" defaultorder="descending">
	
	<!-- Attributes -->

	<spring:message code="alumno.nombre" var="nameHeader" />
	<display:column property="nombre" title="${nameHeader}" sortable="true" headerClass="sortable" />

	<spring:message code="alumno.apellidos" var="apellidosHeader" />
	<display:column property="apellidos" title="${apellidosHeader}" sortable="true" headerClass="sortable" />
	
	<spring:message code="alumno.noAsiste" var="noAsisteHeader" />
	<display:column title="${noAsisteHeader}" sortable="false" >
		<a href="app/profesor/alumno/noAsiste.do?cursoId=${cursoId}&alumnoId=${row.id}"><i class="fa fa-eye-slash"></i></a>
	</display:column>

	<spring:message code="alumno.retraso" var="retrasoHeader" />
	<display:column title="${retrasoHeader}" sortable="false" >
		<a href="app/profesor/alumno/retraso.do?cursoId=${cursoId}&alumnoId=${row.id}"><i class="fa fa-clock-o"></i></a>
	</display:column>

	<spring:message code="alumno.foto" var="fotoHeader" />
	<display:column title="${fotoHeader}" sortable="false" >
		<img style="max-width: 65px;margin-bottom: 5px;" class="img img-responsive thumbnail" src="rest/profesor/alumno/foto.do?alumnoId=${row.id}" alt="Firma">
	</display:column>
	<!--  
	<display:setProperty name="export.pdf" value="true" />
	<display:setProperty name="export.pdf.filename" value="lista.pdf"/>
	<display:setProperty name="export.excel.filename" value="lista.xls"/>
	-->
</display:table>

<script type="text/javascript">
		function reloadAlumnos() {
			var cursoId = $('select#cursos').val();
			window.location.replace("app/profesor/alumno/buscarAlumnosPorCurso.do?cursoId=" + cursoId);
		}
</script>
