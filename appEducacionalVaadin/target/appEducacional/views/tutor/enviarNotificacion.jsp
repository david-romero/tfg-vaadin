<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<form:form cssClass="col-lg-12 col-md-12 col-sm-12 col-xs-12" modelAttribute="notificacion" id="formulario" action="app/tutor/profesor/guardarNotificacion.do">
	<form:hidden path="padreMadreOTutor"/>
	<form:hidden path="fecha"/>
	<form:hidden path="emisor" />
	

	<div id="contenedorTablas" class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<div id="contenedorTablas1" class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
			<display:table requestURI="app/tutor/profesor/enviarNotificacion.do"  
			name="profesores"  pagesize="4" class="table table-hover table-responsive " id="profesor" 
			defaultsort="1" defaultorder="ascending"  >
					<!-- Attributes -->
			
				<spring:message code="alumno.nombre" var="nameHeader" />
				<display:column   property="nombre" title="${nameHeader}" sortable="true" headerClass="sortable" />
			
				<spring:message code="alumno.apellidos" var="apellidosHeader" />
				<display:column  property="apellidos" title="${apellidosHeader}" sortable="true" headerClass="sortable" />
			
				<display:column headerClass="sortable" sortable="true" title="Asignatura" property="asignaturas[0].nombre">
				</display:column>
				
				<display:column title="Seleccione">
					<form:radiobutton path="profesor" value="${profesor.id}" />
				</display:column>
				
				
					
			</display:table >
		</div>
		<div id="contenedorTablas2" style="margin-top: 2%;" class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
			<form:label path="contenido">Mensaje</form:label>
			<form:textarea cssClass="form-control col-lg-12 col-md-12 col-sm-12 col-xs-12" path="contenido"/><br>
			<form:errors cssClass="error" path="contenido" />
		</div>
	</div>
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="text-align: center;">
		<input class="btn btn-success" type="submit" id="enviar" value="Enviar"  />
	</div>
		<div id="errorTutores" class="error" style="display:none;">
			<span>No has seleccionado un Padre, Madre o Tutor al que enviar la notificación</span>
		</div>
</form:form>
<script type="text/javascript">
<!--

//-->
$("tr").click
	(
		function(i)
		{
			$(this).find("input").each
			(
					function()
					{
						id = $(this).val();
						name = ($(this).attr("name"));
						if (name == "seleccionado")
							window.location.href = "app/profesor/alumno/notificacion.do?alumnoId=" + id;
					}
			);
		}
	);
$(document).ready(function () {
	$("#formulario").submit
	(
		function (e)
		{	
			var num_tutores = ($("input:checked").length);
			if ( num_tutores == 0 ){
				document.getElementById('errorTutores').style.display = "block";
				e.preventDefault();
			}
		}
	);
	$('#contenedorTablas2 .pagebanner').css('display','none');
	$('#contenedorTablas2 .pagelinks').css('display','none');
});



 </script>