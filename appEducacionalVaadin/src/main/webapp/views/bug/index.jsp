<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form method="POST" class="form-horizontal col-lg-12 col-md-12 col-sm-12 col-xs-12" role="form" action="app/bug/sendBug.do"  >

	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<label class="col-lg-3 form-label">Email de Contacto</label>
		<input class="col-lg-9 form-control" name="email" />
	</div>
	
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<label class="col-lg-3 form-label">Informe de Fallo</label>
		<textarea style="max-width: 100%;max-height: 150px;height: 80px;" class="col-lg-9 form-control" name="informe">Cuanto más explícito sea más podrá ayudarnos.
			
		</textarea>
	</div>
	
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="text-align: center;">
		<button type="submit" class="btn btn-success"><i class="fa fa-send"></i>&nbsp;Enviar</button>
		<a class="btn btn-danger" href="app/welcome/index.do"><i class="fa fa-times"></i>&nbsp;Cancelar</a>
	</div>

</form>


