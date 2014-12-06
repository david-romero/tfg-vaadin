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
<div id="alumnoInfo">
	<img src="rest/profesor/alumno/foto.do?alumnoId=${alumno.id}" alt="Firma">
	<span>Nombre: <p><jstl:out value="${alumno.nombre}"></jstl:out></p></span>
	<span>Apellidos: <p><jstl:out value="${alumno.apellidos}"></jstl:out></p></span>
	<br>
	<span>Cursos repetidos: <jstl:out value="${alumno.repiteCurso}" /></span> 
	<span style="margin-left:36px;">Pendiente: <jstl:out value="${alumno.pendiente}" /></span>
</div>

<br>
<br>
<!-- Comienzo Bloque pestañas trabajo -->
<spring:message var="label" code='evaluar.trabajos' />
<jstl:out value="${label}"  />
<div id="panel" style="margin-top: 35px;">
	<ul id="tabs1">
    	<li id="tab_01"><a onclick="tab('tab_01','panel_01','1');">1ª Evaluación</a></li>
        <li id="tab_02"><a onclick="tab('tab_02','panel_02','1');">2ª Evaluación</a></li>
        <li id="tab_03"><a onclick="tab('tab_03','panel_03','1');">3ª Evaluación</a></li>
    </ul>
	<div id="paneles1">
		<div id="panel_01">
			<table>
				<tr>
					<th>Título</th>
					<th>Calificación</th>
				</tr>
				<!-- Attributes -->
				<jstl:forEach items="${items}" var="item">
    				<jstl:if test="${ item.evaluacion.indicador==1}">
    					<jstl:if test="${item.getClass().name == 'Trabajo'}">
        					<!-- code here -->
        					<tr>
        						<td>
        							<jstl:out value="${item.titulo}"></jstl:out>
        						</td>
        						<td>
        							<input maxlength="4" name="nota" type="text"  
        							style="width: 3em;text-align: center;" 
        							value="<jstl:out value="${item.calificacion}"/>">
        						</td>
        					</tr>
        				</jstl:if>
    				</jstl:if>
				</jstl:forEach>
			</table>
		</div>
		<div id="panel_02">
			<table>
				<tr>
					<th>Título</th>
					<th>Calificación</th>
				</tr>
				<!-- Attributes -->
				<jstl:forEach items="${items}" var="item">
    				<jstl:if test="${ item.evaluacion.indicador==2}">
    					<jstl:if test="${item.getClass().name == 'Trabajo'}">
        					<!-- code here -->
        					<tr>
        						<td>
        							<jstl:out value="${item.titulo}"></jstl:out>
        						</td>
        						<td>
        							<input maxlength="4" name="nota" type="text"  
        							style="width: 3em;text-align: center;" 
        							value="<jstl:out value="${item.calificacion}"/>">
        						</td>
        					</tr>
        				</jstl:if>
    				</jstl:if>
				</jstl:forEach>
			</table>
		</div>
		<div id="panel_03">
			<table>
				<tr>
					<th>Título</th>
					<th>Calificación</th>
				</tr>
				<!-- Attributes -->
				<jstl:forEach items="${items}" var="item">
    				<jstl:if test="${ item.evaluacion.indicador==3}">
    					<jstl:if test="${item.getClass().name == 'Trabajo'}">
        					<!-- code here -->
        					<tr>
        						<td>
        							<jstl:out value="${item.titulo}"></jstl:out>
        						</td>
        						<td>
        							<input maxlength="4" name="nota" type="text"  
        							style="width: 3em;text-align: center;" 
        							value="<jstl:out value="${item.calificacion}"/>">
        						</td>
        					</tr>
        				</jstl:if>
    				</jstl:if>
				</jstl:forEach>
			</table>
		</div>
	</div>
