var hrefPRE = true;

function footer(fijo) {
	var footer = '';

	if (window.location.hostname.includes("-aesa") ||
		window.location.hostname.includes("aplicaciones-pre") ||
		window.location.hostname.includes("pre.aviacion") ||
		window.location.hostname.includes("presede")) {				
			var hrefAccesibilidad = "href=https://presede.seguridadaerea.gob.es/sede-aesa/accesibilidad";
			var hrefCookies = "href=https://presede.seguridadaerea.gob.es/sede-aesa/contenido/pol\u00EDtica-de-cookies";
			var hrefPrivacidad = "href=https://preportal.seguridadaerea.gob.es/es/politica-de-privacidad-y-aviso-legal";
			var hrefAyuda = "href=https://presede.seguridadaerea.gob.es/sede-aesa/contenido/solicitud-de-ayuda-0";
			var hrefMapa = "href=https://presede.seguridadaerea.gob.es/sede-aesa/sitemap";		
	} else {
			var hrefAccesibilidad = "href=https://sede.seguridadaerea.gob.es/sede-aesa/accesibilidad";
			var hrefCookies = "href=https://sede.seguridadaerea.gob.es/sede-aesa/contenido/pol\u00EDtica-de-cookies";
			var hrefPrivacidad = "href=https://www.seguridadaerea.gob.es/es/politica-de-privacidad-y-aviso-legal";
			var hrefAyuda = "href=https://sede.seguridadaerea.gob.es/sede-aesa/contenido/solicitud-de-ayuda-0";
			var hrefMapa = "href=https://sede.seguridadaerea.gob.es/sede-aesa/sitemap";
	}

	var copyright = '&copy' + (new Date()).getFullYear() + ' Agencia Estatal de Seguridad A&eacuterea';


	if (fijo) footer = ' navbar-fixed-bottom';

	footer = '<footer id=\"piePagina\" class=\"page-footer' + footer + '\">' +
		'<div class=\"list-fotter-contentindext list-inline\">' +
		'<ul class=\"ul-footer\">' +
		'<li class=\"titulo-fotter-2\"><a target=\"_blank\" ' + hrefAccesibilidad + '>Accesibilidad</a></li>' +
		'<li class=\"titulo-fotter-2\"><a target=\"_blank\" ' + hrefCookies + '>Pol&iacute;tica de Cookies</a></li>' +
		'<li class=\"titulo-fotter-2\"><a target=\"_blank\" ' + hrefPrivacidad + '>Pol&iacute;tica de privacidad y aviso legal</a></li>' +
		'<li class=\"titulo-fotter-2\"><a target=\"_blank\" ' + hrefAyuda + '>Solicitud de ayuda</a></li>' +
		'<li class=\"titulo-fotter-2\"><a target=\"_blank\" ' + hrefMapa + '>Mapa de la Sede</a></li>' +
		'</ul>' +
		'</div>' +
		'<div class=\"footer-copyright text-center py-3\">' +
		'<span id=\"year\" class=\"text-copy\">' + copyright + '</span>' +
		'</div>' +
		'</footer>';

	document.write(footer);

	if (fijo) $('body').css('marginBottom', $('#piePagina').height() + 10); /* Los 10px son de margen */

	//Chatbot
	var chatbotPRE = '<script src="https://widget.flowxo.com/embed.js" data-fxo-widget="eyJ0aGVtZSI6IiMwMDhmZTgiLCJ3ZWIiOnsiYm90SWQiOiI2MTA3ZDI0YTdjZWUxMjRmYjkwMDQzNmYiLCJ0aGVtZSI6IiMwMDhmZTgiLCJsYWJlbCI6IsK/TmVjZXNpdGFzIGF5dWRhPyJ9LCJ3ZWxjb21lVGV4dCI6IkFzaXN0ZW50ZSB2aXJ0dWFsIGRlIEFFU0EiLCJwb3B1cEhlaWdodCI6IjcwJSJ9" async defer></script>';
	var chatbotPRO = '<script src="https://widget.flowxo.com/embed.js" data-fxo-widget="eyJ0aGVtZSI6IiMwMDhmZTgiLCJ3ZWIiOnsiYm90SWQiOiI2MTI1ZDhiNjVmNWVkMjAwN2UyMjk4OGMiLCJ0aGVtZSI6IiMwMDhmZTgiLCJsYWJlbCI6IsK/TmVjZXNpdGFzIGF5dWRhPyJ9LCJ3ZWxjb21lVGV4dCI6IkFzaXN0ZW50ZSB2aXJ0dWFsIGRlIEFFU0EiLCJwb3B1cEhlaWdodCI6IjcwJSJ9" async defer></script>';

	if (window.location.hostname.includes("-aesa") ||
		window.location.hostname.includes("aplicaciones-pre") ||
		window.location.hostname.includes("idov-pre.aviacion") ||
		window.location.hostname.includes("presede")) {		
			document.write(chatbotPRE);
	} else {
			document.write(chatbotPRO);
	}

	return true;
}

