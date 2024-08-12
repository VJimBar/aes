<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.time.LocalDate" %>

<html lang="es">
<head>
<title>Página de error</title>
<jsp:include page="htmlHead.jsp"/>
	
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=10" />

<link rel='stylesheet' href='https://sede.seguridadaerea.gob.es/sede-aesa/sites/aesa_sede/themes/aesa/css/bootstrap.min.css?riz64h' type='text/css' />
<link rel=stylesheet type='text/css' href='resources/skin0/css/main.css'>
<link rel=stylesheet type='text/css' href='resources/skin0/css/OV.css'>
<link rel=stylesheet type='text/css' href='resources/skin0/css/comun.css'>
<link rel=stylesheet type='text/css' href='resources/skin0/css/estilo.css'>


<script type="text/javascript" src="./resources/skin0/js/footer.js"></script>


  <script type="text/javascript">
    var locale = 'es';
    var languajeTable = '';
  </script>
  <script>
  jQuery.browser = {};
  jQuery.browser.mozilla = /mozilla/.test(navigator.userAgent.toLowerCase()) && !/webkit/.test(navigator.userAgent.toLowerCase());
  jQuery.browser.webkit = /webkit/.test(navigator.userAgent.toLowerCase());
  jQuery.browser.opera = /opera/.test(navigator.userAgent.toLowerCase());
  jQuery.browser.msie = /msie/.test(navigator.userAgent.toLowerCase());
  
	function redimensiona(){
		$("#interruptor").toggleClass( "abrir", [2] );
		$("#lateral").toggleClass( "lateral_cerrado", [2] );
		$("#visor").toggleClass( "visor_abierto", [2] );
	}
	

	$(document).ready(function() {
		$("#boton_inicio").click(function(){
			$( "#menu_inicio" ).toggle( "slide", {direction: "up", easing:"linear" }, 250 );
			

		});
	});

  </script>

</head>
<body>

	<!-- cabecera aesa -->

	<div>
		<div id="cabeceraSede">
			<div class="container-fluid logo-container">
				<div class="row">
					<div class="col-md-11 content-logo-from">
						<div class="col-md-4 logo-img-top-header">
							<a href="https://sede.seguridadaerea.gob.es/sede-aesa/"><img
								src="./resources/skin0/img/imagenlogocabecera.png"
								alt="Logotipo Corporativo de la Sede Electrónica AESA"
								class="img-responsive logo-img-ipad-vertical"></a>
						</div>

						<div class="col-md-4">
							<img
								src="./resources/skin0/img/sede-electronica-titulo.png"
								alt="Logotipo Sede Electrónica AESA"
								class="img-responsive siluetas">

						</div>

					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- fin cabecera aesa -->
	
	<!-- title page aesa -->

	<div class="container-fluid container-informacion-general">
		<div class="row">
			<p class="title-formulario">
				<strong class="blood-title">Oficina Virtual</strong>
			</p>
		</div>
	</div>

	<!-- fin title page aesa -->
	
	<div>
		<div>
			<div class="container-fluid container-informacion-general">
				<div class="row">
					<div class="col-sm-12 container-center-informacion">
						<br>
						<div class="error text-center">
							<div class="error">
								<h1 class="text-center">
									Lo sentimos, ha ocurrido un error
								</h1>

								<h2 class="text-center">
									<span class="error-page-subtitle"> 									
												
										Mensaje de error: ${Message}&nbsp;
										
										<br>
															
										Fecha y Hora: ${Date} &nbsp;
										
									</span>

								</h2>
                            <p>
								Este error pudo ser debido a un corte puntual en la comunicación con AESA. Pulse
									
												<a href="./populateIndexPage">
													aquí
												</a>
												para volver a la página y continuar con su tramitación
												<br>

							</p>

							<p>
								Para informar de este error, contacte con nuestro Centro de Atención al Usuario a través del formulario de

								<a
									href="https://sede.seguridadaerea.gob.es/sede-aesa/contenido/solicitud-de-ayuda-0">
									Solicitud de Ayuda
								</a>


								, incluyendo la información de este mensaje y toda la información que pueda aportar sobre lo que estaba realizando en la aplicación cuando se produjo el error.
							</p>
							</div>

						<br> <br> <br>
					</div>
				</div>
			</div>
			<br>
		</div>
		<br>
		<br>
		<br>
		</div>
		<br>
	
	</div>

<!-- Footer -->
	<footer class="text-center mb-0">
        <div class="d-flex py-2">
          <ul class="mx-auto list-group list-group-horizontal-md">
            <li class="list-group-item py-1 bg-transparent border-0">
              <a class="text-white" title="Este enlace se abrirá en una nueva ventana" href="https://sede.seguridadaerea.gob.es/sede-aesa/accesibilidad" target="_blank">Accesibilidad</a>
            </li>
            <li class="list-group-item py-1 bg-transparent border-0">
              <a class="text-white" title="Este enlace se abrirá en una nueva ventana" href="https://sede.seguridadaerea.gob.es/sede-aesa/contenido/pol%C3%ADtica-de-cookies" target="_blank">Política de Cookies</a>
            </li>
            <li class="list-group-item py-1 bg-transparent border-0">
              <a class="text-white" title="Este enlace se abrirá en una nueva ventana" href="https://www.seguridadaerea.gob.es/es/politica-de-privacidad-y-aviso-legal" target="_blank">Política de privacidad</a>
            </li>
            <li class="list-group-item py-1 bg-transparent border-0">
              <a class="text-white" title="Este enlace se abrirá en una nueva ventana" href="https://sede.seguridadaerea.gob.es/sede-aesa/contenido/solicitud-de-ayuda-0" target="_blank">Solicitud de ayuda</a>
            </li>
            <li class="list-group-item py-1 bg-transparent border-0">
              <a class="text-white" title="Este enlace se abrirá en una nueva ventana" href="https://sede.seguridadaerea.gob.es/sede-aesa/sitemap" target="_blank">Mapa Web</a>
            </li>
            </ul>
        </div>
        <div class="text-center pb-2">
          <p class="text-white">© <%= Integer.toString (LocalDate.now().getYear()) %> Agencia Estatal de Seguridad Aérea</p>
        </div>
     </footer>
     
     <script type="text/javascript" src="https://widget.flowxo.com/embed.js" data-fxo-widget="eyJ0aGVtZSI6IiMwMDhmZTgiLCJ3ZWIiOnsiYm90SWQiOiI2MTA3ZDI0YTdjZWUxMjRmYjkwMDQzNmYiLCJ0aGVtZSI6IiMwMDhmZTgiLCJsYWJlbCI6IsK/TmVjZXNpdGFzIGF5dWRhPyJ9LCJ3ZWxjb21lVGV4dCI6IkFzaXN0ZW50ZSB2aXJ0dWFsIGRlIEFFU0EiLCJwb3B1cEhlaWdodCI6IjcwJSJ9" defer></script>
    
     
</body>	
</html>
