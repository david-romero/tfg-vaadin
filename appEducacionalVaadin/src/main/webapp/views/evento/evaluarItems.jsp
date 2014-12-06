<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<div class="col-lg-12 col-md-12 col-xs-12 col-sm-12">
	<div class="col-lg-12 col-md-12 col-xs-12 col-sm-12" >
    		<div style="margin-top: 20px;" class="col-lg-4 col-md-4 col-xs-4 col-sm-4">
    			<b>Item Evaluable</b>
    		</div>
    		<div style="margin-top: 20px;" class="col-lg-2 col-md-2 col-xs-2 col-sm-2">
    			<b>Apellidos</b>
    		</div>
    		<div style="margin-top: 20px;" class="col-lg-2 col-md-2 col-xs-2 col-sm-2">
    			<b>Curso</b>
    		</div>
    		<div style="margin-top: 20px;" class="col-lg-1 col-md-1 col-xs-1 col-sm-1">
    			<b>Fotograf&iacute;a</b>
    		</div>
    		<div style="margin-top: 20px;" class="col-lg-2 col-md-2 col-xs-2 col-sm-2">
    			<b>Calificaci&oacute;n</b>
    		</div>
    		<div style="margin-top: 20px;" class="col-lg-1 col-md-2 col-xs-2 col-sm-2">
    			<b>Guardar</b>
    		</div>
    	</div>
    <jstl:forEach items="${items}" var="item">
    	<div class="col-lg-12 col-md-12 col-xs-12 col-sm-12" >
    		<form action="app/profesor/item/guardar.do" method="POST">
    		<div style="margin-top: 20px;" class="col-lg-4 col-md-4 col-xs-4 col-sm-4">
    			<jstl:out value="${item.titulo}"></jstl:out>
    		</div>
    		<div style="margin-top: 20px;" class="col-lg-2 col-md-2 col-xs-2 col-sm-2">
    			<jstl:out value="${item.diasDelCalendario.get(0).alumno.nombre}"></jstl:out>,
    			<jstl:out value="${item.diasDelCalendario.get(0).alumno.apellidos}"></jstl:out>
    		</div>
    		<div style="margin-top: 20px;" class="col-lg-2 col-md-2 col-xs-2 col-sm-2">
    			<jstl:out value="${item.diasDelCalendario.get(0).alumno.curso}"></jstl:out>
    		</div>
    		<div class="col-lg-1 col-md-1 col-xs-1 col-sm-1">
    			<img class="thumbnail"  style="max-height: 60px;" src="/appEducacional/rest/profesor/alumno/foto.do?alumnoId=${item.diasDelCalendario.get(0).alumno.id}" alt="Firma">
    		</div>
    		<div style="margin-top: 20px;" class="col-lg-2 col-md-2 col-xs-2 col-sm-2">
    			<input id="calificacion_${item.id}" name="calificacion" style="text-align: center;" value="${item.calificacion}" type="text" class="form-control">
    		</div>
    		<div style="margin-top: 20px;" class="col-lg-1 col-md-2 col-xs-2 col-sm-2">
    			<input type="hidden" name="itemId" value="${item.id}" />
    			<input type="hidden" name="tipo_nota" value="${item.getClass().simpleName}" />
    			<input type="hidden" name="eventoId" value="${eventoId}" />
    			<button type="submit"  class="btn btn-success" ><i class="fa fa-save"></i></button>
    		</div>
    		</form>
    	</div>
    	
    </jstl:forEach>
</div>