</div>	
<!-- Fin Bloque pestañas trabajo -->
<!-- Bloque de pestañas para actividades -->
<spring:message var="label" code='evaluar.actividades'/>
<jstl:out value="${label}" />
<div id="panel" style="margin-top:13px;">
	<ul id="tabs4">
    	<li id="tab_31"><a onclick="tab('tab_31','panel_31','4');">1ª Evaluación</a></li>
        <li id="tab_32"><a onclick="tab('tab_32','panel_32','4');">2ª Evaluación</a></li>
        <li id="tab_33"><a onclick="tab('tab_33','panel_33','4');">3ª Evaluación</a></li>
    </ul>
	<div id="paneles4">
		<div id="panel_31">
			<table>
				<tr>
					<th>Título</th>
					<th>Calificación</th>
				</tr>
				<!-- Attributes -->
				<jstl:forEach items="${items}" var="item">
    				<jstl:if test="${ item.evaluacion.indicador==1}">
    					<jstl:if test="${item.getClass().name == 'Actividad'}">
        					<!-- code here -->
        					<tr>
        						<td>
        							<jstl:out value="${item.titulo}"></jstl:out>
        						</td>
        						<td>
        							<input maxlength="4" name="nota" type="text"  
        							style="width: 3em;text-align: center;" 
        							value="<jstl:out value="${item.calificacion}"/>">
        						</td>
        					</tr>
        				</jstl:if>
    				</jstl:if>
				</jstl:forEach>
			</table>
		</div>
		<div id="panel_32">
			<table>
				<tr>
					<th>Título</th>
					<th>Calificación</th>
				</tr>
				<!-- Attributes -->
				<jstl:forEach items="${items}" var="item">
    				<jstl:if test="${ item.evaluacion.indicador==2}">
    					<jstl:if test="${item.getClass().name == 'Actividad'}">
        					<!-- code here -->
        					<tr>
        						<td>
        							<jstl:out value="${item.titulo}"></jstl:out>
        						</td>
        						<td>
        							<input maxlength="4" name="nota" type="text"  
        							style="width: 3em;text-align: center;" 
        							value="<jstl:out value="${item.calificacion}"/>">
        						</td>
        					</tr>
        				</jstl:if>
    				</jstl:if>
				</jstl:forEach>
			</table>
		</div>
		<div id="panel_33">
			<table>
				<tr>
					<th>Título</th>
					<th>Calificación</th>
				</tr>
				<!-- Attributes -->
				<jstl:forEach items="${items}" var="item">
    				<jstl:if test="${ item.evaluacion.indicador==3}">
    					<jstl:if test="${item.getClass().name == 'Actividad'}">
        					<!-- code here -->
        					<tr>
        						<td>
        							<jstl:out value="${item.titulo}"></jstl:out>
        						</td>
        						<td>
        							<input maxlength="4" name="nota" type="text"  
        							style="width: 3em;text-align: center;" 
        							value="<jstl:out value="${item.calificacion}"/>">
        						</td>
        					</tr>
        				</jstl:if>
    				</jstl:if>
				</jstl:forEach>
			</table>
		</div>
	</div>