function footerAccesible(fijo) {
	var footer = '';

	if (window.location.hostname.includes("-aesa") ||
		window.location.hostname.includes("aplicaciones-pre") ||
		window.location.hostname.includes("pre.aviacion") ||
		window.location.hostname.includes("presede")) {			
			var hrefAccesibilidad = "href=https://presede.seguridadaerea.gob.es/sede-aesa/accesibilidad";
			var hrefCookies = "href=https://presede.seguridadaerea.gob.es/sede-aesa/contenido/pol\u00EDtica-de-cookies";
			var hrefPrivacidad = "href=https://preportal.seguridadaerea.gob.es/es/politica-de-privacidad-y-aviso-legal";
			var hrefAyuda = "href=https://presede.seguridadaerea.gob.es/sede-aesa/contenido/solicitud-de-ayuda-0";
			var hrefMapa = "href=https://presede.seguridadaerea.gob.es/sede-aesa/sitemap";		
	} else {
			var hrefAccesibilidad = "href=https://sede.seguridadaerea.gob.es/sede-aesa/accesibilidad";
			var hrefCookies = "href=https://sede.seguridadaerea.gob.es/sede-aesa/contenido/pol\u00EDtica-de-cookies";
			var hrefPrivacidad = "href=https://www.seguridadaerea.gob.es/es/politica-de-privacidad-y-aviso-legal";
			var hrefAyuda = "href=https://sede.seguridadaerea.gob.es/sede-aesa/contenido/solicitud-de-ayuda-0";
			var hrefMapa = "href=https://sede.seguridadaerea.gob.es/sede-aesa/sitemap";
	}

	var copyright = '&copy' + (new Date()).getFullYear() + ' Agencia Estatal de Seguridad A&eacuterea';


	if (fijo) footer = ' navbar-fixed-bottom';


	footer = '<footer id=\"footer\" class=\"text-center mb-0\">' +
		'<div class=\"d-flex py-2\">' +
		'<ul class=\"mx-auto list-group list-group-horizontal-md\">' +
		'<li class=\"list-group-item py-1 bg-transparent border-0\">' +
		'<a class=\"text-white\" title=\"Este enlace abrirÃ¡ una ventana nueva\" ' + hrefAccesibilidad + ' target=\"_blank\">Accesibilidad</a>' +
		'</li>' +
		'<li class=\"list-group-item py-1 bg-transparent border-0\">' +
		'<a class=\"text-white\" title=\"Este enlace abrirÃ¡ una ventana nueva\" ' + hrefCookies + ' target=\"_blank\">Pol&iacute;tica de Cookies</a>' +
		'</li>' +
		'<li class=\"list-group-item py-1 bg-transparent border-0\">' +
		'<a class=\"text-white\" title=\"Este enlace abrirÃ¡ una ventana nueva\" ' + hrefPrivacidad + ' target=\"_blank\">Pol&iacute;tica de privacidad y aviso legal</a>' +
		'</li>' +
		'<li class=\"list-group-item py-1 bg-transparent border-0\">' +
		'<a class=\"text-white\" title=\"Este enlace abrirÃ¡ una ventana nueva\" ' + hrefAyuda + ' target=\"_blank\">Solicitud de ayuda</a>' +
		'</li>' +
		'<li class=\"list-group-item py-1 bg-transparent border-0\">' +
		'<a class=\"text-white\" title=\"Este enlace abrirÃ¡ una ventana nueva\" ' + hrefMapa + ' target=\"_blank\">Mapa de la Sede</a>' +
		'</li>' +
		'</ul>' +
		'</div>' +
		'<div class=\"text-center text-white py-2\">' +
		'<p class=\"mb-0\">' + copyright + '</p>' +
		'</div>' +
		'</footer>';


	document.write(footer);

	if (fijo) $('body').css('marginBottom', $('#piePagina').height() + 10); /* Los 10px son de margen */

	//Chatbot
	var chatbotPRE = '<script src="https://widget.flowxo.com/embed.js" data-fxo-widget="eyJ0aGVtZSI6IiMwMDhmZTgiLCJ3ZWIiOnsiYm90SWQiOiI2MTA3ZDI0YTdjZWUxMjRmYjkwMDQzNmYiLCJ0aGVtZSI6IiMwMDhmZTgiLCJsYWJlbCI6IsK/TmVjZXNpdGFzIGF5dWRhPyJ9LCJ3ZWxjb21lVGV4dCI6IkFzaXN0ZW50ZSB2aXJ0dWFsIGRlIEFFU0EiLCJwb3B1cEhlaWdodCI6IjcwJSJ9" async defer></script>';
	var chatbotPRO = '<script src="https://widget.flowxo.com/embed.js" data-fxo-widget="eyJ0aGVtZSI6IiMwMDhmZTgiLCJ3ZWIiOnsiYm90SWQiOiI2MTI1ZDhiNjVmNWVkMjAwN2UyMjk4OGMiLCJ0aGVtZSI6IiMwMDhmZTgiLCJsYWJlbCI6IsK/TmVjZXNpdGFzIGF5dWRhPyJ9LCJ3ZWxjb21lVGV4dCI6IkFzaXN0ZW50ZSB2aXJ0dWFsIGRlIEFFU0EiLCJwb3B1cEhlaWdodCI6IjcwJSJ9" async defer></script>';

	if (window.location.hostname.includes("-aesa") ||
		window.location.hostname.includes("aplicaciones-pre") ||
		window.location.hostname.includes("pre.aviacion") ||
		window.location.hostname.includes("presede")) {		
			document.write(chatbotPRE);
	} else {
			document.write(chatbotPRO);
	}

	return true;
}


