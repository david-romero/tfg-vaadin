<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<blockquote>
	Profesores
</blockquote>
<display:table style="margin-top:-10px;margin-bottom:0px" requestURI="app/admin/banear.do"  
	name="profesores" pagesize="3" class="table table-hover col-lg-12 col-md-12 col-sm-12 col-xs-12" id="row" 
	defaultsort="1" defaultorder="ascending">
	
		<!-- Attributes -->

	<spring:message code="alumno.nombre" var="nameHeader" />
	<display:column class="col-lg-3" property="nombre" title="${nameHeader}" sortable="true" headerClass="sortable" />

	<spring:message code="alumno.apellidos" var="apellidosHeader" />
	<display:column class="col-lg-5" property="apellidos" title="${apellidosHeader}" sortable="true" headerClass="sortable" />
	
	<display:column class="col-lg-3" property="DNI" title="D.N.I." sortable="true" headerClass="sortable" />
	
	
	<display:column title="Banear" class="col-lg-1">
		<jstl:choose>
			<jstl:when test="${fn:length(row.userAccount.authorities ) eq 0}">
				<form action="app/admin/unLock.do" method="post">
					<input type="hidden" name="personaId" value="${row.id}" >
					<input type="hidden" name="tipo" value="PROFESOR" >
					<button class="btn" style="background-color: transparent;color:blue !important;" type="submit" ><i class="fa fa-unlock"></i></button>
				</form>
			</jstl:when>
			<jstl:otherwise>
				<form action="app/admin/saveBaneo.do" method="post">
					<input type="hidden" name="personaId" value="${row.id}" >
					<input type="hidden" name="tipo" value="PROFESOR" >
					<button class="btn" style="background-color: transparent;color:red !important;" type="submit" ><i class="fa fa-ban"></i></button>
				</form>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	
</display:table>

<br>

<blockquote>
	Padre, Madre o Tutor
</blockquote>

<display:table style=""  requestURI="app/admin/banear.do"  
	name="tutores" pagesize="3" class="table table-hover col-lg-12 col-md-12 col-sm-12 col-xs-12" id="row" 
	defaultsort="1" defaultorder="ascending">
	
		<!-- Attributes -->

	<spring:message code="alumno.nombre" var="nameHeader" />
	<display:column class="col-lg-3" property="nombre" title="${nameHeader}" sortable="true" headerClass="sortable" />

	<spring:message code="alumno.apellidos" var="apellidosHeader" />
	<display:column class="col-lg-5" property="apellidos" title="${apellidosHeader}" sortable="true" headerClass="sortable" />
	
	<display:column class="col-lg-3" property="DNI" title="D.N.I." sortable="true" headerClass="sortable" />
	
	<display:column class="col-lg-1" title="Banear">
		<jstl:choose>
			<jstl:when test="${fn:length(row.userAccount.authorities ) eq 0}">
				<form action="app/admin/unLock.do" method="post">
					<input type="hidden" name="personaId" value="${row.id}" >
					<input type="hidden" name="tipo" value="TUTOR" >
					<button class="btn" style="background-color: transparent;color:blue !important;" type="submit" ><i class="fa fa-unlock"></i></button>
				</form>
			</jstl:when>
			<jstl:otherwise>
				<form action="app/admin/saveBaneo.do" method="post">
					<input type="hidden" name="personaId" value="${row.id}" >
					<input type="hidden" name="tipo" value="TUTOR" >
					<button class="btn" style="background-color: transparent;color:red !important;" type="submit" ><i class="fa fa-ban"></i></button>
				</form>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	
</display:table>