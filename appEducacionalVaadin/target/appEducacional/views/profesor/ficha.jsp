<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<script type="text/javascript">
	function tab(pestana,panel,numeroPanel)
	{
		pst 	= document.getElementById(pestana);
		pnl 	= document.getElementById(panel);
		psts	= document.getElementById('tabs'+numeroPanel).getElementsByTagName('li');
		pnls	= document.getElementById('paneles'+numeroPanel).getElementsByTagName('div');
		
		// eliminamos las clases de las pestañas
		for(i=0; i< psts.length; i++)
		{
			psts[i].className = '';
		}
		
		// Añadimos la clase "actual" a la pestaña activa
		pst.className = 'actual';
		
		// eliminamos las clases de las pestañas
		for(i=0; i< pnls.length; i++)
		{
			pnls[i].style.display = 'none';
		}
		
		// Añadimos la clase "actual" a la pestaña activa
		pnl.style.display = 'block';
	}
	
</script>
<div class="col-lg-5 col-md-5 col-sm-12 col-xs-12 pull-right">
	<div id="calendar" class="col-lg-12 col-md-12 col-sm-12 col-xs-12" >
	</div>
	<div style="margin-top: -15px;"  class="col-lg-12 col-md-12 col-sm-12 col-xs-12" >
		<h4>Padres, Madres o Tutores</h4>
		<display:table style="margin-bottom: 0px;" class="table table-compresed table-hover" pagesize="3" name="tutores">
		
				<spring:message code="alumno.nombre" var="nameHeader" />
	<display:column property="nombre" title="${nameHeader}"  headerClass="sortable" />

	<spring:message code="alumno.apellidos" var="apellidosHeader" />
	<display:column property="apellidos" title="${apellidosHeader}"  headerClass="sortable" />
		</display:table>
	</div>
</div>
<script>
	$(document).ready(function() {
		$("#calendar").eventCalendar({
			  eventsjson: '/appEducacional/rest/tutor/alumno/events/${alumno.id}/',
			  monthNames: [ "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
			    "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" ],
			  dayNames: [ 'Domingo','Lunes','Martes','Miércoles',
			    'Jueves','Viernes','Sabado' ],
			  dayNamesShort: [ 'Dom','Lun','Mar','Mie', 'Jue','Vie','Sab' ],
			  txt_noEvents: "No hay eventos para este periodo",
			  txt_SpecificEvents_prev: "",
			  txt_SpecificEvents_after: "eventos:",
			  txt_next: "siguiente",
			  txt_prev: "anterior",
			  txt_NextEvents: "Próximos eventos:",
			  txt_GoToEventUrl: "Ir al evento",
			  eventsScrollable: true,
			  showDescription: true,
			}); 
		});
	</script>

<div class="col-lg-7 col-md-7 col-sm-12 col-xs-12 pull-left" style="margin-top: 1%;">
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
			<img style="max-width: 85px;" class="thumbnail" style="vertical-align: text-bottom;" src="/appEducacional/rest/profesor/alumno/foto.do?alumnoId=${alumno.id}" alt="Firma">
		</div>
		<div id="nombreApellidos" class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
			<span><b>Nombre:</b> <p><jstl:out value="${alumno.nombre}"></jstl:out></p></span>
			<span><b>Apellidos:</b> <p><jstl:out value="${alumno.apellidos}"></jstl:out></p></span>
		</div>
		<div id="faltas" class="col-lg-6 col-md-6 col-sm-12 col-xs-12" style="height: 140px;
