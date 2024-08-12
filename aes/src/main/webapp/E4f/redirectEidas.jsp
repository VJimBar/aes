<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="e" uri="https://www.owasp.org/index.php/OWASP_Java_Encoder_Project" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>AESA-Agencia Estatal de Seguridad Aérea - Ministerio de Fomento</title>
	
	<link rel="stylesheet" href="<c:url value="/resources/skin0/vendors/bootstrap/css/bootstrap.min.css" />">
	<link rel="stylesheet" href="<c:url value="/resources/skin0/css/generalAESA.css" />">
	
	<!-- Para aplicaciones internas -->
	<!-- <link rel="stylesheet" href="css/estilosAesa.css"> -->
	<link rel="stylesheet" href="<c:url value="/resources/skin0/css/comun.css" />" >
	<link rel="stylesheet" href="<c:url value="/resources/skin0/css/estilosInternet.css" />" >
	<link rel="stylesheet" href="<c:url value="/resources/skin0/css/menu.css" />" >
	
	<script src="<c:url value="/resources/skin0/vendors/jquery/jquery-3.2.1.min.js" />"></script>
    <script src="<c:url value="/resources/skin0/vendors/bootstrap/js/bootstrap.min.js" />"></script>
</head>

<body >
    <div class="container-fluid">
	    
	    <form id="redirectForm" name="redirectForm" method="POST" action="${nodeUrl}" >
	    	<input type="hidden" id="SAMLRequest" name="SAMLRequest" value="${samlRequest}"/>
	    	<input type="hidden" id="relayState" name="RelayState" value="${RelayState}"> 
			<input type="hidden" id="country" name="country" value="ES"/>   	
		</form>
		<noscript>
			<form id="redirectForm" name="redirectForm" method="POST"
				action="${nodeUrl}">
				<input type="hidden" id="relayState" name="RelayState" value="${RelayState}">
			    <input type="hidden" id="SAMLRequest" name="SAMLRequest" value="${samlRequest}" />
                <input type="hidden" id="country" name="country" value="ES"/> 
				<p class="box-btn">
					<input type="submit" id="redirectValue_button" class="btn btn-next"
						value="Enviar" />
				</p>
			</form>
		</noscript>
		<div class="row container_12 container_16">
			<div class="container">
					<div class="paragraphs">
						<div class="row">
							<div class="span4">
								 <div align="center">
									<img src="${pageContext.request.contextPath}/resources/skin0/img/loading.gif" alt="Loading" title="Loading" />
								</div>
						    </div>
						</div>
					</div>
				</div>
		</div>
		<div class="row mt-15">
			<div class="form-group col-xs-12 text-right"></div>
		</div>			
     
    </div>
</body>
<script type="text/javascript" src="js/redirectCallbackOnload.js"></script>
</html>