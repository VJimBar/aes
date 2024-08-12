<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.LocalDate" %>

<html lang="es">
<head>
<title>AESA Oficina Virtual de Expedientes Sancionadores</title>
<jsp:include page="htmlHead.jsp" />
	
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<link rel='stylesheet' href='https://sede.seguridadaerea.gob.es/sede-aesa/sites/aesa_sede/themes/aesa/css/bootstrap.min.css?riz64h' type='text/css' />
<link rel=stylesheet type='text/css' href='./resources/skin0/css/main.css'>
<link rel=stylesheet type='text/css' href='./resources/skin0/css/OV.css'>
<link rel=stylesheet type='text/css' href='./resources/skin0/css/comun.css'>
<link rel=stylesheet type='text/css' href='./resources/skin0/css/estilo.css'>
<link rel="stylesheet" type="text/css" href="./resources/skin0/css/bootstrap5.2.0.min.css" />
<link rel="stylesheet" type="text/css" href="./resources/skin0/css/bootstrap5.2.0-extra.css" />
<link rel="stylesheet" type="text/css" href="./resources/skin0/css/menu-accesibilidad.css" />

<script type="text/javascript" src="./resources/skin0/js/bootstrap5.2.0.bundle.min.js"></script>
<script type="text/javascript" src="./resources/skin0/js/footer.js"></script>
<script type="text/javascript" src="./resources/skin0/js/main.js"></script>

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
	
	
 }

  </script>
  
  <script type="text/javascript">
	
		function accesoClave() {
			var form = document.getElementById("formTab2");
			form.submit();
			
		}
		function accesoClavesConcertadas() {
			var form = document.getElementById("formTab3");
			form.submit();
		}
	
	</script>

</head>
<body>
  
 <!-- <header class="shadow container-fluid">
	<div id="enlaces-saltos" class="mt-0 py-2 row visually-hidden-focusable">
		<ul class="list-group list-group-horizontal-md">
			<li class="list-group-item py-1 bg-transparent"><a class="text-white" href="#menu">Saltar al menú</a></li>
			<li class="list-group-item py-1 bg-transparent"><a class="text-white" href="#content">Saltar al contenido principal</a></li>
			<li class="list-group-item py-1 bg-transparent"><a class="text-white" href="#footer">Saltar al pie de página</a></li>
		</ul>
	</div>
</header>-->
  
	<!-- cabecera aesa -->

<table width=100% border=0>
    <caption>
    </caption>
  <tr><td colspan=5 height=8px></td></tr>
  <tr>
    <td width=5%></td>
    <td width=32% valign="bottom"><a id="aAESA" href="https://sede.seguridadaerea.gob.es/sede-aesa/"> <img 
    src="./resources/skin0/img/imagenlogocabecera.png"
	alt="Logotipo Corporativo de la Sede Electrónica AESA" width="90%" height="90%"></a></td>
    <td width=28%>
    <img src="./resources/skin0/img/sede-electronica-titulo.png"
	alt="Logotipo Sede Electrónica AESA" width="100%" height="100%"></td>
	<td align="right" valign="top">
    </td>
    <td width="4%" valign="top">
   </td>
   <td width="5%"></td>
  </tr>
</table>
	<!-- fin cabecera aesa -->
	
	<!-- title page aesa -->

	<div class="container-fluid container-informacion-general">
		<div class="row">
			<p class="title-formulario">
				<strong class="blood-title">Oficina Virtual</strong>
			</p>
		</div>
	</div>

<nav id="menu" class="mt-3 navbar navbar-expand-lg navbar-light bg-light border rounded container-fluid"
		aria-label="Menú principal">
	<!-- fin title page aesa -->
	
	
	<div class="container">
		<div>
			<br />
			<br />
			<form action="IndexPage" id="formTab2" method="post">
				<input type="hidden" id="returnUrl" name="returnUrl" value="${returnUrl}"/>
				<input type="hidden" id="providerName" name="providerName" value="${providerName}"/>
				<input type="hidden" id="spApplication" name="spApplication" value="${spApplication}"/>
				
				<div class="form-group right-content" id="eidasForceAuthDiv">
					<input type="hidden" name="forceCheck" id="forceCheck" type="checkbox" value="false"/>
				</div>
				<table class="table-options">
					<tr>
						<td><input type="hidden" id="nodeServiceUrl" name="nodeServiceUrl" value="${nodeServiceUrl}"/>
						</td>
				</tr>
				</table>
				<div class="form-group" id="disableIdPsDiv">
					<table class="table-options">
						<tr>
							<td>
							    <input name="afirmaCheck" id="afirmaCheck" type="hidden"  value="false" />
								<input name="gissCheck" id="gissCheck" type="hidden" value="false" /> 
							    <input name="aeatCheck" id="aeatCheck" type="hidden" value="true" />
								<input name="eidasCheck" id="eidasCheck" type="hidden"  value="true" />
								<input name="movilCheck" id="movilCheck" type="hidden"  value="true" />
							</td>
						</tr>
					</table>
				</div>
			</form>
			<form id="formTab3" action="logine4f">
			</form>
				<div class="row">
					<div class="col-sm-12 container-center-informacion">
						<p class="title-informacion-sede form-group">
							Para acceder a Oficina Virtual debe identificarse con algunos de los siguientes métodos
						</p>
						<div id="content" class="row">
							
								<div class="col-md-4 col-sm-4 hidden-xs" id="capaSeparador"></div>
							
							<!-- CL@VE INICIO-->
							
								<div class="col-sm-2 col-xs-12 panel-group" id="clave">
							
									<div class="capa-logo-login">
										<img
											src="resources/skin0/img/clave.png"
											alt="Icono acceso con Clave" class="img-responsive img-login">
									</div>
									<div class="panel-group">
										<button id="submit_tab2"
											class="btn btn-success btn-block"
											name="autenticarComprobacion" type="button" alt="Acceder"
											onclick="muestraCargando(); accesoClave();">
											Acceder
										</button>
					  
									</div>
									<p style="text-align: justify">
										Acceso mediante el sistema Cl@ve a través de Certificado digital o DNI electrónico, Claves PIN, Clave permanente y Ciudadanos UE.
									</p>
								</div>
							
							
							<!-- CERTIFICADO -->
							
							<!-- CLAVES CONCERTADAS -->
							
								<div class="col-sm-2 col-xs-12 panel-group"
									id="claves_concertadas">
									<div class="capa-logo-login">
										<img
											src="resources/skin0/img/logo-cc.png"
											alt="Icono acceso con Claves Concertadas"
											class="img-responsive img-login">
									</div>
									<div class="panel-group">
										<button id="submit_tab3"
											class="btn btn-primary btn-block" type="button" alt="Acceder"
											onclick="muestraCargando(); accesoClavesConcertadas();">
											Acceder
										</button>
									</div>
									<p style="text-align: justify">
										Acceso mediante el sistema e4F para personas extranjeras que no disponen de Cl@ve.<p style="text-align:center;">Para acceder es necesario <a href="https://sede.seguridadaerea.gob.es/safcce-internet/accesoSafcce" class="enlace" style="color:darkorange;text-decoration:underline">registrarse</a></p>
									</p>
								</div>
						</div>
					</div>
				</div>
			
		</div>
	</div>
	</nav>
	
	<footer id="footer" class="text-center mb-0">
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