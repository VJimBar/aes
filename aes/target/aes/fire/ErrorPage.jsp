<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.LocalDate" %>

<html lang="es">
<head>
<title>Página de Error</title>
<jsp:include page="htmlHead.jsp"/>
	
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=10" />


<link rel='stylesheet' href='https://sede.seguridadaerea.gob.es/sede-aesa/sites/aesa_sede/themes/aesa/css/bootstrap.min.css?riz64h' type='text/css' />
<link rel=stylesheet type='text/css' href='./resources/skin0/css/main.css'>
<link rel=stylesheet type='text/css' href='./resources/skin0/css/OV.css'>
<link rel=stylesheet type='text/css' href='./resources/skin0/css/comun.css'>
<link rel=stylesheet type='text/css' href='./resources/skin0/css/estilo.css'>

<script type="text/javascript" src="./resources/skin0/js/footer.js"></script>

</head>

<body>

<table width=100% border=0>
  <tr><td colspan=5 height=8px></td></tr>
  <tr>
    <td width=5%></td>
    <td width=32% valign="bottom">
    <a id="aAESA" href="https://sede.seguridadaerea.gob.es/sede-aesa/"> <img 
    src="./resources/skin0/img/imagenlogocabecera.png"
	alt="Logotipo Corporativo de la Sede Electrónica AESA" width="90%" height="90%">
	</a></td>
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
  <%
  	String message = request.getParameter("msg"); //$NON-NLS-1$
  	if (message != null) {
  		message = message.toUpperCase();
  	}else{
  		message="El sistema ha cerrado la sesión. Salga de la aplicación y vuelva a entrar identficándose de nuevo.";
  	}
  %>
 	
	<div id="container">
		<div id="error-txt">	
			<p style="text-align:center">
   				<%= message %> 
  			</p>
  		</div>
  		<form method="POST" action="Sign.jsp">
			<div style="margin-top:30px;text-align: center; ">
				<label for="submit-btn">Pulse el bot&oacute;n para realizar una nueva firma</label><br><br>
				<input  id="submit-btn"  type="submit" value="NUEVA FIRMA">
			</div>
		</form>
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