<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<style>
.spinner {
  
}
.spinner input {
  text-align: right;
}
.input-group-btn-vertical {
  position: relative;
  white-space: nowrap;
  width: 1%;
  vertical-align: middle;
  display: table-cell;
}
.input-group-btn-vertical > .btn {
  display: block;
  float: none;
  width: 100%;
  max-width: 100%;
  padding: 8px;
  margin-left: -1px;
  position: relative;
  border-radius: 0;
}
.input-group-btn-vertical > .btn:first-child {
  border-top-right-radius: 4px;
}
.input-group-btn-vertical > .btn:last-child {
  margin-top: -2px;
  border-bottom-right-radius: 4px;
}
.input-group-btn-vertical i{
  position: absolute;
  top: 0;
  left: 4px;
}
</style>
				<script>
				
  					$(function() 
  							{
  						
  								/*Spiner numeros curso
  								
  								*/
  								$('#curso-nivel-up').on('click', function() {
  									if ($('#curso-nivel').val() < 6)
  			  					    	$('#curso-nivel').val( parseInt($('#curso-nivel').val(), 10) + 1);
  			  					  });
  			  					  $('#curso-nivel-down').on('click', function() 
  			  						{
  			  						  	if ($('#curso-nivel').val() > 1)
  			  					    		$('#curso-nivel').val( parseInt($('#curso-nivel').val(), 10) - 1);
  			  					  	});
  			  					  
  			  					  
  			  					/*Spiner letras curso
    								
    								*/
    								$('#curso-id-up').on('click', function() {
    									var n = $('#curso-identificador').val().charCodeAt(0);
    									if (n < 75)
    			  					    	$('#curso-identificador').val( String.fromCharCode(n+1));
    			  					  });
    			  					  $('#curso-id-down').on('click', function() 
    			  						{
    			  							var n = $('#curso-identificador').val().charCodeAt(0);
    			  						  	if (n > 65)
    			  					    		$('#curso-identificador').val( String.fromCharCode(n-1) );
    			  					  	});
  						
  						    /*var spinner = $("#curso-identificador").spinner({
  						        min: 65,
  						        max: 90
  						    }).data("spinner");
  						    
  						    spinner._parse = function (value) {
  						        if (typeof value === "string") {
  						            return value.charCodeAt(0);
  						        }
  						        return value;        
  						    };
  						    
  						    spinner._format = function (value) {
  						        return String.fromCharCode(value);        
  						    }*/
  						$("#buscarAsignaturas").autocomplete({
  						    source: function (request, response) {
  						        var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
  						        $.ajax({
  						        	type: "GET",
  						            url: '${pageContext.request.contextPath}/rest/profesor/asignaturas',
  						            dataType: "json",
  						            success: function (data) {
  						                response($.map(data, function(v,i){
  						                    var text = v;

  						                    if ( text && ( !request.term || matcher.test(text) ) )
  						                    {
  						                        return {
  						                                label: v,
  						                                value: v,
  						                                id: i
  						                               };
  						                    }
  						                }));
  						            }
  						        });
  						    }
  						});
  						$("#buscarAsignaturas").click(function(){
  							$("#buscarAsignaturas").val('');
  						});	
  								
  								//$("#curso-identificador").alphaspinner();
  								$("#addCurso").click
  								(
  										function ()
  										{
  											var identificador = $("#curso-identificador").val();
  											var nivel = $("#curso-nivel" ).val() + "º";
  											var nivel_Educativo = $("#curso-nivelEducativo").val();
  											var curso = nivel + " " + nivel_Educativo + " " + identificador;
  											max = 2;
  											var tr = $("#cursos tbody tr:last");
  											if(!tr.length || tr.find("td").length >= max)
  										    	$("#cursos tbody").append("<tr>");
  											var tr = $("#cursos tbody tr:last");
  											if ( tr.find("td").length == 0 )
  										    	$("#cursos tbody tr:last").append("<td  style='width:20.85em'>" + curso 
  										    			+ "<input type='image' src='images/close.png' onclick='$(this).parent().parent().remove();'" 
  										    			+ "style='width:15px;height:15px;float:right'/></td>");
  											//else
  												//$("#cursos tbody tr:last").append("<td style='width:20.85em'>Matematicas</td>");
  											return false;
  										}
  								);
  								$("#addAsignaturas").click
  								(
  										function ()
  										{
  											var asignatura = $("#buscarAsignaturas").val();
  											var tr = $("#cursos tbody tr:last");
  											var bandera = false;
  											if (asignatura != "")
  											{
	  											$('#cursos > tbody  > tr').each
	  											(
	  												function(index,tr) 
	  												{
	  													$this = $(this);
	  													$tr = $("#cursos tbody tr:last");
	  													var cursoEnTabla = $this.find("td").eq(0).text();
	  													var cursoActual = $tr.find("td").text();
	  													if (cursoActual = cursoEnTabla)
	  														{
	  															var asignaturaEnTabla = $this.find("td").eq(1).text();
	  															if (asignaturaEnTabla == asignatura )
	  																{
	  																	alert("Ya existe esa relación");
	  																	bandera = true;
	  																}
	  														}
	  														
	  												}
	  											);
  											}
  											if ( asignatura == "" )
  												bandera = true;
  											if ( tr.find("td").length == 1 && !bandera )
  											{
  												$("#cursos tbody tr:last").append("<td style='width:20.85em'>" + asignatura + 
  										    			"<input type='image' src='images/close.png' onclick='$(this).parent().remove();'" 
  										    			+ "style='width:15px;height:15px;float:right;'/></td>");
  												$('#cursosCombo').append("<option selected='selected'>" + tr.find("td").eq(0).text() +"/" + asignatura + "</option>");
  											}
  										    	
  											return false;
  										}
  								);
  								
  								$('#formulario').submit
  								(
  									function()
  									{
  											
  									}
  								);
  							}
  					);
  					
  					
  					
  				</script>
