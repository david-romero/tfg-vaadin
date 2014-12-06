<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form method="POST" cssClass="form-horizontal col-lg-12 col-md-12 col-sm-12 col-xs-12" role="form" action="app/admin/save.do" modelAttribute="admin" >

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="userAccount.authorities"/>
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
				<form:label path="nombre">Nombre:</form:label>
				<form:input cssClass="form-control" path="nombre"/> <br>
				<form:errors cssClass="error" path="nombre" />
			</div>
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">		

				<form:label path="apellidos">Apellidos:</form:label>
				<form:input cssClass="form-control" path="apellidos"/><br>
				<form:errors cssClass="error" path="apellidos" />
			</div>
		</div>
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
				<form:label path="email">Email:</form:label>
				<form:input cssClass="form-control" path="email"/><br>
				<form:errors cssClass="error" path="email" />
			</div>
			<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
				<form:label path="telefono">Telefono:</form:label>
				<form:input cssClass="form-control" path="telefono"/><br>
				<form:errors cssClass="error" path="telefono" />
			</div>
			<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
				<form:label path="DNI">D.N.I.</form:label>
				<form:input cssClass="form-control"  path="DNI"/><br>
				<form:errors cssClass="error" path="DNI" />
			</div>
		</div>
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
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
		</div>
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="text-align: center;">
			<input type="submit" id="save" name="save" value="Enviar" class="btn btn-success" />
			<a class="btn btn-danger" href="app/welcome/index.do">Cancelar</a>
		</div>

</form:form>