<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<div class="row pull-left col-lg-6" style="border-left: 1px solid silver;margin-top:2%;">
		<div class="col-lg-4">
			<img style="max-width: 100px;" class="img-thumbnail img-responsive" src="rest/profesor/alumno/foto.do?alumnoId=${alumnoId}" alt="Firma">
		</div>
		<div class="col-lg-8">
			<Label>Nombre:</Label> <span><jstl:out value="${alumno.nombre}"></jstl:out></span><br>
			<label>Apellidos:</label> <span><jstl:out value="${alumno.apellidos}"></jstl:out></span><br>
			<label>Cursos repetidos:</label> <span><jstl:out value="${alumno.repiteCurso}" /></span> <br>
			<label>Pendiente:</label><span> <jstl:out value="${alumno.pendiente}" /></span>
		</div>
	</div>
<div class="col-lg-offset-6"></div>
	<table class="col-lg-12 col-md-12 col-sm-12 col-xs-12" id="tablaNotasDiarias" style="display: none;margin-top: 3%;">
	<%
	String[] meses = {"Sept","Oct","Nov","Dic","En","Feb","Mar"
			,"Abr","May","Jun"};
	int[] curso = {2014,2015};
	%>
<%
	for (int i = 0; i < 11; i++)
	{
		if (i == 0 )
		{%>
			<tr>
				<%
				for (int ii = 0; ii < 32; ii++)
				{
					if ( ii == 0 )
					{
						%>
						<th> </th>	
						<%
					}
					else
					{
						%>
						<th><%=ii %></th>	
						<%	
					}
				}
				%>
			</tr>
		<%}
		else
		{
		%>
		<tr>
			<%
			for (int ii = 0; ii < 32; ii++)
			{
				if (ii == 0 && i > 0)
				{
					%>
					<th><%=meses[i-1] %></th>
					<%
				}
				else
				{
					Calendar cal = Calendar.getInstance();
					int anio;
					if ( Calendar.MONTH >= 8 )
					{
						anio = curso[0];
					}
					else
					{
						anio = curso[1];
					}
					%>

					<%
					if ( i <= 4 ){
						cal.set(anio-1, i+7, ii);
					}
					else
					{
						cal.set(anio, i-5, ii);
					}
					
					if (cal.get(Calendar.DAY_OF_WEEK) == 1 || cal.get(Calendar.DAY_OF_WEEK) == 7  )
					{
						%>
							<td style="cursor: not-allowed;"><img style="width: 15px;height: 25px;" src="images/All_Controls.png" width="10" height="10" /></td>	
						<%
					}
					else
					{
						%>
						<jstl:set var="contains" value="false" />
						<jstl:set var="fecha" value="<%=new Date(cal.getTimeInMillis()) %>" />
						<jstl:forEach var="item" items="${festivos}">
							<fmt:formatDate value="${item}" pattern="yyyy-MM-dd" var="fecha1" />
							<fmt:formatDate value="${fecha}" pattern="yyyy-MM-dd" var="fecha2" />
  							<jstl:if test="${fecha1 eq fecha2}">
    							<jstl:set var="contains" value="true" />
  							</jstl:if>
						</jstl:forEach>
						
						<jstl:choose>
							<jstl:when test="${contains}">
								<td><img style="width: 15px;height: 25px;" src="images/All_Controls.png" width="10" height="10" /></td>	
							</jstl:when>
							<jstl:otherwise>
								<td><button class="btn btn-success" id="<%=cal.get(Calendar.DAY_OF_MONTH) + "-" +  (cal.get(Calendar.MONTH)+1) + "-"  + cal.get(Calendar.YEAR)   %>"> <i style="margin-left:-1px;margin-top:-5px;" class="fa fa-wrench"></i></button></td>	
							</jstl:otherwise>
						  </jstl:choose>
						
						<%
					}
				}
			}
			%>
		</tr>	
	<%	}	
	}%>
	</table>
	
	<script>
	
	
	function enviar_nota_diaria(fecha)
	{
		var indice = $('#tipo')[0].selectedIndex;
		var calificacion = (indice==2 || indice==4 || indice==5 || indice==8 || indice==9)?0:10;
		var alumnoId = ${alumnoId};
      	$.ajax
      	(
      		{
            		type:"POST",
            		cache:false,
            		url:"/appEducacional/rest/profesor/alumno/guardarNotaDiaria.do",
            		data:
            			{
            				tipoNota : $('#tipo').find(":selected").val(),
            				alum : alumnoId,
            				fecha: fecha,
            				calificacion: calificacion
            			},    // multiple data sent using ajax
            		success: function (html) 
            			{
							$('#container-body').append('<div class="alert alert-success col-lg-12 col-md-12 col-xs-12 col-sm-12">Guardado satisfactoriamente</div>');
            			},
            		error: function(response)
            		{
            			$('#container-body').append('<div class="alert alert-error col-lg-12 col-md-12 col-xs-12 col-sm-12">Se ha producido un error, intentelo de nuevo m&aacute;s tarde</div>');
            		}
          	}
      	);
	}	
	
		$('#tablaNotasDiarias').css("display","table");
		$("button").click
		( 
				function(e)
				{
					var fecha = this.id;
					e.preventDefault();
					$('<div class="modal" id="myModal">'+
							'<div class="modal-dialog">'+
						      '<div class="modal-content">'+
						        '<div class="modal-header">'+
						          '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>'+
						          '<h4 class="modal-title">Establecer nota</h4>'+
						        '</div>'+
						        '<div class="modal-body">'+
						        '  <form class="form-horizontal">' +
								'    <label for="tipo">Tipo</label>' +
								'    <select id="tipo" class="form-control">'+
								'    <option value="Actitud">Trabaja en clase</option>'+
								'    <option value="Actitud">Trabaja en casa</option>'+
								'    <option value="Actitud">No Trabaja</option>'+
								'    <option value="Cuaderno">Cuaderno</option>'+
								'    <option value="Cuaderno">Cuaderno mal presentado</option>'+
								'    <option value="Cuaderno">Cuaderno no presentado</option>'+
								'    <option value="Actividad">Actividades entregadas</option>'+
								'    <option value="Actividad">Actividades entregadas fuera de tiempo</option>'+
								'    <option value="Actividad">Actividades no entregadas</option>'+
								'    <option value="Actitud">Habla en clase</option>'+
								'    </select>'+
								'  </form>'+
						        '</div>'+
						        '<div class="modal-footer">'+
						          '<a href="#" data-dismiss="modal" class="btn btn-danger">Cancelar</a>'+
						          '<a data-dismiss="modal" onclick="enviar_nota_diaria(\'' + fecha +'\');" class="btn btn-primary">Guardar</a>'+
						        '</div>'+
						      '</div>'+
						    '</div>'+
						'</div>').appendTo("#tablaNotasDiarias");
					
					
					
					/*
				    $("#dialog-form").dialog
				    (
				    		{
				    			autoOpen: false,
				    		    height: 220,
				    		    width: 360,
				    		    resizable: false,
				    	   		autoOpen: false,
				    	  		modal: true,
				    	  		buttons: {
				    	  	        Guardar : function(e)
				    	  	        {
				    	  	        	
				    	  	        	
				    	  	        	if (e.target) 
				    	  	        	{
				    	  	        		var indice = $('#tipo')[0].selectedIndex;
				    						var calificacion = (indice==3 || indice==5 || indice==6)?0:10;
				    						var alumnoId = ${alumnoId}
					    	  	        	$.ajax
					    	  	        	(
					    	  	        		{
					    	  	              		type:"POST",
					    	  	              		cache:false,
					    	  	              		url:"/appEducacional/rest/profesor/alumno/guardarNotaDiaria.do",
					    	  	              		data:
					    	  	              			{
					    	  	              				tipoNota : $('#tipo').find(":selected").val(),
					    	  	              				alum : alumnoId,
					    	  	              				fecha: fecha,
					    	  	              				calificacion: calificacion
					    	  	              			},    // multiple data sent using ajax
					    	  	              		success: function (html) 
					    	  	              			{
					    	  	              				alert('Guardado');
					    	  	              				$("#dialog-form").dialog( "close" );
					    	  	              			},
					    	  	              		error: function(response)
					    	  	              		{
					    	  	              			alert("Ha ocurrido un error inesperado, vuelva a intentarlo próximamente");
					    	  	              		}
					    	  	            	}
					    	  	        	);
				    	  	        	}
				    	  	        	
				    	  	        },
				    	  	        Cancelar : function()
				    	  	        {
				    	  	          	$("#dialog-form").dialog( "close" );
				    	  	        }
				    	  		}
				    	    }
				    );
				    
				    $('.ui-button-text').each(function() {
				        var $this = $(this);
				        $this.html($this.parent().attr('text'));
				    });
				    
				    $("#dialog-form").dialog("open");
				    */
				    $('#myModal').modal('show');
				}
		);
	</script>