<form:form id="formulario" cssClass="form-horizontal " action="${saveURL}" modelAttribute="profesorCreado"  cssStyle="margin-top: -35px;" >
	<form:errors cssClass="error" path="*" />
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="identidadConfirmada"/>
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
				<form:label path="nombre">Nombre:</form:label>
				<form:input cssClass="form-control" path="nombre"/> 
				<form:errors cssClass="error" path="nombre" />
			</div>
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
				<form:label path="apellidos">Apellidos:</form:label>
				<form:input cssClass="form-control" path="apellidos"/>
				<form:errors cssClass="error" path="apellidos" />
			</div>
				
		</div>
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">		
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
				<form:label path="preferenciasCita">Preferencias de Tutorías</form:label><br>
				<form:textarea cssClass="form-control" path="preferenciasCita" style="resize: none;"/><br>
				<form:errors cssClass="error" path="preferenciasCita" />
			</div>
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12" style="margin-top: 1.5%;">
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
					<form:label path="DNI">D.N.I.</form:label>
					<form:input cssClass="form-control" path="DNI" size="9" maxlength="9"/>
					<form:errors cssClass="error" path="DNI" />
				</div>
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
					<form:label path="email">Email:</form:label>
					<form:input cssClass="form-control" path="email"/><br>
					<form:errors cssClass="error" path="email" />
				</div>
				
			</div>
		</div>
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
				<form:label path="telefono">Teléfono</form:label>
				<form:input cssClass="form-control" path="telefono" size="12" maxlength="12"/>
				<form:errors cssClass="error" path="telefono" />
			</div>	
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">	

				<form:label path="instituto">Centro Educativo:</form:label>
				<form:input cssClass="form-control" path="instituto"/>
				<form:errors cssClass="error" path="instituto" />
			</div>
		</div>	
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<jstl:choose>
			<jstl:when test="${edit eq true}">
				<form:hidden path="userAccount"/>
			</jstl:when>
			<jstl:otherwise>
					<form:hidden path="userAccount.authorities"/>
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
					<form:label path="userAccount.username">Username:</form:label>
					<form:input cssClass="form-control" path="userAccount.username"/><br>
					<form:errors cssClass="error" path="userAccount.username" />
				</div>
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
					<form:label path="userAccount.password">Password:</form:label>
					<form:password cssClass="form-control" path="userAccount.password"/><br>
					<form:errors cssClass="error" path="userAccount.password" />
				</div>
			</jstl:otherwise>
			</jstl:choose>
		</div>
		<jstl:if test="${edit eq false}">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
					<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
						<label class="form-label" for="curso-nivel">Curso</label>
					</div>
					<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
						<div class="input-group spinner">	
	  						<input class="form-control " id="curso-nivel" name="curso-nivel" value="1" readonly="readonly">
	  						<div class="input-group-btn-vertical">
						      <a id="curso-nivel-up" class="btn btn-default"><i class="fa fa-caret-up"></i></a>
						      <a id="curso-nivel-down" class="btn btn-default"><i class="fa fa-caret-down"></i></a>
						    </div>
						  </div>
	  				</div>
	  				<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
						<select class="form-control"  id="curso-nivelEducativo" name="curso-nivelEducativo">
							<option id="Primaria">Primaria</option>
							<option id="E.S.O.">E.S.O.</option>
							<option id="Bachiller">Bachiller</option>
						</select>
					</div>
					<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
						<div class="input-group spinner">
							<input style="font-size: 15px;" class="form-control" type="text" id="curso-identificador" maxlength="1" size="1"  value="A" readonly="readonly" />
							<div class="input-group-btn-vertical">
						      <a id="curso-id-up" class="btn btn-default"><i class="fa fa-caret-up"></i></a>
						      <a id="curso-id-down" class="btn btn-default"><i class="fa fa-caret-down"></i></a>
						    </div>
						</div>
						
					</div>
					<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
						<a id="addCurso" class="btn btn-link btn-lg">
	  <i class="fa fa-check"></i> </a>
					</div>
				</div>
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
					<label for="buscarAsignaturas" class="form-label col-sm-3">Asignaturas</label>
					<div class="input-group col-sm-9">
						<input style="border-top-right-radius: 3px;border-bottom-right-radius: 3px;" class="form-control" type="text" name="buscarAsignaturas" id="buscarAsignaturas"/>
						<span class="input-group-btn">
							<a id="addAsignaturas" class="btn btn-link btn-lg">
	  <i class="fa fa-check"></i> </a>
						</span>
					</div>
				</div>
			</div>
	
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="margin-top:3%;">
					<table id="cursos" class="table table-hover">
						<thead class="">
							<tr class="success">
								<th class="">Curso</th>
								<th class="">Asignatura</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
			</div>
		</jstl:if>
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="text-align: center;">
				  <input class="btn btn-success" type="submit" id="save" name="save" value="Enviar" />
				  <a class="btn btn-danger" href="app/welcome/index.do">Cancelar</a>
		</div>


	<select id="cursosCombo" name="cursosCombo" multiple="multiple" style="display:none">
		
	</select>
</form:form>

