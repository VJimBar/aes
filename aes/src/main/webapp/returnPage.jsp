<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="java.util.Properties" %>
<%@ page import="es.clave.sp.Constants" %>
<%@ page import="es.clave.sp.SPUtil" %>
<%@ page import="java.io.IOException" %>
<%@ page import="org.apache.commons.text.StringEscapeUtils" %>


<html lang="es">
<head>
<title>AESA-Agencia Estatal de Seguridad Aérea - Ministerio de Fomento</title>
<jsp:include page="htmlHead.jsp"/>
</head>
<body>

	<%
	     session.setAttribute("ID", request.getAttribute("ID"));
	     Properties configs = SPUtil.loadSPConfigs();
	     
	     // Se redirige al usuario
		 String location =configs.getProperty(Constants.URL_LOCATION) + "fAccesoOV?OpenForm&ID=" +  StringEscapeUtils.escapeHtml4(request.getAttribute("ID").toString());
		 
	     response.sendRedirect(location);
	     
	     //response.sendRedirect("https://aplicaciones.aviacion.fomento.es/sancionadoresov/SancionesAESA/Tramitador/ExpSancOficinaVirtual.nsf/fAccesoOV?OpenForm&ID=" + request.getAttribute("ID"));
	%>
	
</body>
</html>