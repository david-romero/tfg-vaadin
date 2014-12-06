<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<security:authorize access="isAuthenticated()">
<!-- Collect the nav links, forms, and other content for toggling -->
<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" id="bs-example-navbar-collapse-1" style="padding-right: 0px;padding-left: 0px;">
<nav class="navbar navbar-default navbar-fixed-top" style="border-bottom-width: 0px;" role="navigation">
  <!-- El logotipo y el icono que despliega el menú se agrupan
       para mostrarlos mejor en los dispositivos móviles -->
  <div class="navbar-header" style="padding: 0px;">
    <button type="button" class="navbar-toggle" data-toggle="collapse"
            data-target=".navbar-ex1-collapse">
      <span class="sr-only">Desplegar navegación</span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
    </button>
    <a class="navbar-brand" href="/appEducacional/app/welcome/index.do"><img style="width: 80%;margin-top: -11px;" src="images/logo.png" alt="Ayudante Educacional" class="img-responsive" /></a>
  </div>
 
  <!-- Agrupar los enlaces de navegación, los formularios y cualquier
       otro elemento que se pueda ocultar al minimizar la barra -->
  <div id="menu-colapsador" class="collapse navbar-collapse navbar-ex1-collapse">
    <ul id="home-link" class="nav navbar-nav">
      <li style="margin-left: -5px;margin-right: -10px;"><a href="/appEducacional/app/welcome/index.do"><i style="line-height:  1.25;" class="fa fa-home"></i></a></li>
    </ul>
 
    <form class="navbar-form navbar-left" style="margin-right: -1px;" role="search">
      <div class="input-group" id="multiple-datasets">
      <input type="text" class="form-control typeahead">
      <div class="input-group-btn">
				<button style="height: 140%;" class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
			</div>
    </div> 
      
    </form> 
 
    <ul class="nav navbar-nav navbar-right">
      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
          <i class="fa fa-calendar"></i>&nbsp;&nbsp;<span>Calendario <b class="caret"></b></span>
        </a>
        <ul class="dropdown-menu">
        	<security:authorize access="hasRole('PROFESOR')">
	            <li><a href="app/profesor/verCalendario.do"><i class="fa fa-eye"></i>&nbsp;  Mi Calendario</a></li>
	            				<li><a href="app/profesor/evento/alta.do"><i class="fa fa-calendar"></i>&nbsp;  Crear evento</a></li>
	        </security:authorize>
	        <security:authorize access="hasRole('TUTOR')">
	            <li><a href="app/tutor/verCalendario.do"><i class="fa fa-eye"></i>&nbsp;  Mi Calendario</a></li>
	        </security:authorize>
        </ul>
      </li>
      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
          <i class="fa fa-cogs"></i>&nbsp;&nbsp;<span>Herramientas <b class="caret"></b></span>
        </a>
        <ul class="dropdown-menu">
        	<security:authorize access="hasRole('PROFESOR')">
	            <li><a href="app/profesor/alumno/realizarEvaluacion.do"><i class="fa fa-pencil-square-o"></i>&nbsp;  Realizar Evaluación</a></li>

				<li><a href="app/profesor/alumno/establecerNota.do" ><i class="fa fa-check-square-o"></i>&nbsp;  Establecer nota diaria</a></li>
				<li><a href="app/profesor/alumno/pasarLista.do" ><i class="fa fa-eye"></i>&nbsp;  Pasar Lista</a></li>

				
				<li><a href="app/profesor/asignatura/establecerCriterios.do"><i class="fa fa-cubes"></i>&nbsp;&nbsp;Establecer Criterios</a></li>
				<li><a href="app/profesor/item/showAll.do"><i class="fa fa-globe"></i>&nbsp;  Mis Items</a></li>
				<li><a href="app/profesor/item/justificarFalta.do"><i class="fa fa-legal"></i>&nbsp;  Justificar Falta de asistencia</a></li>
				<li><a href="app/profesor/evento/evaluar.do"><i class="fa fa-magic"></i>&nbsp;  Evaluar Evento</a></li>
			</security:authorize>
			<security:authorize access="hasRole('ADMINISTRADOR')">
				<li><a href="app/admin/banear.do"><i class="fa fa-ban"></i>&nbsp;  Banear usuarios</a></li>
				<li><a href="app/admin/addAdmin.do"><i class="fa fa-user"></i>&nbsp;  A&ntilde;adir administrador</a></li>
				<li><a href="app/admin/confirmUser.do"><i class="fa fa-check"></i>&nbsp;  Confirmar Identidad</a></li>
			</security:authorize>
			<security:authorize access="hasRole('TUTOR')">
				<li><a href="app/tutor/alumno/seleccionaTutorando.do?accion=justificarFalta"><i class="fa fa-legal"></i>&nbsp;  Justificar Falta de asistencia</a></li>
				
				<li><a href="app/tutor/alumno/vincular.do"><i class="fa fa-exchange"></i>&nbsp;&nbsp;Vincular Alumno</a></li>
			</security:authorize>
			
			
        </ul>
      </li>
     <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
          <i class="fa fa-envelope"></i>&nbsp;&nbsp;<span>Notificaciones <span id="contador_notificaciones" class="badge"></span> <b class="caret"></b></span>
        </a>
        <ul class="dropdown-menu">
          <!-- Profesor -->
          
            <security:authorize access="hasRole('TUTOR')">
            	<li><a href="app/tutor/verNotificaciones.do" class=""><i class="fa fa-eye"></i>&nbsp;Ver</a></li>
            	<li><a href="app/tutor/profesor/enviarNotificacion.do" class=""><i class="fa fa-paper-plane"></i>&nbsp;Enviar</a></li>
            	<li><a href="app/tutor/alumno/verProfesoresParaCita.do" class=""><i class="fa fa-calendar"></i>&nbsp;Concertar Cita</a></li>
            	<li><a href="app/tutor/verCitas.do" class=""><i class="fa fa-users"></i>&nbsp;Ver Citas</a></li>
            </security:authorize>
            <security:authorize access="hasRole('PROFESOR')">
				<li><a href="app/profesor/verNotificaciones.do" class=""><i class="fa fa-eye"></i>&nbsp;Ver</a></li>
				<li><a href="app/profesor/alumno/enviarNotificacion.do" class=""><i class="fa fa-paper-plane"></i>&nbsp;Enviar</a></li>
				<li><a href="app/profesor/alumno/verAlumnosParaCita.do" class=""><i class="fa fa-calendar"></i>&nbsp;Concertar Cita</a></li>
				<li><a href="app/profesor/verCitas.do" class=""><i class="fa fa-users"></i>&nbsp;Ver Citas</a></li>
			</security:authorize>
        </ul>
      </li>
      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
          <i class="fa fa-users"></i>&nbsp;&nbsp;<span>Alumnos <b class="caret"></b></span>
        </a>
        <ul class="dropdown-menu">
          <!-- Profesor -->
          	<security:authorize access="hasRole('PROFESOR')">
          			            <li><a href="app/profesor/alumno/alta.do"> <i class="fa fa-user">&nbsp;</i> Alta alumno</a></li>
				<li><a href="app/profesor/alumno/verAlumnos.do"><i class="fa fa-graduation-cap"></i>&nbsp;Ver ficha de alumno</a></li>
				<li><a style="cursor:pointer;" onclick="$('#myModal').modal('show');" ><i class="fa fa-upload"></i>&nbsp;&nbsp;Importar Alumnos</a></li>
				<li><a href="app/profesor/curso/estadisticasCurso.do" class=""><i class="fa fa-bar-chart-o"></i>&nbsp; Estadísticas por curso</a></li>
				<li><a href="app/profesor/alumno/verAlumnosParaEstadisticas.do"><i class="fa fa-bar-chart-o"></i>&nbsp;  Estadísticas por alumno</a></li>
			</security:authorize>
			<security:authorize access="hasRole('TUTOR')">
				<li><a href="app/tutor/alumno/seleccionaTutorando.do?accion=verFicha"><i class="fa fa-child"></i>&nbsp;  Tutorando</a></li>
				<li><a href="app/tutor/alumno/seleccionaTutorando.do?accion=estadisticas"><i class="fa fa-bar-chart-o"></i>&nbsp;  Estadísticas</a></li>
				<li><a href="app/tutor/alumno/alta.do"> <i class="fa fa-user">&nbsp;</i> Alta tutorando</a></li>
			</security:authorize>
        </ul>
      </li>
      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
          <i class="fa fa-user"></i> <b class="caret"></b>
        </a>
        <ul class="dropdown-menu">
          <security:authorize access="hasRole('PROFESOR')">
          	<li><a href="app/profesor/verMiPerfil.do"><i class="fa fa-user"></i>&nbsp;Mi perfil</a></li>
          	<li><a href="app/profesor/cambiarClave.do"><i class="fa fa-key"></i>&nbsp;Cambiar contrase&ntilde;a</a></li>
          </security:authorize>
          <security:authorize access="hasRole('TUTOR')">
            <li><a href="app/tutor/verMiPerfil.do"><i class="fa fa-user"></i>&nbsp;Mi perfil</a></li>
            <li><a href="app/tutor/cambiarClave.do"><i class="fa fa-key"></i>&nbsp;Cambiar contrase&ntilde;a</a></li>
          </security:authorize>
          <li class="divider"></li>
          <li><a href="app/bug/enviar.do"><i style="" class="fa fa-bug"></i> Reporte de Fallo</a></li>
          <li class="divider"></li>
          <li><a href="<jstl:url value='j_spring_security_logout' />"><i style="" class="fa fa-sign-out"></i> Salir</a></li>
        </ul>
      </li>
    </ul>
  </div>
