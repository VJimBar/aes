<%@page import="org.slf4j.LoggerFactory"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="es.aesa.fire.expedientes.webapp.SignHelper"%>

<%@ page import="java.util.Properties" %>
<%@ page import="es.clave.sp.Constants" %>
<%@ page import="es.clave.sp.SPUtil" %>



<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Recuperar firma</title>
</head>
<body>
	<%   
	     Properties configs = SPUtil.loadSPConfigs();
	     String location =configs.getProperty(Constants.URL_LOCATION);
	    		 
	     response.sendRedirect(location + "fPresentado?OpenForm&IDSOL=" + session.getAttribute("idSol") + "&IDGR=" + request.getAttribute("idAsiento"));
	%>
</body>
</html>
</html>