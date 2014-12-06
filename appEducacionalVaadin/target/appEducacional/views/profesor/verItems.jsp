<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table requestURI="app/profesor/item/showAll.do"  
	name="items" pagesize="10" class="table table-hover col-lg-12" id="row" 
	defaultsort="1" defaultorder="descending" >
	
	<!-- Attributes -->

	<display:column class="col-lg-2" property="fecha" format="{0,date,dd/MM/yyyy}" title="Fecha" sortable="true" headerClass="sortable" >Ofgdfgdfgdfgdf
	</display:column>
	<spring:message code="alumno.apellidos" var="apellidosHeader" />
	<display:column class="col-lg-2" property="asignatura.nombre" title="Asignatura" sortable="true" headerClass="sortable" />
	
	<display:column class="col-lg-1" property="asignatura.curso" title="Curso" sortable="true" headerClass="sortable" />
	
	<display:column class="col-lg-2" title="Tipo">
		<jstl:out value="${row.getClass().getSimpleName()}"></jstl:out>,
	</display:column>
	
	<display:column class="col-lg-3" title="Alumno">
		<jstl:out value="${row.diasDelCalendario.get(0).alumno.nombre}"></jstl:out>,
    			<jstl:out value="${row.diasDelCalendario.get(0).alumno.apellidos}"></jstl:out>
	</display:column>
	<jstl:choose>
		<jstl:when test="${not justificar}">
			<display:column class="col-lg-2" title="Eliminar" sortable="false" >
				<form action="app/profesor/item/delete.do" method="POST">
					<input type="hidden" name="item" value="${row.id}">
					<input type="hidden" name="tipo" value="${row.getClass().getSimpleName()}" />
					<button type="submit"  style="margin-top: -1%;" class="btn btn-danger" ><i class="fa fa-trash-o"></i></button>
				</form>
			</display:column>
		</jstl:when>
		<jstl:otherwise>
			<display:column class="col-lg-2" title="Justificar" sortable="false" >
				<form action="app/profesor/item/justificar.do" method="POST">
					<input type="hidden" name="item" value="${row.id}">
					<input type="hidden" name="tipo" value="${row.getClass().getSimpleName()}" />
					<button type="submit"  style="margin-top: -1%;" class="btn btn-success" ><i class="fa fa-check"></i></button>
				</form>
			</display:column>
		</jstl:otherwise>
	</jstl:choose>
	

	
</display:table>