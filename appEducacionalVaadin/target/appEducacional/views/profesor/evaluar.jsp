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
	<div class="row pull-left col-lg-6" style="margin-left: 1px solid silver">
		<div class="col-lg-4 col-xs-4">
			<img style="max-width: 100px;" class="img-thumbnail img-responsive" src="rest/profesor/alumno/foto.do?alumnoId=${alumno.id}" alt="Firma">
		</div>
		<div class="col-lg-8 col-xs-8">
			<Label>Nombre:</Label> <span><jstl:out value="${alumno.nombre}"></jstl:out></span><br>
			<label>Apellidos:</label> <span><jstl:out value="${alumno.apellidos}"></jstl:out></span><br>
			<label>Cursos repetidos:</label> <span><jstl:out value="${alumno.repiteCurso}" /></span> <br>
			<label>Pendiente:</label><span> <jstl:out value="${alumno.pendiente}" /></span>
		</div>
	</div>
<div class="col-lg-offset-6"></div>

<div class="row col-lg-12 col-md-12 col-sm-12 col-xs-12" style="margin-bottom: -15px;">
	<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12">
		<!-- Comienzo Bloque pestañas trabajo -->
		<spring:message var="label" code='evaluar.trabajos' />
		<p class="col-lg-12 col-md-12 col-sm-12 col-xs-12"><b><jstl:out value="${label}"  /></b></p>
		<div id="panel"  class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<ul id="tabs1">
		    	<li id="tab_01"><a onclick="tab('tab_01','panel_01','1');">1ª Evaluación</a></li>
		        <li id="tab_02"><a onclick="tab('tab_02','panel_02','1');">2ª Evaluación</a></li>
		        <li id="tab_03"><a onclick="tab('tab_03','panel_03','1');">3ª Evaluación</a></li>
		    </ul>
			<div id="paneles1">
				<div id="panel_01">
					<table class="table table-hover table-compresed">
						<tr class="success">
							<th class="col-lg-6">Título</th>
							<th colspan="2" class="col-lg-6">Calificación</th>
						</tr>
						<!-- Attributes -->
						<jstl:forEach items="${items}" var="item">
		    				<jstl:if test="${ item.evaluacion.indicador==1}">
		    					<jstl:if test="${item.getClass().getSimpleName() == 'Trabajo'}">
		    						<form action="app/profesor/alumno/trabajo/${alumno.id}/${item.id}/save.do" method="post">

			        					<!-- code here -->
			        					<tr>
			        						<td>
			        							<jstl:out value="${item.titulo}"></jstl:out>
			        						</td>
			        						<td class="col-lg-4">
			        							<input maxlength="4" name="nota" type="text"  
			        							style="width: 3em;text-align: center;" 
			        							value="<jstl:out value="${item.calificacion}"/>">
			        						</td>
			        						<td>
				        						<button class="btn btn-success" type="submit" value="">
				        							<i class="fa fa-floppy-o"></i>
				        						</button>
				        					</td>
			        					</tr>
		        					</form>
		        				</jstl:if>
		    				</jstl:if>
						</jstl:forEach>
					</table>
				</div>
				<div id="panel_02">
					<table class="table table-hover table-compresed">
						<tr class="success">
							<th class="col-lg-6">Título</th>
							<th colspan="2" class="col-lg-6">Calificación</th>
						</tr>
						<!-- Attributes -->
						<jstl:forEach items="${items}" var="item">
		    				<jstl:if test="${ item.evaluacion.indicador==2}">
		    					<jstl:if test="${item.getClass().getSimpleName() == 'Trabajo'}">
		    						<form action="app/profesor/alumno/trabajo/${alumno.id}/${item.id}/save.do" method="post">

			        					<!-- code here -->
			        					<tr>
			        						<td>
			        							<jstl:out value="${item.titulo}"></jstl:out>
			        						</td>
			        						<td class="col-lg-4">
			        							<input maxlength="4" name="nota" type="text"  
			        							style="width: 3em;text-align: center;" 
			        							value="<jstl:out value="${item.calificacion}"/>">
			        						</td>
			        						<td>
				        						<button class="btn btn-success" type="submit" value="">
				        							<i class="fa fa-floppy-o"></i>
				        						</button>
				        					</td>
			        					</tr>
		        					</form>
		        				</jstl:if>
		    				</jstl:if>
						</jstl:forEach>
					</table>
				</div>
				<div id="panel_03">
					<table class="table table-hover table-compresed">
						<tr class="success">
							<th class="col-lg-6">Título</th>
							<th colspan="2" class="col-lg-6">Calificación</th>
						</tr>
						<!-- Attributes -->
						<jstl:forEach items="${items}" var="item">
		    				<jstl:if test="${ item.evaluacion.indicador==3}">
		    					<jstl:if test="${item.getClass().getSimpleName() == 'Trabajo'}">
		    						<form action="app/profesor/alumno/trabajo/${alumno.id}/${item.id}/save.do" method="post">
		        						<!-- code here -->
			        					<tr>
			        						<td>
			        							<jstl:out value="${item.titulo}"></jstl:out>
			        						</td>
			        						<td class="col-lg-4">
			        							<input class="form-control" maxlength="4" name="nota" type="text"  
			        							style="text-align: center;" 
			        							value="<jstl:out value="${item.calificacion}"/>">
			        						</td>
			        						<td>
				        						<button class="btn btn-success" type="submit" value="">
				        							<i class="fa fa-floppy-o"></i>
				        						</button>
				        					</td>
			        					</tr>
		        					</form>
		        				</jstl:if>
		    				</jstl:if>
						</jstl:forEach>
					</table>
				</div>
			</div>
		</div>	
	<!-- Fin Bloque pestañas trabajo -->
	</div>
	<!-- Bloque de pestañas para actividades -->
	<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12">
	<spring:message var="label" code='evaluar.actividades'/>
	<p class="col-lg-12 col-md-12 col-sm-12 col-xs-12"><b><jstl:out value="${label}" /></b></p>
	<div id="panel"  class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<ul id="tabs4">
	    	<li id="tab_31"><a onclick="tab('tab_31','panel_31','4');">1ª Evaluación</a></li>
	        <li id="tab_32"><a onclick="tab('tab_32','panel_32','4');">2ª Evaluación</a></li>
	        <li id="tab_33"><a onclick="tab('tab_33','panel_33','4');">3ª Evaluación</a></li>
	    </ul>
		<div id="paneles4">
			<div id="panel_31">
				<table class="table table-hover table-compresed">
					<tr class="success">
						<th class="col-lg-6">Título</th>
						<th colspan="2" class="col-lg-6">Calificación</th>
					</tr>
					<!-- Attributes -->
					<jstl:forEach items="${items}" var="item">
	    				<jstl:if test="${ item.evaluacion.indicador==1}">
	    					<jstl:if test="${item.getClass().getSimpleName() == 'Actividad'}">
	    						<form action="app/profesor/alumno/actividad/${alumno.id}/${item.id}/save.do" method="post">

		        					<!-- code here -->
		        					<tr>
		        						<td>
		        							<jstl:out value="${item.titulo}"></jstl:out>
		        						</td>
		        						<td class="col-lg-4">
		        							<input class="form-control" maxlength="4" name="nota" type="text"  
		        							style="text-align: center;" 
		        							value="<jstl:out value="${item.calificacion}"/>">
		        						</td>
		        						<td>
			        						<button class="btn btn-success" type="submit" value="">
			        							<i class="fa fa-floppy-o"></i>
			        						</button>
			        					</td>
		        					</tr>
	        					</form>
	        				</jstl:if>
	    				</jstl:if>
					</jstl:forEach>
				</table>
			</div>
			<div id="panel_32">
				<table class="table table-hover table-compresed">
					<tr class="success">
						<th class="col-lg-6 ">Título</th>
						<th colspan="2" class="col-lg-6">Calificación</th>
					</tr>
					<!-- Attributes -->
					<jstl:forEach items="${items}" var="item">
	    				<jstl:if test="${ item.evaluacion.indicador==2}">
	    					<jstl:if test="${item.getClass().getSimpleName() == 'Actividad'}">
	    						<form action="app/profesor/alumno/actividad/${alumno.id}/${item.id}/save.do" method="post">

		        					<!-- code here -->
		        					<tr>
		        						<td>
		        							<jstl:out value="${item.titulo}"></jstl:out>
		        						</td>
		        						<td class="col-lg-4">
		        							<input class="form-control" maxlength="4" name="nota" type="text"  
		        							style="text-align: center;" 
		        							value="<jstl:out value="${item.calificacion}"/>">
		        						</td>
		        						<td>
			        						<button  class="btn btn-success" type="submit" value="">
			        							<i class="fa fa-floppy-o"></i>
			        						</button>
			        					</td>
		        					</tr>
	        					</form>
	        				</jstl:if>
	    				</jstl:if>
					</jstl:forEach>
				</table>
			</div>
			<div id="panel_33">
				<table class="table table-hover table-compresed">
					<tr class="success">
						<th class="col-lg-6">Título</th>
						<th colspan="2" class="col-lg-6">Calificación</th>
					</tr>
					<!-- Attributes -->
					<jstl:forEach items="${items}" var="item">
	    				<jstl:if test="${ item.evaluacion.indicador==3}">
	    					<jstl:if test="${item.getClass().getSimpleName() == 'Actividad'}">
	    						<form action="app/profesor/alumno/actividad/${alumno.id}/${item.id}/save.do" method="post">

	        						<!-- code here -->
		        					<tr>
		        						<td>
		        							<label><jstl:out value="${item.titulo}"></jstl:out></label>
		        						</td>
		        						<td class="col-lg-4">
		        							<input class="form-control" maxlength="4" name="nota" type="text"  
		        							style="text-align: center;" 
		        							value="<jstl:out value="${item.calificacion}"/>">
		        						</td>
		        						<td>
			        						<button class="btn btn-success" type="submit" value="">
			        							<i class="fa fa-floppy-o"></i>
			        						</button>
			        					</td>
		        					</tr>
	        					</form>
	        				</jstl:if>
	    				</jstl:if>
					</jstl:forEach>
				</table>
			</div>
		</div>
	</div>	
	<!-- Fin bloque para actividades -->
