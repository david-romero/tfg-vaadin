<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<blockquote>
  <p>Notificaciones Enviadas.</p>
</blockquote>

<display:table requestURI="${requestURI}"  
	name="notificacionesEmitidas" pagesize="6" class="table table-hover" id="row" 
	defaultsort="1" defaultorder="ascending"  >
	
	<!-- Attributes -->
	<jstl:set var="now" value="${row.fecha}" />

	<spring:message code="notificacion.fecha" var="nameHeader" />
	<display:column class="col-lg-3" property="fecha" format="{0,date,dd/MM/yyyy HH:mm}"  title="${nameHeader}" sortable="true" headerClass="sortable" >
	
	</display:column>
	<spring:message code="notificacion.profesor" var="apellidosHeader" />
	<display:column class="col-lg-4" property="profesor" title="${apellidosHeader}" sortable="true" headerClass="sortable" >
		
	</display:column>
	

	<spring:message code="notificacion.content" var="fotoHeader" />
	<display:column class="col-lg-5" title="${fotoHeader}" sortable="false" property="contenido" >
		
	</display:column>
	

	
</display:table>


<blockquote>
  <p>Notificaciones Recibidas.</p>
</blockquote>

<display:table requestURI="${requestURI}"  
	name="notificacionesRecibidas" pagesize="6" class="table table-hover" id="row" 
	defaultsort="1" defaultorder="ascending"  >
	
	<!-- Attributes -->
	<jstl:set var="now" value="${row.fecha}" />

	<spring:message code="notificacion.fecha" var="nameHeader" />
	<display:column class="col-lg-3" property="fecha" format="{0,date,dd/MM/yyyy HH:mm}"  title="${nameHeader}" sortable="true" headerClass="sortable" >
	
	</display:column>
	<spring:message code="notificacion.profesor" var="apellidosHeader" />
	<display:column class="col-lg-4" property="profesor" title="${apellidosHeader}" sortable="true" headerClass="sortable" >
		
	</display:column>
	

	<spring:message code="notificacion.content" var="fotoHeader" />
	<display:column class="col-lg-4" title="${fotoHeader}" sortable="false" property="contenido" >
		
	</display:column>
	
	<display:column class="col-lg-1" title="" sortable="false" >
		<a title="Concertar cita" href="/appEducacional/app/tutor/profesor/concertarCita.do?profesorId=${row.profesor.id}"><i class="fa fa-calendar"></i></a>&nbsp;
		<a title="Enviar Notificacion" href="app/tutor/profesor/enviarNotificacion.do"><i class="fa fa-envelope" ></i></a>
	</display:column>
	
</display:table>

