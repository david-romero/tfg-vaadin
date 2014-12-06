<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<script>
function prueba()
{
	var oFReader = new FileReader();
    oFReader.readAsDataURL(document.getElementById("foto").files[0]);

    oFReader.onload = function (oFREvent) {
        document.getElementById("preview").src = oFREvent.target.result;
    };
}
</script>
<form:form role="form" cssStyle="margin-top:20px;" cssClass="col-lg-12 col-md-12 col-sm-12 col-xs-12 form-horizontal" action="${urlSave }" modelAttribute="alumno" enctype="multipart/form-data" >

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="imagen"/>
	<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<form:label path="nombre" cssClass="col-sm-3 control-label">Nombre:</form:label>
			<div class="col-sm-9">
				<form:input path="nombre" cssClass="form-control"/>
				<form:errors cssClass="error glyphicon" path="nombre" />
	    	</div>
	</div>
	<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
	    	
			<form:label path="apellidos" cssClass="col-sm-3 control-label">Apellidos:</form:label>
			<div class="col-sm-9">
				<form:input path="apellidos" cssClass="form-control"/>
				<form:errors cssClass="error glyphicon" path="apellidos" />
			</div>
	</div>
	<div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12" style="margin-top:25px;margin-left: 10.8%;">
		<form:label path="curso" cssClass="col-sm-3 control-label">Curso:</form:label>
		<div class="col-sm-9">
			<form:select  cssClass="form-control" path="curso" >
				
				
			    <jstl:forEach var="curso" items="${cursos}">
			        <form:option value="${curso.id}">
			        	<jstl:out value="${curso.nivel} ${curso.nivelEducativo} ${curso.identificador }"/>
			        </form:option>
			    </jstl:forEach>
			</form:select>
			<form:errors cssClass="error glyphicon" path="curso" />
		</div>
	</div>
	<div class="form-group col-lg-5 col-md-5 col-sm-5 col-xs-12" style="text-align: center;">
				<div style="display:none;overflow:hidden">
					<input name="foto" id="foto" type="file" onchange="prueba();"/>
				</div>
				<img class="img-thumbnail" id="preview"  src="images/alumno.png" style="cursor: pointer;max-width: 90px;height: auto;" onclick="chooseFile();" />
				<p class="help-block">Click para introducir una imagen.</p>
	</div>
<script>
   function chooseFile()
   {
      $("#foto").click();
   }

</script>
	
	<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<fmt:formatDate value="${alumno.fechaNacimiento}" var="dateString" pattern="dd/MM/yyyy" />
				<form:label path="fechaNacimiento" cssClass="col-sm-3 control-label">Fecha de Nacimiento:</form:label>
				<div class="col-sm-9 ">
					<div class="input-group  date" id="datetimepicker">
						<form:input id="date-picker"  path="fechaNacimiento" cssClass="form-control" value="${dataString}" name="fechaNacimiento"  />
						

						<span id="calendar-icon" class="add-on glyphicon" style="display: table-cell;width: 22px;font-size: 14px;font-weight: normal;line-height: 1;color: #555;text-align: center;background-color: #eee;border: 1px solid #ccc;border-top-right-radius: 4px;border-bottom-right-radius: 4px;">
    <i class="glyphicon" data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
  </span>
					</div>
					<form:errors cssClass="error glyphicon" path="fechaNacimiento" />
				</div>
	</div>

	<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<form:label path="pendiente" cssClass="col-sm-3 control-label">Asignaturas pendientes:</form:label>
				<div class="col-sm-9">
					<form:input path="pendiente" cssClass="form-control"/>
				</div>
	</div>
	<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<form:label cssClass="col-sm-3 control-label" path="repiteCurso">¿Cursos repetidos?</form:label>
				<div class="col-sm-9">
					<form:input path="repiteCurso" cssClass="form-control"/>
				</div>
	</div>
	<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12" style="text-align: center;">
				  <input class="btn btn-info" type="submit" id="save" name="save" value="Enviar" />
	</div>


</form:form>


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