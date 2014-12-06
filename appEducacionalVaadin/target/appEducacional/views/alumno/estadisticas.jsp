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
<ul class="nav nav-tabs">
  <li class="active"><a id="1" href="#tab_a" data-toggle="tab">${alumno.nombre} ${alumno.apellidos}</a></li>
  <li><a id="2" href="#tab_b" data-toggle="tab">Comparativa con el curso</a></li>
  <li><a id="3" href="#tab_c" data-toggle="tab">Comparativa con otros a&ntilde;os</a></li>
  <li><a id="4" href="#tab_d" data-toggle="tab">Notas de asignaturas</a></li>
</ul><!-- end of nav -->
<div class="tab-content">
        <div class="tab-pane  active" id="tab_a">
            <div id="container" class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
        </div>
        <div class="tab-pane" id="tab_b">
            <div id="container2" class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
        </div>
        <div class="tab-pane" id="tab_c">
            <div id="container3" class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
        </div>
        <div class="tab-pane" id="tab_d">
            <div id="container4" class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
        </div>
</div><!-- tab content -->


<script type="text/javascript">
<!--

//-->
$(function () {
    $('#container').highcharts({
        title: {
            text: 'Media de Notas Obtenidas',
            x: -20 //center
        },
        subtitle: {
            text: '',
            x: -20
        },
        xAxis: {
            categories: ['Sep', 'Oct', 'Nov', 'Dic', 'Ene', 'Feb',
                'Mar', 'Abr', 'May', 'Jun', 'Jul']
        },
        yAxis: {
            title: {
                text: 'Nota'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: ''
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            name: "${alumno.nombre} ${alumno.apellidos}",
            data: [7.0, 6.9, 9.5, 4.5, 8.2, 1.5, 5.2, 6.5, 3.3, 8.3, 3.9]
        }]
    });
    
    $('#container2').highcharts({
        title: {
            text: 'Media de Notas Obtenidas con media del curso',
            x: -20 //center
        },
        subtitle: {
            text: '',
            x: -20
        },
        xAxis: {
            categories: ['Sep', 'Oct', 'Nov', 'Dic', 'Ene', 'Feb',
                'Mar', 'Abr', 'May', 'Jun', 'Jul']
        },
        yAxis: {
            title: {
                text: 'Nota'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: ''
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [
        {
            name: "${alumno.nombre} ${alumno.apellidos}",
            data: [7.0, 6.9, 9.5, 4.5, 8.2, 1.5, 5.2, 6.5, 3.3, 8.3, 3.9]
        },
        {
            name: "Media del curso",
            data: [5.75, 6.9, 7.5, 4.5, 5.2, 6.5, 4.2, 6.5, 5.3, 6.3, 4.9]
        }
        ]
    });
    
    
    
    
    
});

$(function() {
	$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
	     if (e.currentTarget.id == 3)
	     {
		     $('#container' + e.currentTarget.id).highcharts({
		         title: {
		             text: 'Media de Notas Obtenidas con cursos anteriores',
		             x: -20 //center
		         },
		         subtitle: {
		             text: '',
		             x: -20
		         },
		         xAxis: {
		             categories: ['Sep', 'Oct', 'Nov', 'Dic', 'Ene', 'Feb',
		                 'Mar', 'Abr', 'May', 'Jun', 'Jul']
		         },
		         yAxis: {
		             title: {
		                 text: 'Nota'
		             },
		             plotLines: [{
		                 value: 0,
		                 width: 1,
		                 color: '#808080'
		             }]
		         },
		         tooltip: {
		             valueSuffix: ''
		         },
		         legend: {
		             layout: 'vertical',
		             align: 'right',
		             verticalAlign: 'middle',
		             borderWidth: 0
		         },
		         series: [
		         {
		             name: "${alumno.nombre} ${alumno.apellidos}",
		             data: [7.0, 6.9, 9.5, 4.5, 8.2, 1.5, 5.2, 6.5, 3.3, 8.3, 3.9]
		         },
		         {
		             name: "Curso 2014-2015",
		             data: [5.75, 6.9, 7.5, 4.5, 5.2, 6.5, 4.2, 6.5, 5.3, 6.3, 4.9]
		         },
		         {
		             name: "Curso 2012-2013",
		             data: [3.75, 4.9, 3.5, 4.5, 4.2, 5.5, 6.2, 6.7, 6.3, 6.3, 5.9]
		         }
		         ]
		     });
	     }
	     if (e.currentTarget.id == 2)
	     {
		     $('#container' + e.currentTarget.id).highcharts({
		         title: {
		             text: 'Media de Notas Obtenidas con media del curso',
		             x: -20 //center
		         },
		         subtitle: {
		             text: '',
		             x: -20
		         },
		         xAxis: {
		             categories: ['Sep', 'Oct', 'Nov', 'Dic', 'Ene', 'Feb',
		                 'Mar', 'Abr', 'May', 'Jun', 'Jul']
		         },
		         yAxis: {
		             title: {
		                 text: 'Nota'
		             },
		             plotLines: [{
		                 value: 0,
		                 width: 1,
		                 color: '#808080'
		             }]
		         },
		         tooltip: {
		             valueSuffix: ''
		         },
		         legend: {
		             layout: 'vertical',
		             align: 'right',
		             verticalAlign: 'middle',
		             borderWidth: 0
		         },
		         series: [
		         {
		             name: "${alumno.nombre} ${alumno.apellidos}",
		             data: [7.0, 6.9, 9.5, 4.5, 8.2, 1.5, 5.2, 6.5, 3.3, 8.3, 3.9]
		         },
		         {
		             name: "Media del curso",
		             data: [5.75, 6.9, 7.5, 4.5, 5.2, 6.5, 4.2, 6.5, 5.3, 6.3, 4.9]
		         }
		         ]
		     });
	     }
	     if (e.currentTarget.id == 4)
	     {
		     $('#container' + e.currentTarget.id).highcharts({
		            chart: {
		                type: 'column'
		            },
		            title: {
		                text: 'Notas por asignatura con la media del curso'
		            },
		            subtitle: {
		                text: ''
		            },
		            xAxis: {
		                categories: [
		                    'Matemáticas',
		                    'Lengua Castellana',
		                    'Ciencias Sociales',
		                    'Ciencias Naturales',
		                    'Inglés',
		                    'Francés',
		                    'Educación Física'
		                ]
		            },
		            yAxis: {
		                min: 0,
		                title: {
		                    text: 'Nota: '
		                }
		            },
		            tooltip: {
		                headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
		                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
		                    '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
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
		                name: '${alumno.nombre} ${alumno.apellidos}',
		                data: [4.9, 7.5, 6.4, 9.2, 4.0, 6.0, 5.6]
		    
		            }, {
		                name: 'Media del Curso',
		                data: [3.6, 8.8, 8.5, 3.4, 6.0, 4.5, 5.0]
		    
		            }]
		        });
	     }
	 });
});
</script>

