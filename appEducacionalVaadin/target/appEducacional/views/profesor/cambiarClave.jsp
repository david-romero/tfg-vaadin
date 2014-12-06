<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form method="POST" class="form-horizontal col-lg-12 col-md-12 col-sm-12 col-xs-12" role="form" action="app/profesor/savePassword">

		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<label for="password">Contrase&ntilde;a:</label>
			<input type="password" class="form-control" name="password"/>
		</div>
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<label for="passwordConfirm">Confirmar Contrase&ntilde;a:</label>
			<input type="password" class="form-control" name="passwordConfirm"/>
		</div>
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="text-align: center;">
			<input type="submit" id="save" name="save" value="Enviar" class="btn btn-success" />
			<a class="btn btn-danger" href="app/welcome/index.do">Cancelar</a>
		</div>

</form>