</nav>
	<!--  <ul id="cbp-tm-menu" class="cbp-tm-menu col-lg-12 col-md-3 col-sm-3 col-xs-3">
				<li>
					<a href="app/welcome/index.do">Inicio</a>
				</li>
				<security:authorize access="isAuthenticated()">
				<li>
					<a href="#">Herramientas</a>
					<ul class="cbp-tm-submenu">
						<li><a href="app/profesor/alumno/realizarEvaluacion.do" class="cbp-tm-icon-archive">Realizar Evaluación</a></li>
						<li><a href="app/profesor/alumno/alta.do" class="cbp-tm-icon-cog">Alta alumno</a></li>
						<li><a href="app/profesor/alumno/establecerNota.do" class="cbp-tm-icon-location">Establecer nota diaria</a></li>
						<li><a href="app/profesor/alumno/pasarLista.do" class="cbp-tm-icon-users">Pasar Lista</a></li>
						<li><a href="#" class="cbp-tm-icon-earth">Justificar Falta de asistencia</a></li>
						<li><a href="#" class="cbp-tm-icon-location">Crear evento</a></li>
						<!--  <li><a href="#" class="cbp-tm-icon-mobile">Sea lettuce</a></li>-->
					<!--</ul>
				</li>
				</security:authorize>
				<security:authorize access="isAuthenticated()">
				<li>
					<a href="#">Notificaciones</a>
					<ul class="cbp-tm-submenu">
					<!-- Profesor -->
						<!--<li><a href="#" class="cbp-tm-icon-archive">Ver</a></li>
						<li><a href="app/profesor/alumno/enviarNotificacion.do" class="cbp-tm-icon-cog">Enviar</a></li>
						<li><a href="#" class="cbp-tm-icon-link">Mostrar Examen</a></li>
						<security:authorize access="hasRole('PROFESOR')">
							<li><a href="app/profesor/alumno/verAlumnosParaCita.do" class="cbp-tm-icon-clock">Concertar Cita</a></li>
						</security:authorize>
						<security:authorize access="hasRole('TUTOR')">
							<li><a href="app/tutor/alumno/verProfesoresParaCita.do" class="cbp-tm-icon-clock">Concertar Cita</a></li>
						</security:authorize>
						<!--  <li><a href="#" class="cbp-tm-icon-earth">Garlic mint</a></li>
						<li><a href="#" class="cbp-tm-icon-location">Zucchini garnish</a></li>
						<li><a href="#" class="cbp-tm-icon-mobile">Sea lettuce</a></li>-->
					<!--</ul>
				</li>
				</security:authorize>
				<security:authorize access="isAuthenticated()">
				<li>
					<a href="#">Datos</a>
					<ul class="cbp-tm-submenu">
					<!-- Profesor -->
						<!--<li><a href="app/profesor/alumno/verAlumnos.do" class="cbp-tm-icon-screen">Alumno</a></li>
						<li><a href="#" class="cbp-tm-icon-mail">Estadísticas por curso</a></li>
						<li><a href="#" class="cbp-tm-icon-contract">Mostrar datos personales</a></li>
						<li><a href="#" class="cbp-tm-icon-pencil">Modificar datos personales</a></li>
						<!--  <li><a href="#" class="cbp-tm-icon-article">Onion endive</a></li>
						<li><a href="#" class="cbp-tm-icon-clock">Bitterleaf</a></li>-->
					<!--</ul>
				</li>
				</security:authorize>
				<security:authorize access="isAuthenticated()">
				<li>
					<a href="<jstl:url value='j_spring_security_logout' />">Salir</a>
				</li>
				</security:authorize>
			</ul>-->
</div>
</security:authorize>

