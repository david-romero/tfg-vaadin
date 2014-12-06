<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<form role="form" method="POST" style="margin-top:20px;" class="col-lg-12 col-md-12 col-sm-12 col-xs-12 form-horizontal" action="app/tutor/alumno/buscarVinculacion.do">


	<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<label class="col-sm-3 control-label">Nombre:</label>
			<div class="col-sm-9">
				<input name="nombre" class="form-control"/>
				
	    	</div>
	</div>
	<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
	    	
			<label   class="col-sm-3 control-label">Apellidos:</label>
			<div class="col-sm-9">
				<input name="apellidos" class="form-control"/>

			</div>
	</div>
	<div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12" style="margin-top:25px;margin-left: 10.8%;">
		<label for="curso" class="col-sm-3 control-label">Curso:</label>
		<div class="col-sm-9">
			<select name="curso"  class="form-control"  >
				
				
			    <jstl:forEach var="curso" items="${cursos}">
			        <option value="${curso.id}">
			        	<jstl:out value="${curso.nivel} ${curso.nivelEducativo} ${curso.identificador }"/>
			        </option>
			    </jstl:forEach>
			</select>
		</div>
	</div>


	
	<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
				
				<label  class="col-sm-3 control-label">Fecha de Nacimiento:</label>
				<div class="col-sm-9 ">
					<div class="input-group  date" id="datetimepicker">
						<input id="date-picker"   class="form-control" value="" name="fechaNacimiento"  />
						

						<span id="calendar-icon" class="add-on glyphicon" style="display: table-cell;width: 22px;font-size: 14px;font-weight: normal;line-height: 1;color: #555;text-align: center;background-color: #eee;border: 1px solid #ccc;border-top-right-radius: 4px;border-bottom-right-radius: 4px;">
    <i class="glyphicon" data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
  </span>
					</div>
				</div>
	</div>

	<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12" style="text-align: center;">
				  <input class="btn btn-info" type="submit" id="save" name="save" value="Enviar" />
	</div>


</form>


	<script type="text/javascript">

          $(function() 
          {
        	  $.getScript("/appEducacional/scripts/bootstrap-datetimepicker.js", function(){
        		  $.getScript("/appEducacional/scripts/bootstrap-datetimepicker-es.js", function(){
        		    $('#datetimepicker').datetimepicker({
        		    	language: 'es',
        		      format: 'MM/dd/yyyy',
        		      endDate: new Date()
        		    });
        		    

        		    
        		  var picker = $('#datetimepicker').data('datetimepicker');
        		  picker.setLocalDate(new Date());
        		    

        		  });//get script
        		  });//get script
        		  
        		  

        		   $('form span').each(function(index){
        		       if( $(this).text().length !== 0 ){
        		    	  $(this).css("color","#a94442"); 
        		    	  $(this).addClass('glyphicon-remove');
        		          $(this).parent().parent().addClass('has-error');
        		          // When a span with some value is found, alert the index of the row
        		       }
        		   }); 
        		  
        		  $('#calendar-icon').removeClass('glyphicon-remove');
        		  $('#date-picker').parent().parent().removeClass('has-error');
        	  
          });

</script>