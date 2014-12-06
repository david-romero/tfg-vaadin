<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<form role="form" method="POST" style="margin-top:20px;" class="col-lg-12 col-md-12 col-sm-12 col-xs-12 form-horizontal" action="app/tutor/alumno/guardarVinculacion.do">


	<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<label class="col-sm-3 control-label">Nombre:</label>
			<div class="col-sm-9" style="margin-top: 8px;">
				<span>${alumno.nombre}</span>
				
	    	</div>
	</div>
	<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
	    	
			<label   class="col-sm-3 control-label">Apellidos:</label>
			<div class="col-sm-9" style="margin-top: 8px;">
				<span>${alumno.apellidos}</span>

			</div>
	</div>
	<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12" >
		<label for="curso" class="col-sm-3 control-label">Curso:</label>
		<div class="col-sm-9" style="margin-top: 8px;">
			<span>${alumno.curso}</span>
		</div>
	</div>


	
	<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
				
				<label  class="col-sm-3 control-label">Fecha de Nacimiento:</label>
				<div class="col-sm-9" style="margin-top: 8px;">
					<span>
						<fmt:formatDate value="${alumno.fechaNacimiento}" pattern="dd/MM/yyyy" var="fecha" />
						<jstl:out value="${fecha}"></jstl:out>
					</span>
				</div>
	</div>


	<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12" >
		<label for="curso" class="col-sm-3 control-label">Asignaturas Pendientes:</label>
		<div class="col-sm-9" style="margin-top: 8px;">
			<span>${alumno.pendiente}</span>
		</div>
	</div>	
	<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12" >
		<label for="curso" class="col-sm-3 control-label">Cursos Repetidos:</label>
		<div class="col-sm-9" style="margin-top: 8px;">
			<span>${alumno.repiteCurso}</span>
		</div>
	</div>
	<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12" style="text-align: center;">
				  <input type="hidden" name="alumno" value="${alumno.id }" />
				  <button class="btn btn-success" type="submit" id="save" name="save"  >
				  	<i class="fa fa-check"></i>&nbsp;Vincular
				  </button>
	</div>


</form>