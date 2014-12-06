<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


	<script>
		function actualizarTablaAlumnos()
		{
			window.location.href = "app/profesor/alumno/enviarNotificacionFiltrado.do?cursoId=" + $("#select-cursos").val();
		}
	</script>

<form:form cssClass="col-lg-12 col-md-12 col-sm-12 col-xs-12" modelAttribute="notificacion" id="formulario" action="app/profesor/alumno/enviarNotificacion.do" cssStyle="margin-bottom: -15px;">
	<form:hidden path="profesor"/>
	
	<form:select cssClass="form-control col-lg-12 col-md-12 col-sm-12 col-xs-12" id="select-cursos" path="" items="${cursos}" itemValue="id" onchange="actualizarTablaAlumnos();">
	</form:select>
	<div id="contenedorTablas" class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<div id="contenedorTablas1" class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
			<display:table requestURI="app/profesor/alumno/enviarNotificacion.do"  
			name="alumnos" pagesize="4" class="table table-hover table-responsive " id="alumnoAsunto" 
			defaultsort="1" defaultorder="ascending"  >
					<!-- Attributes -->
			
				<spring:message code="alumno.nombre" var="nameHeader" />
				<display:column style="cursor:pointer;"  property="nombre" title="${nameHeader}" sortable="true" headerClass="sortable" />
			
				<spring:message code="alumno.apellidos" var="apellidosHeader" />
				<display:column style="cursor:pointer;" property="apellidos" title="${apellidosHeader}" sortable="true" headerClass="sortable" />
			
				<spring:message code="alumno.foto" var="fotoHeader" />
				<display:column style="cursor:pointer;" title="${fotoHeader}" sortable="false" >
					<img src="rest/profesor/alumno/foto.do?alumnoId=${alumnoAsunto.id}" 
					style="margin: 0px 0px 0px 0px;height: 63px;width: 48px;" alt="Firma">
					<input type="hidden" id="seleccionado" name="seleccionado" value="${alumnoAsunto.id}"  />
				</display:column>
				
					
			</display:table >
		</div>
		<div id="contenedorTablas2" class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
			<display:table requestURI="app/profesor/alumno/notificacion.do"  
			name="tutores" pagesize="3" class="table table-hover table-responsive col-lg-6 col-md-6 col-sm-12 col-xs-12" id="tutorAEnviar"  >
			<display:setProperty name="paging.banner.onepage" value=""></display:setProperty> 
			<display:setProperty name="paging.banner.one_item_found" value=""></display:setProperty>
			<display:setProperty name="paging.banner.all_items_found" value=""></display:setProperty>
			<display:setProperty name="paging.banner.some_items_found" value=""></display:setProperty>
			<display:setProperty name="basic.msg.empty_list" value=""></display:setProperty>
				<spring:message code="alumno.nombre" var="nameHeader" />
				<display:column  property="nombre" title="${nameHeader}" sortable="true" headerClass="sortable" />
				<spring:message code="alumno.apellidos" var="apellidosHeader" />
				<display:column property="apellidos" title="${apellidosHeader}" sortable="true" headerClass="sortable" />
				<display:column>
					<input type="checkbox" name="destinatario" value="${tutorAEnviar.id}" />
				</display:column>
			</display:table>
			<select class="form-control col-lg-12 col-md-12 col-sm-12 col-xs-12" name="tipoNotificacion" id="tipoNotificacion">
				<option>Falta de Asistencia</option>
				<option>Mal comportamiento</option>
				<option>Falta grave</option>
				<option>Nota Positiva</option>
				<option>Nota de examen</option>
				<option>Otros</option>
			</select>
			<form:textarea cssClass="form-control col-lg-12 col-md-12 col-sm-12 col-xs-12" path="contenido"/><br>
			<form:errors cssClass="error" path="contenido" />
		</div>
		<div id="errorTutores" class="error col-lg-12 col-md-12 col-sm-12 col-xs-12" style="text-align: center;display:none;margin-top: -20px;">
			<span>No has seleccionado un Padre, Madre o Tutor al que enviar la notificación</span>
		</div>
	</div>
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="text-align: center;">
		<input class="btn btn-success" type="submit" id="enviar" value="Enviar"  />
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