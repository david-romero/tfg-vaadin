<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="es">
<head>

<base
	href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">



<link href="//netdna.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">






<!-- Boostrap desde Internet -->
<!-- Latest compiled and minified CSS -->

<link rel="stylesheet" href="styles/bootstrap.css">
<!-- Optional theme -->
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">

<link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css" rel="stylesheet">	
	
	
<!-- Welcome -->

    <link rel="stylesheet" href="styles/bootstrap-responsive.min.css">
    <link rel="stylesheet" href="styles/sl-slide.css">
	

<link rel="stylesheet" type="text/css" media="screen" href="styles/bootstrap-datetimepicker.css">



<!-- jquery desde google -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>


<link rel="stylesheet" type="text/css" href="styles/default.css" />
<link rel="stylesheet" type="text/css" href="styles/eventCalendar_theme_responsive.css" />
<link rel="stylesheet" type="text/css" href="styles/eventCalendar_theme.css" />
<link rel="stylesheet" type="text/css" href="styles/eventCalendar.css" />
<!--<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />-->
<link rel="stylesheet" type="text/css" href="styles/component.css" />
<link rel="stylesheet" type="text/css" href="styles/common.css" />

<!-- Boostrap Slider Range -->
<link rel="stylesheet" href="styles/powerange.css" />
<script src="scripts/powerange.js"></script>

<script src="scripts/modernizr.custom.js"></script>

<script src="scripts/jquery.eventCalendar.js"></script>





<link rel="shortcut icon" href="favicon.ico"/> 

<link rel="stylesheet" type="text/css" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" />
 <!-- Añadido para el autocomplete -->
    <script type="text/javascript"
        src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.9.1/jquery-ui.min.js"></script>

 <link rel="stylesheet" href="styles/calendar.css">

<title><tiles:insertAttribute name="title" /></title>

</head>

<body class="col-lg-10 col-lg-offset-1 col-md-10 col-md-offset-1 col-sm-12 col-xs-12">
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="z-index: 1000;">
		<tiles:insertAttribute name="header" />
	</div>
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12"  id="container-body">
		<tiles:insertAttribute name="body" />
		<jstl:if test="${message != null}">
			<br />
			<span class="error"><spring:message code="${message}" /></span>
		</jstl:if>	
	</div>
	<div id="footer" class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<tiles:insertAttribute name="footer" />
	</div>
	<div id="myModal" class="modal fade">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	        <h4 class="modal-title">Importar Alumnos desde un fichero de formato CSV</h4>
	      </div>
	      <div class="modal-body">
	       	<form role="form" class="form-horizontal">
	       		<label class="form-label"></label>
	       		<input id="fichero" accept=".csv" type="file" class="form-control" name="ficheroCSV">
	       	</form>
	       	<br>
	       	<div class="progress progress-striped">
			  <div class="progress-bar progress-bar-success" role="progressbar"
			       aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"
			       style="width: 0%">
			    <span class="sr-only">0% completado (éxito)</span>
			  </div>
			</div>
	       	
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
	        <button id="importar_button" onclick="importar_alumnos();" type="button" class="btn btn-primary">Importar</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	<script>
		function importar_alumnos()
		{
			var formData = new FormData($('form')[0]);
			file = $('#fichero')[0].files[0];
			formData.append("file",file);
		    $.ajax({
		        url: 'rest/profesor/alumno/importarAlumnos.do',  //Server script to process data
		        type: 'POST',
		        xhr: function() {  // Custom XMLHttpRequest
		            var myXhr = $.ajaxSettings.xhr();
		            if(myXhr.upload){ // Check if upload property exists
		                myXhr.upload.addEventListener('progress',progressHandlingFunction, false); // For handling the progress of the upload
		            }
		            return myXhr;
		        },
		        //Ajax events
		        //beforeSend: beforeSendHandler,
		        success: function(response) 
		        	{
		        		$('#importar_button').prop('disabled', true);
		        		$('#importar_button').addClass('disabled'); 
		        		$('.modal-body').find('.alert').hide();
		        		$('.modal-body').append('<div class="alert alert-success">'+  
		        				  '<a class="close" data-dismiss="alert">x</a>  '+
		        				  '<strong>Éxito!</strong> Los alumnos han sido guardados.'+  
		        				'</div>');
		        		
		        	},
		        error: function(response)
		        	{
		        		$('.modal-body').find('.alert').hide();
		        		$('.modal-body').append('<div class="alert alert-error">'+  
	        				  '<a class="close" data-dismiss="alert">x</a>  '+
	        				  '<strong>Érror!</strong> Ha ocurrido un error.'+  
	        				'</div>');
		        	},
		        // Form data
		        data: formData,
		        //Options to tell jQuery not to process data or worry about content-type.
		        cache: false,
		        contentType: false,
		        processData: false
		    });
		}
		function progressHandlingFunction(e){
		    if(e.lengthComputable){
		        $('progress').attr({value:e.loaded,max:e.total});
		        $('.progress-bar').css("width",e.loaded + "%");
		    }
		}
	</script>
	<security:authorize access="hasRole('PROFESOR')">
		<script>
			$.ajax({
		        url: 'rest/profesor/numeroNotificaciones.do',  //Server script to process data
		        type: 'GET',
		        //Ajax events
		        //beforeSend: beforeSendHandler,
		        success: function(response) 
		        	{
		        		$('#contador_notificaciones').html(response);
		        		
		        	},
		        error: function(response)
		        	{
		        		//No es un profesor
		        	},
		    });
		</script>
	</security:authorize>
	<security:authorize access="hasRole('TUTOR')">
		<script type="text/javascript">
			$.ajax({
		        url: 'rest/tutor/numeroNotificaciones.do',  //Server script to process data
		        type: 'GET',
		        //Ajax events
		        //beforeSend: beforeSendHandler,
		        success: function(response) 
		        	{
		        		$('.contador_notificaciones').html(response);
		        	},
		        error: function(response)
		        	{
		        		//No es un profesor
		        	},
		    });
		</script>
	</security:authorize>
		<security:authorize access="hasRole('ADMINISTRADOR')">
		<script type="text/javascript">
			$.ajax({
		        url: 'rest/admin/numeroPersonasConfirmar.do',  //Server script to process data
		        type: 'GET',
		        //Ajax events
		        //beforeSend: beforeSendHandler,
		        success: function(response) 
		        	{
		        	    if (isFinite(response) && response > 0 )
		        	    {
				        	$('.carousel-indicators').append('<li data-target="#slider" data-slide-to="3"></li>');
			        		$('.carousel-inner').append('<div class="item">'+
			                        '<img style="margin-top:260px;" class="pull-right" src="images/img4.png" alt="" />'+
			                        '<div class="carousel-caption" style="left:-15%;top:inherit;">'+
				                        '<h2>Tienes por confirmar ' + response + ' personas</h2>'+
				                        '<h3 class="gap">Debes confirmarlas lo antes posible.'+
				                        '</h3>'+
				                        
			                        '</div>'+
			                '</div>');
		        	    }
		        	},
		        error: function(response)
		        	{
		        		//No es un profesor
		        	},
		    });
		</script>
	</security:authorize>
</body>
</html>
