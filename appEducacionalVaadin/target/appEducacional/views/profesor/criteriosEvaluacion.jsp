<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<div id="tabs" class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
	<ul id="tabs-index" class="nav nav-pills nav-stacked col-md-2">
		
	</ul>
	<div id="container-tab" class="tab-content col-md-10">

	        
	</div><!-- tab content -->
</div>
<jstl:set var="counter"  value="${0}"/>
<jstl:forEach items="${asignaturas}" var="asignatura">
	<jstl:if test="${counter eq 0 }">
		<script>
		jQuery('#tabs-index').append('<li class="active" ><a  href="#tab_${asignatura.id}" data-toggle="pill">${asignatura.nombre}</a></li>');
		</script>
		<script>
			jQuery('#container-tab').append('<div class="tab-pane active" id="tab_${asignatura.id}">'+
					'<div class="col-lg-9 col-md-9 col-sm-12 col-xs-12 pull-left">' +
						'<p id="slider_${asignatura.id}"></p>'+
					'</div>'+
					 '<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12 pull-right">' +
			             '<h4 style="">${asignatura.nombre} de ${asignatura.curso.nivel} ${asignatura.curso.nivelEducativo} ${asignatura.curso.identificador}</h4>'+
			             '<h5 style="">Seleccione los porcentajes de peso para cada criterio de evaluacion</h5>' +
			             '<h5 id="suma_total_${asignatura.id}" style="">Suma Total: 0 %</h5>' +
			             '<form id="form_${asignatura.id}" style=""><input type="hidden" name="asignatura" value="${asignatura.id}" />'+
			             '<a class="btn btn-success" onclick="establecer_criterios(${asignatura.id});" id="submit_${asignatura.id}" >Guardar</a></form>'+
				            
			         '</div>'+
			        '</div>');
		</script>
	</jstl:if>
	<jstl:if test="${counter gt 0 }">
		<script>
		jQuery('#tabs-index').append('<li ><a  href="#tab_'+${asignatura.id}+'" data-toggle="pill">${asignatura.nombre}</a></li>');
		</script>
		<script>
			jQuery('#container-tab').append('<div class="tab-pane" id="tab_${asignatura.id}">'+
					'<div class="col-lg-9 col-md-9 col-sm-12 col-xs-12 pull-left">' +
						'<p id="slider_${asignatura.id}"></p>'+
					'</div>'+
					'<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12 pull-right">' +
			             '<h4 style="">${asignatura.nombre} de ${asignatura.curso.nivel} ${asignatura.curso.nivelEducativo} ${asignatura.curso.identificador}</h4>'+
			             '<h5 style="">Seleccione los porcentajes de peso para cada criterio de evaluacion</h5>' +
			             '<h5 id="suma_total_${asignatura.id}" style="">Suma Total: 0 %</h5>' +
			             '<form method="post" action="app/profesor/asignatura/${asignatura.id}/guardarCriterios.do" id="form_${asignatura.id}" style=""><input type="hidden" name="asignatura" value="${asignatura.id}" />'+
			             '<a class="btn btn-success" id="submit_${asignatura.id}" onclick="establecer_criterios(${asignatura.id});"  name="submit" value="Guardar" >Guardar</a></form>'+
				            
		            '</div>'+
			        '</div>');
		</script>
	</jstl:if>
		<script>
			var sumaTotal = 0;
		</script>
		<jstl:forEach items="${items}" var="item">
			<script>
				jQuery('#slider_${asignatura.id}').append('<div>'+
			'<label>${item.getSimpleName()}</label>'+
			'<input  id="slider_${asignatura.id}_${item.getSimpleName()}" data-slider-id="ex1Slider" type="text" data-slider-min="0" data-slider-max="20" data-slider-step="1" data-slider-value="14"/>'+
			''+
			'</div><br>');
				
				
				var criteriosEvaluacion = "${asignatura.criteriosDeEvaluacion}";
				var criterioArray = criteriosEvaluacion.split(",");
				
				var elem = document.querySelector('#tab_${asignatura.id} #slider_${asignatura.id}_${item.getSimpleName()}');
				var existeCriterio = false;
				
				jQuery('#slider_${asignatura.id}').append('<input disabled id="display_${asignatura.id}_${item.getSimpleName()}" class="input_small form-control" type="text" value="0 %" style="width: 4.5em;position: relative;margin-top: -2.6em;margin-left: 80%;" />');
				
				for ( var i = 0; i < criterioArray.length ; i++)
				{
					var criterio = "${item.getSimpleName()}";
					if ( criterioArray[i].split("=")[0].trim().replace("{", "") == criterio )
					{
						console.log("entro!");
						var porcentaje = parseInt(criterioArray[i].split("=")[1]);
						sumaTotal += porcentaje;
						console.log(sumaTotal);
						jQuery('#display_${asignatura.id}_${item.getSimpleName()}').val(porcentaje + " %");
						console.log(jQuery('#display_${asignatura.id}_${item.getSimpleName()}'));
						console.log(jQuery('#display_${asignatura.id}_${item.getSimpleName()}').val());
						
						var init = new Powerange(elem,{ callback: displayValue, start : porcentaje });
						existeCriterio = true;
					}
					
				}
				
				if (!existeCriterio)
				{
					
					jQuery('#display_${asignatura.id}_${item.getSimpleName()}').val(0 + " %");
					var init = new Powerange(elem,{ callback: displayValue });
				}
				
				
				function displayValue()
				{
					var sumaTotal = 0;
					jQuery('#display_${asignatura.id}_${item.getSimpleName()}').val(jQuery('#slider_${asignatura.id}_${item.getSimpleName()}').val() + " %");
					jQuery('#slider_${asignatura.id}').find('.input_small').each(
							function()
							{
								sumaTotal += parseInt(this.value);
								jQuery("#suma_total_${asignatura.id}").html("Suma Total " + sumaTotal + " %");
								if (sumaTotal == 100)
									{
										jQuery('#submit_${asignatura.id}').prop("disabled",false);
									}
								else
									{
										jQuery('#submit_${asignatura.id}').prop("disabled",true);
									}
							});
				};
			</script>
		</jstl:forEach>
		
	<jstl:set var="counter"  value="${counter+1}"/>
