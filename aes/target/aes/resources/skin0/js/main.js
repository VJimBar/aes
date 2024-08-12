$(document).ready(function() {

	// Inicializar de forma genÃ©rica todos los controles de fecha.
	try
	{
		$('.datepicker').datepicker({
			dateFormat: "dd/mm/yyyy",
			language: "es",
			autoclose: true,
			todayBtn: "linked",
			todayHighlight: true
		});

		// Inicializar control de fecha .NET.
		$(".btn-calendar-net").datepicker({
			language: "es",
	        autoclose: true,
	        todayBtn: "linked",
	        todayHighlight: true,
	        buttonImageOnly: true,
	        buttonImage: '../../Recursos/img/calendar.png',
	        buttonText: "SelecciÃ³n de fecha",
	        monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
	        monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
	        dayNames: ['Domingo', 'Lunes', 'Martes', 'MiÃ©rcoles', 'Jueves', 'Viernes', 'SÃ¡bado'],
	        dayNamesShort: ['Dom', 'Lun', 'Mar', 'MiÃ©', 'Juv', 'Vie', 'SÃ¡b'],
	        dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'SÃ¡'],
	        weekHeader: 'Sm',
	        dateFormat: 'dd/mm/yy',
	        firstDay: 1,
	    });
	}
	catch (errDatepicker)
	{
		/* No hace nada. Es para evitar que deje de cargar el JS en caso no utilizar
		   un control Datepicker y por lo tanto, no cargar las librerÃ­as JS necesarias */
		   console.log("No se han cargado las librerÃ­as de Bootstrap para el control Datapicker.");
	}

    $("#btnLogoutModal").on('click', function() {
		logout();
	});

	// Sticky Menu.
	var stickyMenu = $('.container-menu');
	if (hayMenu = (stickyMenu.length > 0))
		var altura = stickyMenu.offset().top;

	$(window).on('scroll', function()
	{
		if (hayMenu)
			if ($(window).scrollTop() > altura) 
				stickyMenu.addClass('navbar-fixed-top');
			else
				stickyMenu.removeClass('navbar-fixed-top');
	});
});

function muestraCargando() {
	$("body").addClass("loading");
	return true;
}

function unloadCargando() {
	$("body").removeClass("loading");
	return true;
}

function colocarAtributoElemento(idElemento, atributo, objeto) {
	if (atributo == 'text')
		$('#' + idElemento).text(objeto);
	else
		$('#' + idElemento).attr(atributo, objeto);
}

function mostrarElemento(idElemento) {
	$('#' + idElemento).css('display', 'block');
}

function ocultarElemento(idElemento) {
	$('#' + idElemento).css('display', 'none');
}

function mostrarOcultarElemento(idElemento) {
	if ($('#' + idElemento).css('display') == 'block') {
		$('#' + idElemento).css('display', 'none');
		return false;
	} else {
		$('#' + idElemento).css('display', 'block');
		return true;
	}
}

function ponerElementoTrue(idElemento) {
	$('#' + idElemento).val(true);
}

function setValor(idElemento, valor) {
	$('#' + idElemento).val(valor);
}

function placeholderSelect(idElemento) {
	$('#' + idElemento).change(function() {
		if ($(this).val() == "") {
			$(this).addClass("option-empty");
		} else {
			$(this).removeClass("option-empty")
		}
	});
	$('#' + idElemento).change();
}

function generarTablaSinDatos(idTable) {
	$(idTable).DataTable({
		dom : "",
		responsive : true,
		paging : false,
		bFilter : false,
		language : {
			url : "/Recursos/datatables/i18n/Spanish.json",
			"infoEmpty" : "No Existe informaciÃ³n disponible",
			"emptyTable" : "No Existe informaciÃ³n disponible",
			"zeroRecords" : "No Existe informaciÃ³n disponible",
			"dataTables_empty" : "No Existe informaciÃ³n disponible"
		},
		columnDefs : [ {
			"orderable" : false,
			"targets" : -1
		} ],
		initComplete : function(settings, json) {
			$('.dataTables_empty').html("No Existe informaciÃ³n disponible");
		}
	});
}

function getBase64Image(img) {

	// Create an empty canvas element
	var canvas = document.createElement("canvas");
	canvas.width = img.width;
	canvas.height = img.height;

	// Copy the image contents to the canvas
	var ctx = canvas.getContext("2d");
	ctx.drawImage(img, 0, 0);
	var dataURL = canvas.toDataURL("image/png");

	// Data-URL formatted image, Firefox supports PNG and JPEG.
	return dataURL;

	// You could check img.src to guess the original format, but be aware the
	// using "image/jpg" will re-encode the image.
	// return dataURL.replace(/^data:image\/(png|jpg);base64,/, "");
}

// IMAGENES PARA LAS CABECERAS DE PDF EXPORTADOS EN TABLAS
var ImageFomento = new Image();
ImageFomento.src = window.location.origin + "/Recursos/img/logo-fomento.jpg";
ImageFomento.onload = function(){};
function getImagesFomento() {
	var confImagenFomento;
			confImagenFomento = {
			fit : [ 110, 100 ],
			margin : [ 0, 0, 0, 10 ],
			alignment : 'left',
			image : getBase64Image(ImageFomento)
		};
	
	return confImagenFomento;
}

var imageAesa = new Image();
imageAesa.src = window.location.origin + "/Recursos/img/logo-aesa.jpg";
imageAesa.onload = function(){
};
function getImagesAesa() {
	var confImagenAesa;
		confImagenAesa = {
			fit : [ 140, 100 ],
			margin : [ 0, 0, 0, 10 ],
			alignment : 'right',
			image : getBase64Image(imageAesa)
	};
	
	return confImagenAesa;
}