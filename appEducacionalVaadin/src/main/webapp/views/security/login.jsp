<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div class="page-wrapper" style="margin-left: -30px;margin-right: -30px;">
            <!-- header-11 -->
            <header class="header-11">
                <div class="" >
                    <div class="row">
                        <div class="navbar col-sm-12" role="navigation">
                            <div class="navbar-header" style="background-color: white;">
                                <button type="button" class="navbar-toggle"></button>
                                <a style="margin-top: -10px;" class="brand" href="/appEducacional/app/welcome/index.do"><img src="images/logo2.png" width="50" height="50" alt=""> Guardians</a>
                            </div>
                            <div class="collapse navbar-collapse pull-right" style="">
                                <ul class="nav pull-left" style="margin-top: -15px;">
                                    <li class="active"><a href="#">Inicio</a></li>
                                    <li><a href="#">Sobre Nosotros</a></li>
                                    <li><a href="#">Blog</a></li>
                                    <li><a href="#">Contacto</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </header>
            <section class="header-11-sub bg-midnight-blue">
                <div class="background">
                    &nbsp;
                </div>
                <div class="" style="margin-top: -2%;">
                    <div class="row" style="margin-right: 20px;margin-left: 0px;">
                        <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
                            <h3>Guardians</h3>
                            <p>
                                La aplicaci&oacute;n que te ayuda a contactar con profesores y padres, madres o tutores.
                            </p>
                            <div class="signup-form">
                                <form:form action="j_spring_security_check" modelAttribute="credentials">
                                    <div class="input-group" style="margin-bottom: 5px;">
                                    	<span class="input-group-addon" style="border-top-width: 0px;border-bottom-width: 0px;"><i class="fa fa-user fa-fw"></i></span>
                                        <form:input path="username" class="form-control" type="text" placeholder="Usuario" />
                                        <form:errors class="error" path="username" />
                                    </div>
                                        <div class="input-group" style="margin-bottom: 5px;">
                                        	<span class="input-group-addon" style="border-top-width: 0px;border-bottom-width: 0px;"><i class="fa fa-key fa-fw"></i></span>
                                            <form:password path="password" class="form-control" placeholder="Clave" />
                                            <form:errors class="error" path="password" />
                                        </div>
                                    <div class="form-group">
                                    	<jstl:if test="${showError == true}">
											<div class="error">
												<spring:message code="security.login.failed" />
											</div>
										</jstl:if>
                                        <button type="submit" class="btn btn-block btn-info">Acceder</button>
                                    </div>
                                </form:form>
                            </div>
                            <div class="additional-links" style="margin-bottom: 2%;color:black;">
                                Reg&iacute;strate como <a style="cursor:pointer;;color:black;" href="app/alta/profesor/alta.do">Profesor</a> o <a href="app/alta/tutor/alta.do" style="cursor:pointer;;color:black;">como Padre, Madre o Tutor</a>
                            </div>
                            <div class="additional-links">
                                Registr&aacute;ndote est&aacute;s aceptando los <a style="cursor:pointer;" href="#">T&eacute;rminos de uso</a> y <a style="cursor:pointer;" href="#"> la pol&iacute;tica de privacidad</a>
                            </div>
                        </div>
                        <div class="col-lg-8 col-md-8 col-sm-12 col-xs-12 player-wrapper" style="display: block;">
                            
                        <div class="player">
                                <iframe src="http://player.vimeo.com/video/29568236?color=3498db" webkitallowfullscreen="" mozallowfullscreen="" allowfullscreen=""></iframe>
                                <!--<iframe src="http://www.youtube.com/embed/BCC7rFxo6QA" allowfullscreen></iframe>-->
                            </div></div>
                    </div>
                </div>
            </section>
           </div>
           
           <script>
            jQuery('#content-body').css("height","85%;");
            jQuery('#footer').css("z-index","20");
           	jQuery('#footer').css("margin-top","-20px");
           </script>