function footerAccesibleEN(fijo) {
	var footer = '';
	
	if (window.location.hostname.includes("-aesa") ||
		window.location.hostname.includes("aplicaciones-pre") ||
		window.location.hostname.includes("pre.aviacion") ||
		window.location.hostname.includes("presede")) {				
			var hrefAccesibilidad = "href=https://presede.seguridadaerea.gob.es/sede-aesa/accesibilidad";
			var hrefCookies = "href=https://presede.seguridadaerea.gob.es/sede-aesa/contenido/pol\u00EDtica-de-cookies";
			var hrefPrivacidad = "href=https://preportal.seguridadaerea.gob.es/es/politica-de-privacidad-y-aviso-legal";
			var hrefAyuda = "href=https://presede.seguridadaerea.gob.es/sede-aesa/contenido/solicitud-de-ayuda-0";
			var hrefMapa = "href=https://presede.seguridadaerea.gob.es/sede-aesa/sitemap";		
	} else {
			var hrefAccesibilidad = "href=https://sede.seguridadaerea.gob.es/sede-aesa/accesibilidad";
			var hrefCookies = "href=https://sede.seguridadaerea.gob.es/sede-aesa/contenido/pol\u00EDtica-de-cookies";
			var hrefPrivacidad = "href=https://www.seguridadaerea.gob.es/es/politica-de-privacidad-y-aviso-legal";
			var hrefAyuda = "href=https://sede.seguridadaerea.gob.es/sede-aesa/contenido/solicitud-de-ayuda-0";
			var hrefMapa = "href=https://sede.seguridadaerea.gob.es/sede-aesa/sitemap";
	}

	var copyright = '&copy' + (new Date()).getFullYear() + ' Agencia Estatal de Seguridad A&eacuterea';


	if (fijo) footer = ' navbar-fixed-bottom';


	footer = '<footer id=\"footer\" class=\"text-center mb-0\">' +
		'<div class=\"d-flex py-2\">' +
		'<ul class=\"mx-auto list-group list-group-horizontal-md\">' +
		'<li class=\"list-group-item py-1 bg-transparent border-0\">' +
		'<a class=\"text-white\" title=\"This link opens in a new window\" ' + hrefAccesibilidad + ' target=\"_blank\">Accessibility</a>' +
		'</li>' +
		'<li class=\"list-group-item py-1 bg-transparent border-0\">' +
		'<a class=\"text-white\" title=\"Este enlace abrirÃ¡ una ventana nueva\" ' + hrefCookies + ' target=\"_blank\">Cookies policy</a>' +
		'</li>' +
		'<li class=\"list-group-item py-1 bg-transparent border-0\">' +
		'<a class=\"text-white\" title=\"This link opens in a new window\" ' + hrefPrivacidad + ' target=\"_blank\">Privacy policy and legal warning</a>' +
		'</li>' +
		'<li class=\"list-group-item py-1 bg-transparent border-0\">' +
		'<a class=\"text-white\" title=\"This link opens in a new window\" ' + hrefAyuda + ' target=\"_blank\">Help</a>' +
		'</li>' +
		'<li class=\"list-group-item py-1 bg-transparent border-0\">' +
		'<a class=\"text-white\" title=\"This link opens in a new window\" ' + hrefMapa + ' target=\"_blank\">Site map</a>' +
		'</li>' +
		'</ul>' +
		'</div>' +
		'<div class=\"text-center text-white py-2\">' +
		'<p class=\"mb-0\">' + copyright + '</p>' +
		'</div>' +
		'</footer>';


	document.write(footer);

	if (fijo) $('body').css('marginBottom', $('#piePagina').height() + 10); /* Los 10px son de margen */

	//Chatbot
	var chatbotPRE = '<script src="https://widget.flowxo.com/embed.js" data-fxo-widget="eyJ0aGVtZSI6IiMwMDhmZTgiLCJ3ZWIiOnsiYm90SWQiOiI2MTA3ZDI0YTdjZWUxMjRmYjkwMDQzNmYiLCJ0aGVtZSI6IiMwMDhmZTgiLCJsYWJlbCI6IsK/TmVjZXNpdGFzIGF5dWRhPyJ9LCJ3ZWxjb21lVGV4dCI6IkFzaXN0ZW50ZSB2aXJ0dWFsIGRlIEFFU0EiLCJwb3B1cEhlaWdodCI6IjcwJSJ9" async defer></script>';
	var chatbotPRO = '<script src="https://widget.flowxo.com/embed.js" data-fxo-widget="eyJ0aGVtZSI6IiMwMDhmZTgiLCJ3ZWIiOnsiYm90SWQiOiI2MTI1ZDhiNjVmNWVkMjAwN2UyMjk4OGMiLCJ0aGVtZSI6IiMwMDhmZTgiLCJsYWJlbCI6IsK/TmVjZXNpdGFzIGF5dWRhPyJ9LCJ3ZWxjb21lVGV4dCI6IkFzaXN0ZW50ZSB2aXJ0dWFsIGRlIEFFU0EiLCJwb3B1cEhlaWdodCI6IjcwJSJ9" async defer></script>';
	
	if (window.location.hostname.includes("-aesa") ||
		window.location.hostname.includes("aplicaciones-pre") ||
		window.location.hostname.includes("pre.aviacion") ||
		window.location.hostname.includes("presede")) {		
			document.write(chatbotPRE);
	} else {
			document.write(chatbotPRO);
	}

	return true;
}


