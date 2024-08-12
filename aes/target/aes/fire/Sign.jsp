<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE>
<%@ page import="java.io.FileReader" %>
<%@ page import="java.util.Properties" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.time.LocalDateTime"%>
<%@ page import="java.time.format.DateTimeFormatter" %>

<html lang="es">


  
<head>
<title>Firmar Documento</title>
<jsp:include page="htmlHead.jsp" />
	
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=10" />
<link rel='stylesheet' href='https://sede.seguridadaerea.gob.es/sede-aesa/sites/aesa_sede/themes/aesa/css/bootstrap.min.css?riz64h' type='text/css' />
<link rel=stylesheet type='text/css' href='./resources/skin0/css/main.css'>
<link rel=stylesheet type='text/css' href='./resources/skin0/css/OV.css'>
<link rel=stylesheet type='text/css' href='./resources/skin0/css/comun.css'>
<link rel=stylesheet type='text/css' href='./resources/skin0/css/estilo.css'>

<script type="text/javascript" src="js/footer.js"></script>

</head>

<body>

<table width=100% border=0>
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

	<!-- fin title page aesa -->
  
   <div class="window-header fixd_12" align="center">
		<h2>Redirigiendo...</h2>
		<span><img src="./resources/skin0/img/loading.gif" alt="Loading"
			title="Loading" /></span>
   
	<form  id="firmar" name="firmar" action="signatureService" method="post">
		<input id="subjectId" type="hidden" value="${subjectId}" name="subjectId" />
		<input id="numExpediente" type="hidden" value="${numExpediente}" name="numExpediente" />
		<input id="idSol" type="hidden" value="${idSol}" name="idSol" />
		<input id="subjectNom" type="hidden" value="${subjectNom}" name="subjectNom" />	
		<input id="e4f" type="hidden" value="${e4f}" name="e4f" />		
	</form>
	
	<form  id="firmarE4f" name="firmarE4f" action="firmae4f">
		<input id="subjectId" type="hidden" value="${subjectId}" name="subjectId" />
		<input id="numExpediente" type="hidden" value="${numExpediente}" name="numExpediente" />
		<input id="idSol" type="hidden" value="${idSol}" name="idSol" />
		<input id="subjectNom" type="hidden" value="${subjectNom}" name="subjectNom" />	
		<input id="e4f" type="hidden" value="${e4f}" name="e4f" />			
	</form>
	
	<noscript>
	 <form  id="firmar" name="firmar" action="signatureService" method="post">
		<input id="subjectId" type="hidden" value="${subjectId}" name="subjectId" />
		<input id="numExpediente" type="hidden" value="${numExpediente}" name="numExpediente" />
		<input id="idSol" type="hidden" value="${idSol}" name="idSol" />
		<input id="subjectNom" type="hidden" value="${subjectNom}" name="subjectNom" />	
		<input id="e4f" type="hidden" value="${e4f}" name="e4f" />	

				<p class="box-btn">
					<input type="submit" id="redirectValue_button" class="btn btn-next"
						value="Enviar" />
				</p>
	 </form>
	 
	  <form  id="firmarE4f" name="firmarE4f" action="firmae4f">
		<input id="subjectId" type="hidden" value="${subjectId}" name="subjectId" />
		<input id="numExpediente" type="hidden" value="${numExpediente}" name="numExpediente" />
		<input id="idSol" type="hidden" value="${idSol}" name="idSol" />
		<input id="subjectNom" type="hidden" value="${subjectNom}" name="subjectNom" />	
		<input id="e4f" type="hidden" value="${e4f}" name="e4f" />	

				<p class="box-btn">
					<input type="submit" id="redirectValue_button" class="btn btn-next"
						value="Enviar" />
				</p>
	 </form>
  </noscript>
	
	<% 
	Date date= new Date();
	
	System.out.println("\n" + date + " subjectId: " + request.getAttribute("subjectId") + "\n");
	
	if(request.getAttribute("subjectId")!=null && request.getAttribute("samlId")!=null){ 
		 
	   if(request.getAttribute("e4f")!=null && request.getAttribute("e4f").equals("false")){
	     %>
	       <script type="text/javascript" src="js/redirectFirmOnload.js"></script>
	       
	   <% } else {%>
	       <script type="text/javascript" src="js/redirectFirmE4fOnload.js"></script>
       <% }} else {
    	   
    	   request.setAttribute("Title","SAML_VALIDATION_ERROR");
    	   request.setAttribute("Message", "El sistema ha cerrado la sesión. Salga de la aplicación y vuelva a entrar identficándose de nuevo.");
    	   String dateTime= DateTimeFormatter.ofPattern("dd/MM/yy hh:mm:ss").format(LocalDateTime.now());
		   request.setAttribute("Date", dateTime);
		   
    	   RequestDispatcher dispatcher = request.getRequestDispatcher("/errorPage.jsp");
		   dispatcher.forward(request, response);
		  } %>
    
</body>
</html>