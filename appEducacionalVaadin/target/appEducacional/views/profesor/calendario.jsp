<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div class="page-header" style="margin-top: -10px;">

		<div class="pull-right form-inline">
			<div class="btn-group">
				<button class="btn btn-primary" data-calendar-nav="prev">&lt;&lt; Anterior</button>
				<button class="btn" data-calendar-nav="today">Hoy</button>
				<button class="btn btn-primary" data-calendar-nav="next">Siguiente &gt;&gt;</button>
			</div>
			<div class="btn-group">
				<button class="btn btn-warning" data-calendar-view="year">A&ntilde;o</button>
				<button class="btn btn-warning active" data-calendar-view="month">Mes</button>
				<button class="btn btn-warning" data-calendar-view="week">Semana</button>
				<button class="btn btn-warning" data-calendar-view="day">D&iacute;a</button>
			</div>
		</div>

		<h3> </h3>
	</div>
<div id="calendar"></div>


	<script type="text/javascript" src="scripts/underscore.js"></script>
    <script type="text/javascript" src="scripts/calendar.js"></script>
    <script type="text/javascript">
    "use strict";
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1; //January is 0!
    var yyyy = today.getFullYear();

    if(dd<10) {
        dd='0'+dd
    } 

    if(mm<10) {
        mm='0'+mm
    } 

    today = yyyy+'-'+mm+'-'+dd;
 
	var options = {
			events_source: "rest/profesor/getEventos.do",
				 /*[
					{
						"id": "293",
						"title": "This is warning class event with very long title to check how it fits to evet in day view",
						"url": "http://www.example.com/",
						"class": "event-warning",
						"start": "1362938400000",
						"end":   "1363197686300"
					},
					{
						"id": "256",
						"title": "Event that ends on timeline",
						"url": "http://www.example.com/",
						"class": "event-warning",
						"start": "1363155300000",
						"end":   "1363227600000"
					},
					{
						"id": "276",
						"title": "Short day event",
						"url": "http://www.example.com/",
						"class": "event-success",
						"start": "1363245600000",
						"end":   "1363252200000"
					},
					{
						"id": "294",
						"title": "This is information class ",
						"url": "http://www.example.com/",
						"class": "event-info",
						"start": "1363111200000",
						"end":   "1363284086400"
					},
					{
						"id": "297",
						"title": "This is success event",
						"url": "http://www.example.com/",
						"class": "event-success",
						"start": "1363234500000",
						"end":   "1363284062400"
					},
					{
						"id": "54",
						"title": "This is simple event",
						"url": "http://www.example.com/",
						"class": "",
						"start": "1363712400000",
						"end":   "1363716086400"
					},
					{
						"id": "532",
						"title": "This is inverse event",
						"url": "http://www.example.com/",
						"class": "event-inverse",
						"start": "1364407200000",
						"end":   "1364493686400"
					},
					{
						"id": "548",
						"title": "This is special event",
						"url": "http://www.example.com/",
						"class": "event-special",
						"start": "1363197600000",
						"end":   "1363629686400"
					},
					{
						"id": "295",
						"title": "Event 3",
						"url": "http://www.example.com/",
						"class": "event-important",
						"start": "1364320800000",
						"end":   "1364407286400"
					}
				],*/
		view: 'month',
		tmpl_path: 'tmpls/',
		tmpl_cache: false,
		first_day : 1,
		day: today,
		languaje: "es-ES",
		onAfterEventsLoad: function(events) {
			if(!events) {
				return;
			}
			var list = $('#eventlist');
			list.html('');

			$.each(events, function(key, val) {
				$(document.createElement('li'))
					.html('<a href="' + val.url + '">' + val.title + '</a>')
					.appendTo(list);
			});
		},
		onAfterViewLoad: function(view) {
			$('.page-header h3').text(this.getTitle());
			$('.btn-group button').removeClass('active');
			$('button[data-calendar-view="' + view + '"]').addClass('active');
		},
		classes: {
			months: {
				general: 'label'
			}
		}
	};

	var calendar = $('#calendar').calendar(options);   
	
	$('.btn-group button[data-calendar-nav]').each(function() {
		var $this = $(this);
		$this.click(function() {
			calendar.navigate($this.data('calendar-nav'));
		});
	});

	$('.btn-group button[data-calendar-view]').each(function() {
		var $this = $(this);
		$this.click(function() {
			calendar.view($this.data('calendar-view'));
		});
	});
	calendar.setLanguage("es-ES");
	calendar.view();
    </script>