<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

    <form:form method="POST" cssClass="form-horizontal col-lg-12 col-md-12 col-sm-12 col-xs-12" role="form" action="app/profesor/evento/save.do" modelAttribute="evento" >

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="profesor"/>
		<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
				
				<form:label path="fecha" cssClass="col-sm-1 control-label">Fecha:</form:label>
				<div class="col-sm-11 ">
					<div class="input-group  date" id="datetimepicker">
						<form:input id="date-picker" path="fecha"   cssClass="form-control" value="${dataString}" name="fechaNacimiento"  />
						

						<span id="calendar-icon" class="add-on glyphicon" style="display: table-cell;width: 22px;font-size: 14px;font-weight: normal;line-height: 1;color: #555;text-align: center;background-color: #eee;border: 1px solid #ccc;border-top-right-radius: 4px;border-bottom-right-radius: 4px;">
    						<i class="glyphicon" data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
  						</span>
					</div>
					<form:errors cssClass="error glyphicon" path="fecha" />
				</div>
		</div>
		<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<label for="tipoEvento">Tipo de Evento:</label>
			<select class="form-control" name="tipoEvento" id="tipoEvento">
				<option value="">&nbsp;</option>
				<option value="Examen">Examen</option>
				<option value="Trabajo">Entrega de Trabajo</option>
				<option value="Actividad">Entrega de Actividades</option>
				<option value="EjerciciosEntregados">Entrega de Ejercicios</option>
				<option value="Cuaderno">Entrega de Cuaderno</option>
			</select>
		</div>
		<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<ul class="nav nav-pills nav-stacked col-md-2">
			  <li class="active"><a href="#tab_a" data-toggle="pill">Cursos</a></li>
			  <li><a href="#tab_b" data-toggle="pill">Alumnos</a></li>
			</ul>
			<div class="tab-content col-md-10">
			        <div class="tab-pane active" id="tab_a">
			             <h4>Mis Cursos</h4>
			             <jstl:forEach items="${cursos}" var="curso">
			             	<div class="input-group">
						      <span class="input-group-addon">
						        <input type="checkbox" name="cursoSeleccionado" id="curso_seleccionado" value="${curso.id }">
						      </span>
						      <input type="text" class="form-control" value="${curso }" readonly="readonly">
						    </div><!-- /input-group -->
			             </jstl:forEach>
			        </div>
			        <div class="tab-pane" id="tab_b" style="height: 340px;overflow-y: auto;">
			             <h4>Mis Alumnos</h4>
			             <div class="input-group">
						      <span class="input-group-addon">
						        <input type="checkbox" value="Seleccionar Todos" name="select_all" id="all">
						      </span>
						      <input type="text" class="form-control" value="Seleccionar Todos" readonly="readonly">
						 </div><!-- /input-group -->
			             <jstl:forEach items="${alumnos}" var="alumno">
			             	<div class="input-group">
						      <span class="input-group-addon">
						        <input type="checkbox" value="${alumno.id }" name="alumnoSeleccionado" id="alumno_seleccionado">
						      </span>
						      <input type="text" class="form-control" value="${alumno}" readonly="readonly">
						    </div><!-- /input-group -->
			             </jstl:forEach>
			        </div>
			</div><!-- tab content -->
		</div>
		
		<script type="text/javascript">
			$('#all').click( function(){
				   if( $(this).is(':checked') )
				   {
					   $("input").prop('checked', true);
				   }
				   else
				   {
					   $("input").prop('checked', false);
				   }
			});
		</script>
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="text-align: center;">
			<input type="submit" id="save" name="save" value="Enviar" class="btn btn-success" />
			<a class="btn btn-danger" href="app/welcome/index.do">Cancelar</a>
		</div>

</form:form>

<script type="text/javascript">

          $(function() 
          {
        	  $.getScript("/appEducacional/scripts/bootstrap-datetimepicker.js", function(){
        		  $.getScript("/appEducacional/scripts/bootstrap-datetimepicker-es.js", function(){
        		    $('#datetimepicker').datetimepicker({
        		    	language: 'es',
        		      format: 'dd/MM/yyyy',
        		      startDate: new Date()
        		    });
        		    

        		    
        		  var picker = $('#datetimepicker').data('datetimepicker');
        		  picker.setLocalDate(new Date());
        		    

        		  });//get script
        		  });//get script
        		  
          });//function
</script>