</div>	
<!-- Fin bloque para actividades -->
<!-- Comienzo bloque para controles -->
<p style="position: absolute;left: 52.4%;top: 31.5%;">Controles</p>

	<!-- Segunda grupo de pestañas -->
	
	<div id="panel" style="position: absolute;left: 52.4%;top: 37%;">
	
	<ul id="tabs2">
    	<li id="tab_11"><a onclick="tab('tab_11','panel_11','2');">1ª Evaluación</a></li>
        <li id="tab_12"><a onclick="tab('tab_12','panel_12','2');">2ª Evaluación</a></li>
        <li id="tab_13"><a onclick="tab('tab_13','panel_13','2');">3ª Evaluación</a></li>
    </ul>
	<div id="paneles2" style="width: 400px;">
		<div id="panel_11" style="width: 385px;">
			<table>
				<tr>
					<th>Título</th>
					<th>Calificación</th>
				</tr>
				<!-- Attributes -->
				<jstl:forEach items="${items}" var="item">
				<form action="app/profesor/alumno/examen/${alumno.id}/${item.id}/save.do">
    				<jstl:if test="${ item.evaluacion.indicador==1}">
    					<jstl:if test="${item.getClass().name == 'Examen'}">
	        				<!-- code here -->
	        				<tr>
	        					<td>
	        						<jstl:out value="${item.titulo}"></jstl:out>
	        					</td>
	        					<td>
	        						<input maxlength="4" name="nota" type="text"  
	        						style="width: 3em;text-align: center;"
	        						 value="<jstl:out value="${item.calificacion}"/>">
	        					</td>
	        					<td>
	        						<input type="submit" value="Guardar">
	        					</td>
	        				</tr>
        				</jstl:if>
    				</jstl:if>
    				</form>
				</jstl:forEach>
			</table>
		</div>
		<div id="panel_12" style="width: 385px;">
			<table>
				<tr>
					<th>Título</th>
					<th>Calificación</th>
				</tr>
				<!-- Attributes -->
				<jstl:forEach items="${items}" var="item">
				<form method="POST" action="app/profesor/alumno/examen/${alumno.id}/${item.id}/save.do">
					<input type="hidden" name="id" value="${item.id}">
					<input type="hidden" name="titulo" value="${item.titulo}">
					<input type="hidden" name="evaluacion" value="${item.evaluacion}">
					<input type="hidden" name="fecha" value="${item.fecha}">
					<input type="hidden" name="asignatura" value="${item.asignatura}">
    				<jstl:if test="${ item.evaluacion.indicador==2}">
    					<jstl:if test="${item.getClass().name == 'Examen'}">
	        				<!-- code here -->
	        				<tr>
	        					<td>
	        						<jstl:out value="${item.titulo}"></jstl:out>
	        					</td>
	        					<td>
	        						<input maxlength="4" name="nota" type="text"  
	        						style="width: 3em;text-align: center;"
	        						 value="<jstl:out value="${item.calificacion}"/>">
	        					</td>
	        					<td>
	        						<input type="submit" value="Guardar">
	        					</td>
	        				</tr>
	        			</jstl:if>
    				</jstl:if>
    				</form>
				</jstl:forEach>
			</table>
		</div>
		<div id="panel_13" style="width: 385px;">
			<table>
				<tr>
					<th>Título</th>
					<th>Calificación</th>
				</tr>
				<!-- Attributes -->
				<jstl:forEach items="${items}" var="item">
				<form method="POST" action="app/profesor/alumno/examen/${alumno.id}/${item.id}/save.do">
					<input type="hidden" name="id" value="${item.id}">
					<input type="hidden" name="titulo" value="${item.titulo}">
					<input type="hidden" name="evaluacion" value="${item.evaluacion}">
					<input type="hidden" name="fecha" value="${item.fecha}">
					<input type="hidden" name="asignatura" value="${item.asignatura}">
    				<jstl:if test="${ item.evaluacion.indicador==3}">
    					<jstl:if test="${item.getClass().name == 'Examen'}">
	        				<!-- code here -->
	        				<tr>
	        					<td>
	        						<jstl:out value="${item.titulo}"></jstl:out>
	        					</td>
	        					<td>
	        						<input maxlength="4" name="nota" type="text"  
	        						style="width: 3em;text-align: center;"
	        						 value="<jstl:out value="${item.calificacion}"/>">
	        					</td>
	        					<td>
	        						<input type="submit" value="Guardar">
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
<!-- Comienzo de bloque para evaluacion -->
<p style="position: absolute;left: 52.4%;top: 60.75%;">Evaluación Global</p>
<div id="panel" style="position: absolute;left: 52.4%;top: 67.35%;">
	
	<ul id="tabs3">
    	<li id="tab_21"><a onclick="tab('tab_21','panel_21','3');">1ª Evaluación</a></li>
        <li id="tab_22"><a onclick="tab('tab_22','panel_22','3');">2ª Evaluación</a></li>
        <li id="tab_23"><a onclick="tab('tab_23','panel_23','3');">3ª Evaluación</a></li>
    </ul>
	<div id="paneles3" style="width: 400px;">
		<div id="panel_21" style="width: 385px;">
			<form:form modelAttribute="nota1" action="app/profesor/alumno/guardarNota.do">
				<form:hidden path="id"/>
				<form:hidden path="version"/>
				<form:hidden path="alumno"/>
				<form:hidden path="evaluacion"/>
				<form:hidden path="asignatura"/>
				<table style="margin: 0px 0px 0px 0px;">
					<tr>
						<th>
							Nota
						</th>
						<th>
							<form:input path="notaFinal" maxlength="4" size="4"/>
	        			</th>
	        			<td>
	        				<input type="submit" name="save" value="Guardar">
	        			</td>
					</tr>			
				</table>
			</form:form>
		</div>
		<div id="panel_22" style="width: 385px;">
			<form:form modelAttribute="nota2" action="app/profesor/alumno/guardarNota.do">
				<form:hidden path="id"/>
				<form:hidden path="version"/>
				<form:hidden path="alumno"/>
				<form:hidden path="evaluacion"/>
				<form:hidden path="asignatura"/>
				<table style="margin: 0px 0px 0px 0px;">
					<tr>
						<th>
							Nota
						</th>
						<th>
							<form:input path="notaFinal" maxlength="4" size="4"/>
	        			</th>
	        			<td>
	        				<input type="submit" name="save" value="Guardar">
	        			</td>
					</tr>			
				</table>
			</form:form>
		</div>
		<div id="panel_23" style="width: 385px;">
			<form:form modelAttribute="nota3"  action="app/profesor/alumno/guardarNota.do">
				<form:hidden path="id"/>
				<form:hidden path="version"/>
				<form:hidden path="alumno"/>
				<form:hidden path="evaluacion"/>
				<form:hidden path="asignatura"/>
				<table style="margin: 0px 0px 0px 0px;">
					<tr>
						<th>
							Nota
						</th>
						<th>
							<form:input path="notaFinal" maxlength="4" size="4"/>
	        			</th>
	        			<td>
	        				<input type="submit" name="save" value="Guardar">
	        			</td>
					</tr>			
				</table>
			</form:form>
		</div>
	</div>
</div>
<!-- Fin bloque evaluacion -->

	<script type="text/javascript">
		tab('tab_01','panel_01','1');
		tab('tab_11','panel_11','2');
		tab('tab_21','panel_21','3');
		tab('tab_31','panel_31','4');
	</script>