overflow-y: auto;">
			<blockquote>Faltas de Asistencia</blockquote>
			<jstl:forEach items="${faltas}" var="falta">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					 <span class="col-md-12"><fmt:formatDate pattern="dd-MM-yyyy" 
            value="${falta.fecha}" /> </span>
            		
				</div>
			</jstl:forEach>
			
			
		</div>
	</div>
	
	
	
	<div class="row" style="margin-right: 0px;">
		<!-- Comienzo Bloque pestañas trabajo -->
		<spring:message var="label" code='alumno.items' />
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" ><span class="span-4"><b>${label}</b></span></div>
		<div id="panel" style="height: 300px;" class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		
			<ul id="tabs1">
		    	<li id="tab_01"><a onclick="tab('tab_01','panel_01','1');">1ª Evaluación</a></li>
		        <li id="tab_02"><a onclick="tab('tab_02','panel_02','1');">2ª Evaluación</a></li>
		        <li id="tab_03"><a onclick="tab('tab_03','panel_03','1');">3ª Evaluación</a></li>
		    </ul>
			<div id="paneles1" style="height: 250px;">
				<div id="panel_01" style="height: 250px;">
					<table class="table table-hover table-compresed col-lg-12 col-md-12 col-sm-12 col-xs-12">
						<tr class="success">
							<th class="col-lg-6">Título</th>
							<th class="col-lg-6">Calificación</th>
						</tr>
						<!-- Attributes -->
						<jstl:forEach items="${items}" var="item">
		    				<jstl:if test="${ item.evaluacion.indicador==1}">
		    					
		        					<!-- code here -->
		        					<tr>
		        						<td>
		        							<jstl:out value="${item.titulo}"></jstl:out>
		        						</td>
		        						<td>
		        							<jstl:out value="${item.calificacion}"/>
		        						</td>
		        					</tr>
		    				</jstl:if>
						</jstl:forEach>
					</table>
				</div>
				<div id="panel_02" style="height: 250px;">
					<table class="table table-hover table-compresed col-lg-12 col-md-12 col-sm-12 col-xs-12">
						<tr class="success">
							<th class="col-lg-6">Título</th>
							<th class="col-lg-6">Calificación</th>
						</tr>
						<!-- Attributes -->
						<jstl:forEach items="${items}" var="item">
		    				<jstl:if test="${ item.evaluacion.indicador==2}">
		    					
		        					<!-- code here -->
		        					<tr>
		        						<td>
		        							<jstl:out value="${item.titulo}"></jstl:out>
		        						</td>
		        						<td>
		        							<jstl:out value="${item.calificacion}"/>
		        						</td>
		        					</tr>
		    				</jstl:if>
						</jstl:forEach>
					</table>
				</div>
				<div id="panel_03" style="height: 250px;">
					<table class="table table-hover table-compresed col-lg-12 col-md-12 col-sm-12 col-xs-12">
						<tr class="success">
							<th class="col-lg-6">Título</th>
							<th class="col-lg-6">Calificación</th>
						</tr>
						<!-- Attributes -->
						<jstl:forEach items="${items}" var="item">
		    				<jstl:if test="${ item.evaluacion.indicador==3}">
		        					<!-- code here -->
		        					<tr>
		        						<td>
		        							<jstl:out value="${item.titulo}"></jstl:out>
		        						</td>
		        						<td>
		        							<jstl:out value="${item.calificacion}"/>
		        						</td>
		        					</tr>
		    				</jstl:if>
						</jstl:forEach>
					</table>
				</div>
			</div>
		</div>	
		<!-- Fin Bloque pestañas trabajo -->
		
		
		
			<script type="text/javascript">
				tab('tab_01','panel_01','1');
				jQuery('.eventsCalendar-monthWrap').css("width","100%");
				jQuery('.currentMonth').css("width","100%");
			</script>
		</div>
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" id="botonera">
			<a class="btn btn-success" href="app/profesor/alumno/concertarCita.do?alumnoId=${alumnoId}"><i class="fa fa-calendar"></i>&nbsp;&nbsp;Cita</a>
			<a class="btn btn-success" href="app/profesor/alumno/enviarNotificacion.do"><i class="fa fa-send"></i>&nbsp;&nbsp;Notificaci&oacute;n</a>
			<a class="btn btn-success" href="app/profesor/alumno/establecerNotaTrabajoDiario.do?alumnoId=${alumnoId}"><i class="fa fa-pencil-square-o"></i>&nbsp;&nbsp;Nota</a>
			<a class="btn btn-success" href="app/profesor/alumno/estadisticas.do?alumnoId=${alumnoId}"><i class="fa fa-bar-chart-o"></i>&nbsp;&nbsp;Estad&iacute;sticas</a>
			<a class="btn btn-success" href="rest/profesor/alumno/generatePDF/${alumnoId}.do"><i class="fa fa-file-pdf-o"></i>&nbsp;&nbsp;PDF</a>
		</div>
</div>