</jstl:forEach>

<script>
$('#container-body').append('<div id="success" class="alert alert-success col-lg-12 col-md-12 col-xs-12 col-sm-12">Guardado satisfactoriamente</div>');
$('#container-body').append('<div id="error" class="alert alert-error col-lg-12 col-md-12 col-xs-12 col-sm-12">Se ha producido un error, intentelo de nuevo m&aacute;s tarde</div>');
jQuery('#success').hide();
jQuery('#error').hide();
	function establecer_criterios(asignaturaId)
	{
		
		var examen_porcentaje = parseInt(jQuery('#display_'+ asignaturaId + '_Examen').val(), 10);

		var actitud_porcentaje = parseInt(jQuery('#display_'+ asignaturaId + '_Actitud').val(), 10);

		var falta_porcentaje = parseInt(jQuery('#display_'+ asignaturaId + '_FaltaDeAsistencia').val(), 10);
		var ejercicio_porcentaje = parseInt(jQuery('#display_'+ asignaturaId + '_EjerciciosEntregados').val(), 10);

		var actividad_porcentaje = parseInt(jQuery('#display_'+ asignaturaId + '_Actividad').val(), 10);

		var retraso_porcentaje = parseInt(jQuery('#display_'+ asignaturaId + '_Retraso').val(), 10);
		var cuaderno_porcentaje = parseInt(jQuery('#display_'+ asignaturaId + '_Cuaderno').val(), 10);
		var trabajo_porcentaje = parseInt(jQuery('#display_'+ asignaturaId + '_Trabajo').val(), 10);
		
		var sum = examen_porcentaje + actitud_porcentaje + falta_porcentaje + ejercicio_porcentaje +
		actividad_porcentaje + retraso_porcentaje + cuaderno_porcentaje + trabajo_porcentaje;
		
			if (sum != 100)
			{
				jQuery('#success').hide();
    			jQuery('#error').show();
			}
			else
			{
				$.ajax
		      	(
		      		{
		            		type:"POST",
		            		cache:false,
		            		url:"/appEducacional/rest/profesor/asignatura/guardarCriterios.do",
		            		data:
		            			{
		            				asignatura : asignaturaId,
		            				examen: examen_porcentaje,
		            				actitud: actitud_porcentaje,
		            				falta: falta_porcentaje,
		            				retraso: retraso_porcentaje,
		            				cuaderno: cuaderno_porcentaje,
		            				trabajo: trabajo_porcentaje,
		            				actividad: actividad_porcentaje,
		            				ejercicio: ejercicio_porcentaje
		            				
		            				
		            			},    // multiple data sent using ajax
		            		success: function (html) 
		            			{
		            				jQuery('#success').show();
		            				jQuery('#error').hide();
		            			},
		            		error: function(response)
		            		{
		            			jQuery('#success').hide();
		            			jQuery('#error').show();
		            			
		            		}
		          	}
		      	);
			}
		
	}
</script>