</div>
</div>

<div class="row col-lg-12 col-md-12 col-sm-12 col-xs-12">
	<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12">

	<!-- Comienzo bloque para controles -->
	<p style="" class="col-lg-12 col-md-12 col-sm-12 col-xs-12"><b>Controles</b></p>
	
		<!-- Segunda grupo de pestañas -->
		
		<div id="panel" class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		
		<ul id="tabs2">
	    	<li id="tab_11"><a onclick="tab('tab_11','panel_11','2');">1ª Evaluación</a></li>
	        <li id="tab_12"><a onclick="tab('tab_12','panel_12','2');">2ª Evaluación</a></li>
	        <li id="tab_13"><a onclick="tab('tab_13','panel_13','2');">3ª Evaluación</a></li>
	    </ul>
		<div id="paneles2" style="">
			<div id="panel_11">
				<table class="table table-hover table-compresed">
					<tr class="success">
						<th class="col-lg-6">Título</th>
						<th colspan="2" class="col-lg-6">Calificación</th>
					</tr>
					<!-- Attributes -->
					<jstl:forEach items="${items}" var="item">
					<form action="app/profesor/alumno/examen/${alumno.id}/${item.id}/save.do">

	    				<jstl:if test="${ item.evaluacion.indicador==1}">
	    					<jstl:if test="${item.getClass().getSimpleName() == 'Examen'}">
		        				<!-- code here -->
		        				<tr>
		        					<td>
		        						<jstl:out value="${item.titulo}"></jstl:out>
		        					</td>
		        					<td class="col-lg-4">
		        						<input class="form-control" maxlength="4" name="nota" type="text"  
		        						style="text-align: center;"
		        						 value="<jstl:out value="${item.calificacion}"/>">
		        					</td>
		        					<td>
		        						<button class="btn btn-success" type="submit" value="">
		        							<i class="fa fa-floppy-o"></i>
		        						</button>
		        					</td>
		        				</tr>
	        				</jstl:if>
	    				</jstl:if>
	    				</form>
					</jstl:forEach>
				</table>
			</div>
			<div id="panel_12">
				<table class="table table-hover table-compresed">
					<tr class="success">
						<th class="col-lg-6">Título</th>
						<th colspan="2" class="col-lg-6">Calificación</th>
					</tr>
					<!-- Attributes -->
					<jstl:forEach items="${items}" var="item">
					<form method="POST" action="app/profesor/alumno/examen/${alumno.id}/${item.id}/save.do">

	    				<jstl:if test="${ item.evaluacion.indicador==2}">
	    					<jstl:if test="${item.getClass().getSimpleName() == 'Examen'}">
		        				<!-- code here -->
		        				<tr>
		        					<td>
		        						<jstl:out value="${item.titulo}"></jstl:out>
		        					</td>
		        					<td class="col-lg-4">
		        						<input class="form-control" maxlength="4" name="nota" type="text"  
		        						style="text-align: center;"
		        						 value="<jstl:out value="${item.calificacion}"/>">
		        					</td>
		        					<td>
		        						<button class="btn btn-success" type="submit" value="">
		        							<i class="fa fa-floppy-o"></i>
		        						</button>
		        					</td>
		        				</tr>
		        			</jstl:if>
	    				</jstl:if>
	    				</form>
					</jstl:forEach>
				</table>
			</div>
			<div id="panel_13">
				<table class="table table-hover table-compresed">
					<tr class="success">
						<th class="col-lg-6">Título</th>
						<th colspan="2" class="col-lg-6">Calificación</th>
					</tr>
					<!-- Attributes -->
					<jstl:forEach items="${items}" var="item">
						<form method="POST" action="app/profesor/alumno/examen/${alumno.id}/${item.id}/save.do">

		    				<jstl:if test="${ item.evaluacion.indicador==3}">
		    					<jstl:if test="${item.getClass().getSimpleName() == 'Examen'}">
			        				<!-- code here -->
			        				<tr>
			        					<td>
			        						<jstl:out value="${item.titulo}"></jstl:out>
			        					</td>
			        					<td class="col-lg-4">
			        						<input class="form-control" maxlength="4" name="nota" type="text"  
			        						style="text-align: center;"
			        						 value="<jstl:out value="${item.calificacion}"/>">
			        					</td>
			        					<td>
			        						<button class="btn btn-success" type="submit" value="">
			        							<i class="fa fa-floppy-o"></i>
			        						</button>
			        					</td>
			        				</tr>
		        				</jstl:if>
		    				</jstl:if>
		    			</form>
					</jstl:forEach>
				</table>
			</div>
		</div>
	</div>
	<!-- Fin de bloque para controles -->
	</div>
	<!-- Comienzo de bloque para evaluacion -->
	<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12">
		<p style="" class="col-lg-12 col-md-12 col-sm-12 col-xs-12"><b>Evaluación Global</b></p>
		<div id="panel" class="col-lg-12 col-md-12 col-sm-12 col-xs-12" >
			
			<ul id="tabs3">
		    	<li id="tab_21"><a onclick="tab('tab_21','panel_21','3');">1ª Evaluación</a></li>
		        <li id="tab_22"><a onclick="tab('tab_22','panel_22','3');">2ª Evaluación</a></li>
		        <li id="tab_23"><a onclick="tab('tab_23','panel_23','3');">3ª Evaluación</a></li>
		    </ul>
			<div id="paneles3">
				<div id="panel_21">
					<form:form modelAttribute="nota1" action="app/profesor/alumno/guardarNota.do">
						<form:hidden path="id"/>
						<form:hidden path="version"/>
						<form:hidden path="alumno"/>
						<form:hidden path="evaluacion"/>
						<form:hidden path="asignatura"/>
						<table class="table table-hover table-compresed" style="margin: 0px 0px 0px 0px;">
							<tr class="success">
								<th class="col-lg-5">
									<label class="form-label">Nota</label>
								</th>
								<th class="col-lg-5">
									<form:input cssClass="form-control" path="notaFinal" maxlength="4" size="4"/>
			        			</th>
			        			<td class="col-lg-2">
			        				<input class="btn btn-success" type="submit" name="save" value="Guardar">
			        			</td>
							</tr>			
						</table>
					</form:form>
				</div>
				<div id="panel_22">
					<form:form modelAttribute="nota2" action="app/profesor/alumno/guardarNota.do">
						<form:hidden path="id"/>
						<form:hidden path="version"/>
						<form:hidden path="alumno"/>
						<form:hidden path="evaluacion"/>
						<form:hidden path="asignatura"/>
						<table class="table table-hover table-compresed" style="margin: 0px 0px 0px 0px;">
							<tr class="success">
								<th class="col-lg-5">
									<label class="form-label">Nota</label>
								</th>
								<th class="col-lg-5">
									<form:input cssClass="form-control" path="notaFinal" maxlength="4" size="4"/>
			        			</th>
			        			<td class="col-lg-2">
			        				<input class="btn btn-success" type="submit" name="save" value="Guardar">
			        			</td>
							</tr>			
						</table>
					</form:form>
				</div>
				<div id="panel_23">
					<form:form modelAttribute="nota3"  action="app/profesor/alumno/guardarNota.do">
						<form:hidden path="id"/>
						<form:hidden path="version"/>
						<form:hidden path="alumno"/>
						<form:hidden path="evaluacion"/>
						<form:hidden path="asignatura"/>
						<table class="table table-hover table-compresed" style="margin: 0px 0px 0px 0px;">
							<tr class="success">
								<th class="col-lg-5">
									<label class="form-label">Nota</label>
								</th>
								<th class="col-lg-5">
									<form:input cssClass="form-control"  path="notaFinal" maxlength="4" size="4"/>
			        			</th>
			        			<td class="col-lg-2">
			        				<input class="btn btn-success" type="submit" name="save" value="Guardar">
			        			</td>
							</tr>			
						</table>
					</form:form>
				</div>
			</div>
		</div>
	</div>
	<!-- Fin bloque evaluacion -->
</div>

	<script type="text/javascript">
		tab('tab_01','panel_01','1');
		tab('tab_11','panel_11','2');
		tab('tab_21','panel_21','3');
		tab('tab_31','panel_31','4');
		
		
	function enviar_formulario()
	{
		$.ajax({
			  url: "test.html",
			  context: document.body
			}).done(function() {
			  $( this ).addClass( "done" );
			});
	}
		
	</script>