function footerAccesibleDual(fijo) {
	var footer = '';
	
	if (window.location.hostname.includes("-aesa") ||
		window.location.hostname.includes("aplicaciones-pre") ||
		window.location.hostname.includes("pre.aviacion") ||
		window.location.hostname.includes("presede")) {				
			var hrefAccesibilidad = "href=https://presede.seguridadaerea.gob.es/sede-aesa/accesibilidad";
			var hrefCookies = "href=https://presede.seguridadaerea.gob.es/sede-aesa/contenido/pol\u00EDtica-de-cookies";
			var hrefPrivacidad = "href=https://preportal.seguridadaerea.gob.es/es/politica-de-privacidad-y-aviso-legal";
			var hrefAyuda = "href=https://presede.seguridadaerea.gob.es/sede-aesa/contenido/solicitud-de-ayuda-0";
			var hrefMapa = "href=https://presede.seguridadaerea.gob.es/sede-aesa/sitemap";		
	} else {
			var hrefAccesibilidad = "href=https://sede.seguridadaerea.gob.es/sede-aesa/accesibilidad";
			var hrefCookies = "href=https://sede.seguridadaerea.gob.es/sede-aesa/contenido/pol\u00EDtica-de-cookies";
			var hrefPrivacidad = "href=https://www.seguridadaerea.gob.es/es/politica-de-privacidad-y-aviso-legal";
			var hrefAyuda = "href=https://sede.seguridadaerea.gob.es/sede-aesa/contenido/solicitud-de-ayuda-0";
			var hrefMapa = "href=https://sede.seguridadaerea.gob.es/sede-aesa/sitemap";
	}

	var copyright = '&copy' + (new Date()).getFullYear() + ' Agencia Estatal de Seguridad A&eacuterea';


	if (fijo) footer = ' navbar-fixed-bottom';


	footer = '<footer id=\"footer\" class=\"text-center mb-0\">' +
		'<div class=\"d-flex py-2\ footerAccesibilidad" id=\"footerEs\">' +
		'<ul class=\"mx-auto list-group list-group-horizontal-md\">' +
		'<li class=\"list-group-item py-1 bg-transparent border-0\">' +
		'<a class=\"text-white\" title=\"Este enlace abrirÃ¡ una ventana nueva\" ' + hrefAccesibilidad + ' target=\"_blank\">Accesibilidad</a>' +
		'</li>' +
		'<li class=\"list-group-item py-1 bg-transparent border-0\">' +
		'<a class=\"text-white\" title=\"Este enlace abrirÃ¡ una ventana nueva\" ' + hrefCookies + ' target=\"_blank\">Pol&iacute;tica de Cookies</a>' +
		'</li>' +
		'<li class=\"list-group-item py-1 bg-transparent border-0\">' +
		'<a class=\"text-white\" title=\"Este enlace abrirÃ¡ una ventana nueva\" ' + hrefPrivacidad + ' target=\"_blank\">Pol&iacute;tica de privacidad y aviso legal</a>' +
		'</li>' +
		'<li class=\"list-group-item py-1 bg-transparent border-0\">' +
		'<a class=\"text-white\" title=\"Este enlace abrirÃ¡ una ventana nueva\" ' + hrefAyuda + ' target=\"_blank\">Solicitud de ayuda</a>' +
		'</li>' +
		'<li class=\"list-group-item py-1 bg-transparent border-0\">' +
		'<a class=\"text-white\" title=\"Este enlace abrirÃ¡ una ventana nueva\" ' + hrefMapa + ' target=\"_blank\">Mapa de la Sede</a>' +
		'</li>' +
		'</ul>' +
		'</div>' +
		'<div class=\"d-flex py-2\ footerAccesibilidad" id=\"footerEn\">' +
		'<ul class=\"mx-auto list-group list-group-horizontal-md\">' +
		'<li class=\"list-group-item py-1 bg-transparent border-0\">' +
		'<a class=\"text-white\" title=\"This link opens in a new window\" ' + hrefAccesibilidad + ' target=\"_blank\">Accessibility</a>' +
		'</li>' +
		'<li class=\"list-group-item py-1 bg-transparent border-0\">' +
		'<a class=\"text-white\" title=\"Este enlace abrirÃ¡ una ventana nueva\" ' + hrefCookies + ' target=\"_blank\">Cookies policy</a>' +
		'</li>' +
		'<li class=\"list-group-item py-1 bg-transparent border-0\">' +
		'<a class=\"text-white\" title=\"This link opens in a new window\" ' + hrefPrivacidad + ' target=\"_blank\">Privacy policy and legal warning</a>' +
		'</li>' +
		'<li class=\"list-group-item py-1 bg-transparent border-0\">' +
		'<a class=\"text-white\" title=\"This link opens in a new window\" ' + hrefAyuda + ' target=\"_blank\">Help</a>' +
		'</li>' +
		'<li class=\"list-group-item py-1 bg-transparent border-0\">' +
		'<a class=\"text-white\" title=\"This link opens in a new window\" ' + hrefMapa + ' target=\"_blank\">Site map</a>' +
		'</li>' +
		'</ul>' +
		'</div>' +
		'<div class=\"text-center text-white py-2\">' +
		'<p class=\"mb-0\">' + copyright + '</p>' +
		'</div>' +
		'</footer>';


	document.write(footer);

	if (fijo) $('body').css('marginBottom', $('#piePagina').height() + 10); /* Los 10px son de margen */

	//Chatbot
	var chatbotPRE = '<script src="https://widget.flowxo.com/embed.js" data-fxo-widget="eyJ0aGVtZSI6IiMwMDhmZTgiLCJ3ZWIiOnsiYm90SWQiOiI2MTA3ZDI0YTdjZWUxMjRmYjkwMDQzNmYiLCJ0aGVtZSI6IiMwMDhmZTgiLCJsYWJlbCI6IsK/TmVjZXNpdGFzIGF5dWRhPyJ9LCJ3ZWxjb21lVGV4dCI6IkFzaXN0ZW50ZSB2aXJ0dWFsIGRlIEFFU0EiLCJwb3B1cEhlaWdodCI6IjcwJSJ9" async defer></script>';
	var chatbotPRO = '<script src="https://widget.flowxo.com/embed.js" data-fxo-widget="eyJ0aGVtZSI6IiMwMDhmZTgiLCJ3ZWIiOnsiYm90SWQiOiI2MTI1ZDhiNjVmNWVkMjAwN2UyMjk4OGMiLCJ0aGVtZSI6IiMwMDhmZTgiLCJsYWJlbCI6IsK/TmVjZXNpdGFzIGF5dWRhPyJ9LCJ3ZWxjb21lVGV4dCI6IkFzaXN0ZW50ZSB2aXJ0dWFsIGRlIEFFU0EiLCJwb3B1cEhlaWdodCI6IjcwJSJ9" async defer></script>';

	if (window.location.hostname.includes("-aesa") ||
		window.location.hostname.includes("aplicaciones-pre") ||
		window.location.hostname.includes("pre.aviacion") ||
		window.location.hostname.includes("presede")) {		
			document.write(chatbotPRE);
	} else {
			document.write(chatbotPRO);
	}

	return true;
}



function estadisticasWeb(idSite) {
	var trackingId;
	var analyticsJs;

	if (window.location.hostname.includes("-aesa") ||
		window.location.hostname.includes("aplicaciones-pre") ||
		window.location.hostname.includes("pre.aviacion") ||
		window.location.hostname.includes("presede")) {
			trackingId = 'UA-152001370-1';
	}
	else {
			trackingId = 'UA-152001370-2';
	}

	analyticsJs = '<!-- Global site tag (gtag.js) - Google Analytics -->' +
		'<script async src="https://www.googletagmanager.com/gtag/js?id=' + trackingId + '"></script>' +
		'<script>' +
		'  window.dataLayer = window.dataLayer || [];' +
		'  function gtag(){dataLayer.push(arguments);}' +
		'  gtag(\'js\', new Date());' +
		'  gtag(\'config\', \'' + trackingId + '\');' +
		'</script>';

	document.write(analyticsJs);

	return true;
}