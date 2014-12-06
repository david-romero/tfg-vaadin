<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

 <script src="http://code.highcharts.com/highcharts.js"></script>
 <script src="http://code.highcharts.com/modules/data.js"></script>
<script src="http://code.highcharts.com/modules/exporting.js"></script>

<div id="tabs" style="">
	<ul id="tabs-index" class="nav nav-tabs col-lg-12 col-md-12">
		
	</ul>
	<div id="container-tab" style="padding-left: 0px;
padding-right: 0px;" class="tab-content col-lg-12 col-md-12">

	        
	</div><!-- tab content -->
</div>
<jstl:set var="counter"  value="${0}"/>
<jstl:forEach items="${cursos}" var="curso">
	<jstl:if test="${counter eq 0 }">
		<script>
		jQuery('#tabs-index').append('<li class="active" ><a style="padding-left: 4px;padding-right: 4px;"  href="#tab_${curso.id}" data-toggle="pill">${curso.nivel} ${curso.nivelEducativo} ${curso.identificador}</a></li>');
		</script>
		<script>
			jQuery('#container-tab').append('<div class="tab-pane active" id="tab_${curso.id}">'+
					'<div id="${curso.id}-container1" style="padding:0" class="col-lg-4 col-md-4 col-sm-12 col-xs-12"></div>'+
					'<div id="${curso.id}-container2" style="padding:0" class="col-lg-4 col-md-4 col-sm-12 col-xs-12"></div>'+
					'<div id="${curso.id}-container3" style="padding:0" class="col-lg-4 col-md-4 col-sm-12 col-xs-12"></div>' +
			        '</div>');
		</script>
	</jstl:if>
	<jstl:if test="${counter gt 0 }">
		<script>
		jQuery('#tabs-index').append('<li ><a style="padding-left: 4px;padding-right: 4px;"  href="#tab_'+${curso.id}+'" data-toggle="pill">${curso.nivel} ${curso.nivelEducativo} ${curso.identificador}</a></li>');
		</script>
		<script>
			jQuery('#container-tab').append('<div class="tab-pane" id="tab_${curso.id}">'+
					'<div id="${curso.id}-container1" style="padding:0" class="col-lg-4 col-md-4 col-sm-12 col-xs-12"></div>'+
'<div id="${curso.id}-container2" style="padding:0" class="col-lg-4 col-md-4 col-sm-12 col-xs-12"></div>'+
'<div id="${curso.id}-container3" style="padding:0" class="col-lg-4 col-md-4 col-sm-12 col-xs-12"></div>' +
			        '</div>');
		</script>
	</jstl:if>
	<jstl:set var="counter"  value="${counter+1}"/>
	<script>
$(function () {
    var options = {
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: '1ª Evaluación'
        },
        tooltip: {
    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false
                },
                showInLegend: true
            }
        },
        series: [{
            type: 'pie',
            name: 'Porcentaje',
            data: []
        }]
    };
    
    $.getJSON('/appEducacional/rest/profesor/curso/estadisticasPorCurso.do?curso=${curso.id}&evaluacion=1', function(data) {
        var json = '[';
    	jQuery.each(data,
        		function(index,opcion)
        		{
 					json += '{"name":"' + index +'","y":' + opcion +'},';
        		});
    	json = json.substring(0, json.length - 1);
    	json += ']';
        var arr = JSON.parse(json);
        options.series[0].data = arr;
        //var chart = new Highcharts.Chart(options);
        $('#${curso.id}-container1').highcharts(options);
    });
});
</script>
<script>
$(function () {
    var options = {
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: '2ª Evaluación'
        },
        tooltip: {
    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false
                },
                showInLegend: true
            }
        },
        series: [{
            type: 'pie',
            name: 'Porcentaje',
            data: []
        }]
    };
    
    $.getJSON('/appEducacional/rest/profesor/curso/estadisticasPorCurso.do?curso=${curso.id}&evaluacion=2', function(data) {
        var json = '[';
    	jQuery.each(data,
        		function(index,opcion)
        		{
 					json += '{"name":"' + index +'","y":' + opcion +'},';
        		});
    	json = json.substring(0, json.length - 1);
    	json += ']';
        var arr = JSON.parse(json);
        options.series[0].data = arr;
        //var chart = new Highcharts.Chart(options);
        $('#${curso.id}-container2').highcharts(options);
    });
});
</script>
<script>
$(function () {
    var options = {
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: '3ª Evaluación'
        },
        tooltip: {
    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions:
        {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false
                },
                showInLegend: true
            }
        },
        series: [{
            type: 'pie',
            name: 'Porcentaje',
            data: []
        }]
    };
    
    $.getJSON('/appEducacional/rest/profesor/curso/estadisticasPorCurso.do?curso=${curso.id}&evaluacion=3', function(data) {
        var json = '[';
    	jQuery.each(data,
        		function(index,opcion)
        		{
 					json += '{"name":"' + index +'","y":' + opcion +'},';
        		});
    	json = json.substring(0, json.length - 1);
    	json += ']';
        var arr = JSON.parse(json);
        options.series[0].data = arr;
        //var chart = new Highcharts.Chart(options);
        $('#${curso.id}-container3').highcharts(options);
    });
});
</script>
</jstl:forEach>
		<script>
		jQuery('#tabs-index').append('<li class="" ><a style="padding-left: 4px;padding-right: 4px;"  href="#tab_aprobados" data-toggle="pill">% Aprobados </a></li>');
		</script>
		<script>
			jQuery('#container-tab').append('<div class="tab-pane" id="tab_aprobados">'+
					'<div id="aprobados-container" style="padding:0;margin-left: -30px;" class="col-lg-12 col-md-12 col-sm-12 col-xs-12"></div>'+
			        '</div>');
			var optionsHS = {
		            chart: {
		                type: 'column'
		            },
		            title: {
		                text: '% Aprobados y Suspensos'
		            },
		            subtitle: {
		                text: 'Por curso'
		            },
		            xAxis: {
		                categories: [
		                    
		                ]
		            },
		            yAxis: {
		                min: 0,
		                title: {
		                    text: '%'
		                }
		            },
		            tooltip: {
		                headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
		                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
		                    '<td style="padding:0"><b>{point.y:.1f} %</b></td></tr>',
		                footerFormat: '</table>',
		                shared: true,
		                useHTML: true
		            },
		            plotOptions: {
		                column: {
		                    pointPadding: 0.2,
		                    borderWidth: 0
		                }
		            },
		            series: [{
		                name: 'Aprobados',
		                data: [49, 22]
		    
		            }, {
		                name: 'Suspensos',
		                data: [51, 88]
		    
		            }]
		        };
			

		</script>
		<jstl:set var="counter"  value="${0}"/>
		<jstl:forEach items="${cursos}" var="curso">
			<script>
			    console.log(optionsHS.xAxis.categories);
			    console.log(optionsHS);
			    var js_counter = parseInt('${counter}');
				optionsHS.xAxis.categories[js_counter] = '${curso.nivel} ${curso.nivelEducativo} ${curso.identificador}'; 
			</script>
			<jstl:set var="counter"  value="${counter+1}"/>
		</jstl:forEach>
		<script>
		$('#aprobados-container').highcharts(optionsHS);
		</script>



