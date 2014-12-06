<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

 <!-- Date/time picker -->
<script type="text/javascript" src="scripts/jquery.datetimepicker.js"></script>
<link rel="stylesheet" href="styles/jquery.datetimepicker.css">




	<form:form cssClass="col-lg-12 col-md-12 col-sm-12 col-xs-12" cssStyle="" id="formulario" action="app/tutor/profesor/guardarCita.do" modelAttribute="cita"  >
		<input type="hidden" name="alumnoId" value="${alumnoId}" />
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="profesor"/>
		<form:hidden path="padreMadreOTutor"/>
		<form:hidden path="confirmadoProfesor"/>
		<form:hidden path="confirmadoTutor"/>
		<form:hidden path="emisor"/>
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<form:label cssClass="form-label" path="fecha">Fecha:</form:label>
				<form:input path="fecha" id="datetimepickervalue" cssStyle="display:none;"  />
				<div class="input-group">
					<input class="form-control"  type="text" id="datetimepicker" readonly="readonly" />
					<span class="input-group-addon" onclick="cargarTimePicker();" style="cursor:pointer;">	
						<i  class="fa fa-calendar" ></i>
					</span>
				</div>
						<form:errors cssClass="error" path="fecha" />
			</div>
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<form:label cssClass="form-label" path="contenido">Asunto:</form:label>
				<form:input class="form-control" path="contenido" />
				<form:errors cssClass="error" path="contenido" />
			</div>
			 <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<label>Preferencias para la cita</label><br>
				<p class="help-block"><jstl:out  value="${preferenciasCita}"></jstl:out></p>
			</div>
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="text-align: center;">
			<input class="btn btn-success" type="submit" value="Enviar" name="save" />
		</div >
		
	</form:form>
	<script>
	var f = new Date();
			jQuery('#datetimepicker').datetimepicker(
					{
						lang:'es',
						
						 i18n:{
						  es:{
						   months:[
						    'Enero','Febrero','Marzo','Abril',
						    'Mayo','Junio','Julio','Agosto',
						    'Septiembre','Octubre','Noviembre','Diciembre',
						   ],
						   dayOfWeek:[
						    "Do.", "Lu", "Ma", "Mi", 
						    "Ju", "Vi", "Sa.",
						   ]
						  }
						 },
						 allowTimes:[
						             '08:00','08:30','09:00', '09:30', '10:00', '10:30', 
						             '11:00', '11:30','12:00','12:30',
						             '13:00','13:30', '14:00','14:30', '15:00', '15:30',
						             '16:00','16:30','17:00', '17:30',
						             '18:00', '18:30', '19:00', '19:30', '20:00'
						            ],
						minDate:f.getFullYear() + "/" + (f.getMonth() +1) + "/" + f.getDate(),
						maxDate:f.getFullYear() + "/" + (f.getMonth() +6) + "/" + f.getDate(),//tommorow is maximum date calendar
						onGenerate:function( ct ){
						    jQuery(this).find('.xdsoft_date.xdsoft_weekend')
						      .addClass('xdsoft_disabled');
						  },
						  dayOfWeekStart: 1,
						  onChangeDateTime:function(dp,$input)
						  {
							  jQuery('#datetimepickervalue').val($input.val());
						  },
						  format: 'Y-m-d H:i',
					});
			function cargarTimePicker()
			{
				$('#datetimepicker').datetimepicker('show');
			}
		</script>