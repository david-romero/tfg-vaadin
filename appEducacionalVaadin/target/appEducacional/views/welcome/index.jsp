<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

    <!--Slider-->
     <div id="slider" class="carousel slide" data-ride="carousel" style="margin-left: -30px;margin-top: -1%;margin-right: -30px;">
     
     	<ol class="carousel-indicators" style="margin-left: -50%;">
		    <li data-target="#slider" data-slide-to="0" class="active"></li>
		    <li data-target="#slider" data-slide-to="1"></li>
		    <li data-target="#slider" data-slide-to="2"></li>
  		</ol>

        <!--Slider Items-->    
            <!--Slider Item1-->
            <div style="background-color: #47a3da;" class="carousel-inner">
            
            
    			<div class="item active" style="background-color: #47a3da;">
                        <img class="pull-right" src="images/img1.png" alt="" />
                        <div class="carousel-caption" style="left:-15%;top:inherit;">
	                        <h2 class="">Tu mejor ayudante</h2>
	                        <h3 class="gap">Tanto si eres profesor o padre, madre o tutor<br> esta es tu aplicación para controlar a tus tutorandos</h3>
	                        
                    	</div>
                </div>
            <!--/Slider Item1-->

            <!--Slider Item2-->
    			<div class="item">
                        <img class="pull-right" src="images/img2.png" alt="" />
                        <div class="carousel-caption" style="left:-15%;top:inherit;">
	                        <h2>Gana tiempo y felicidad</h2>
	                        <h3 class="gap">Podrás realizar tus tareas de seguimiento <br>
	                        o de control en cualquier instance con una interfaz rápida, <br>
	                        sencilla e intuitiva.
	                        </h3>
	                        
                        </div>
                </div>
            <!--Slider Item2-->

            <!--Slider Item3-->
    			<div class="item">
                    <img class="pull-right" src="images/img3.png" alt="" />
                    <div class="carousel-caption" style="left:-15%;top:inherit;">
	                    <h2>Adaptado a todos tus dispositivos</h2>
	                    <h3 class="gap">Utilizame donde quieras</h3>
	                    <br>
	                    <br>
	                    <br>
	                    <br>
	                    <br>
	                    <br>
	                    <br>
	                    <h5 style="text-align: center;">
	                    	Gracias a su dise&ntilde;o, podr&aacute;s acceder a <br>
	                    	la aplicaci&oacute;n desde cualquier dispositivo y en <br>
	                    	cualquier momento.
	                    </h5>
                    </div>
                </div>
        <!--Slider Item3-->
        
        </div>

    <!-- Controls -->
  <a style="background: none;" class="left carousel-control" href="#slider" data-slide="prev">
    <span class="glyphicon glyphicon-chevron-left"></span>
  </a>
  <a style="background: none;" class="right carousel-control" href="#slider" data-slide="next">
    <span class="glyphicon glyphicon-chevron-right"></span>
  </a>

</div>

