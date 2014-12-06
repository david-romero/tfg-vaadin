<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<blockquote>
  <p>Peticiones de cita enviadas.</p>
</blockquote>

<display:table requestURI="${requestURI}"  
	name="notificacionesEmitidas" pagesize="6" class="table table-hover" id="row" 
	defaultsort="1" defaultorder="descending"  >
	
	<!-- Attributes -->

	<spring:message code="notificacion.fecha" var="nameHeader" />
	<display:column class="col-lg-2"  property="fecha" format="{0,date,dd/MM/yyyy HH:mm}"  title="${nameHeader}" sortable="true" headerClass="sortable" >
	
	</display:column>
	<spring:message  code="notificacion.padre" var="apellidosHeader" />
	<display:column class="col-lg-3" property="padreMadreOTutor" title="${apellidosHeader}" sortable="true" headerClass="sortable" >
		
	</display:column>
	

	<spring:message  code="notificacion.content" var="fotoHeader" />
	<display:column class="col-lg-4" title="${fotoHeader}" sortable="false" property="contenido" >
	
	<display:column style="text-align: center;"  class="col-lg-1" title="Profesor" sortable="false" >
		<jstl:choose>
			<jstl:when test="${row.confirmadoProfesor }">
				<i class="fa fa-check"></i>
			</jstl:when>
			<jstl:otherwise>
				<i class="fa fa-times"></i>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	<display:column style="text-align: center;" class="col-lg-2" title="¿Padre, Madre o Tutor?" sortable="false" >
		<jstl:choose>
			<jstl:when test="${row.confirmadoTutor }">
				<i class="fa fa-check"></i>
			</jstl:when>
			<jstl:otherwise>
				<i class="fa fa-times"></i>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
		
	</display:column>
	
	
	
</display:table>


<blockquote>
  <p>Peticiones de cita recibidas.</p>
</blockquote>

<display:table requestURI="${requestURI}"  
	name="notificacionesRecibidas" pagesize="6" class="table table-hover" id="row" 
	defaultsort="1" defaultorder="ascending"  >
	
	<!-- Attributes -->
	<jstl:set var="now" value="${row.fecha}" />

	<spring:message code="notificacion.fecha" var="nameHeader" />
	<display:column class="col-lg-2" property="fecha" format="{0,date,dd/MM/yyyy HH:mm}"  title="${nameHeader}" sortable="true" headerClass="sortable" >
	
	</display:column>
	<spring:message code="notificacion.padre" var="apellidosHeader" />
	<display:column class="col-lg-2" property="padreMadreOTutor" title="${apellidosHeader}" sortable="true" headerClass="sortable" >
		
	</display:column>
	

	<spring:message code="notificacion.content" var="fotoHeader" />
	<display:column class="col-lg-4" title="${fotoHeader}" sortable="false" property="contenido" >
		
	</display:column>
	
	<display:column class="col-lg-1" title="" sortable="false" >
		<a title="Enviar Notificacion" href="app/tutor/profesor/enviarNotificacion.do"><i class="fa fa-envelope" ></i></a>
	</display:column>
	
	<display:column style="text-align: center;"  class="col-lg-1" title="Profesor" sortable="false" >
		<jstl:choose>
			<jstl:when test="${row.confirmadoProfesor }">
				<i class="fa fa-check"></i>
			</jstl:when>
			<jstl:otherwise>
				<a title="Confirmar Cita" href="app/profesor/tutor/confirmarCita.do?cita=${row.id}"><i class="fa fa-times"></i></a>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	<display:column class="col-lg-2" title="¿Padre, Madre o Tutor?" sortable="false" >
		<jstl:choose>
			<jstl:when test="${row.confirmadoTutor }">
				<i class="fa fa-check"></i>
			</jstl:when>
			<jstl:otherwise>
				<i class="fa fa-times"></i>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	
</display:table>

