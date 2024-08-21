/* Copyright (C) 2017 [Gobierno de Espana]
 * This file is part of FIRe.
 * FIRe is free software; you can redistribute it and/or modify it under the terms of:
 *   - the GNU General Public License as published by the Free Software Foundation;
 *     either version 2 of the License, or (at your option) any later version.
 *   - or The European Software License; either version 1.1 or (at your option) any later version.
 * Date: 08/09/2017
 * You may contact the copyright holder at: soporte.afirma@correo.gob.es
 */
package es.aesa.fire.expedientes.webapp;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.gob.fire.client.ClientConfigFilesNotFoundException;
import es.gob.fire.client.FireClient;
import es.gob.fire.client.SignOperationResult;
import es.gob.fire.client.SignProcessConstants.SignatureAlgorithm;
import es.gob.fire.client.SignProcessConstants.SignatureFormat;
import es.gob.fire.client.SignProcessConstants.SignatureOperation;


/**
 * Servicio para la firma de datos.
 */
public class SignatureService extends HttpServlet {

	/** Serial Id. */
	private static final long serialVersionUID = 1991462934952495784L;

	private static final Logger LOGGER = LoggerFactory.getLogger(SignatureService.class);


	private static final String CONFIG_FILE = "C:/Fire/Config/fire.properties"; //$NON-NLS-1$
	private static final String CONFIG_REGISTRO_FILE = "C:/Fire/Config/registro.properties"; //$NON-NLS-1$
	private static final String PROP_URL_BASE = "urlbase"; //$NON-NLS-1$


	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		RequestDispatcher dispatcher = request.getRequestDispatcher("fire/Sign.jsp");
		dispatcher.forward(request, response);

	}
	
	
	@Override
	protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {


		String subjectId = request.getParameter("subjectId"); //$NON-NLS-1$
		String subjectNom = request.getParameter("subjectNom"); //$NON-NLS-1$
		String numExpediente = request.getParameter("numExpediente"); //$NON-NLS-1$
		String idSol = request.getParameter("idSol"); //$NON-NLS-1$

		//Configuración de la aplicación
		FileReader readerR=new FileReader(CONFIG_REGISTRO_FILE);  
		Properties pR=new Properties();  
		pR.load(readerR); 
		
		Date date=new Date();
		System.out.println("Fecha" + date + "\n");
		System.out.println("NUMEXPEDIENTE " + numExpediente + "\n");

		String fileNamePath=pR.getProperty("fileNamePath") + numExpediente+ "/";
		File carpeta=new File(fileNamePath);
		String[] fileNames=carpeta.list();
		String fileName="";
		String descripcion="";

		if(fileNames!=null) {
	    	for(int i=0;i<fileNames.length;i++) {
		    	if(fileNames[i].contains("Solicitud"))
		    	{
				  fileName=fileNames[i];
				  descripcion=fileNames[i];
			    }
		   }
		}else {
			LOGGER.error("Error durante la operacion de firma"); //$NON-NLS-1$
			response.sendRedirect("fire/ErrorPage.jsp?msg=" + URLEncoder.encode("Error en la llamada a la operacion de firma. No se ha generado ninguna solicitud")); 
			return;
		}
		
		String tempFile= fileNamePath +  fileName;

		//Configuración de la aplicación
		FileReader reader=new FileReader(CONFIG_FILE);  
		Properties p=new Properties();  
		p.load(reader);  

		String urlBase= p.getProperty(PROP_URL_BASE);
		String APP_ID = p.getProperty("appId");
		String aplicacionName = p.getProperty("appName");

		HttpSession session = request.getSession();
		
		System.out.print("\n" + date + " sesion 4 " + session.getId() + "\n");
		
		session.setAttribute("subjectId", subjectId); //$NON-NLS-1$
		session.setAttribute("subjectNom", subjectNom); //$NON-NLS-1$
		session.setAttribute("appId", APP_ID); //$NON-NLS-1$
		session.setAttribute("fileName", fileName); //$NON-NLS-1$
		session.setAttribute("numExpediente", numExpediente); //$NON-NLS-1$
		session.setAttribute("idSol", idSol); //$NON-NLS-1$
		
		Cookie sessionCookie=new Cookie("JSESSIONID", session.getId());
		sessionCookie.setMaxAge(30*60);
		
		response.addCookie(sessionCookie);
		
		System.out.println("subjectId: " + subjectId); //$NON-NLS-1$
		System.out.println("appId: " + APP_ID); //$NON-NLS-1$
		System.out.println("ficheroTemporal: " + tempFile); //$NON-NLS-1$
		System.out.println(" === " + request.getRequestedSessionId());

		// Configuracion del formato de firma
		final Properties extraParams = new Properties();
		extraParams.setProperty("format", "PAdES");

		// Configuracion de FIRe
		final Properties config = new Properties();
		config.setProperty("docTitle", descripcion);
		config.setProperty("docName", fileName);
		config.setProperty("redirectOkUrl", urlBase + "signatureServiceResult");
		config.setProperty("redirectErrorUrl", urlBase + "signatureServiceResult");
		config.setProperty("appName", aplicacionName);
		config.setProperty("certOrigin", "local,clavefirmatest");

		FireClient fireClient;
		try {
			fireClient = new FireClient(APP_ID);
		} catch (final ClientConfigFilesNotFoundException e1) {
			e1.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		File fichero = new File(tempFile);

		byte[] data=org.apache.commons.io.FileUtils.readFileToByteArray(fichero);

		SignOperationResult result;
		try {
			result = fireClient.sign(
					subjectId,
					SignatureOperation.SIGN,
					SignatureFormat.PADES,
					SignatureAlgorithm.SHA256WITHRSA,
					extraParams,
					data,
					config);
		} catch (final Exception e) {
			LOGGER.error("Error durante la operacion de firma", e); //$NON-NLS-1$
			response.sendRedirect("fire/ErrorPage.jsp?msg=" + URLEncoder.encode("Error en la llamada a la operacion de firma:<br>" + e.toString(), "utf-8")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$));
			return;
		}

		// Del resultado obtenemos:
		// - El identificador de transaccion necesario para recuperar la firma generada.
		// - La URL a la que deberemos redirigir al usuario para que se autentique.
		final String transactionId = result.getTransactionId();
		final String redirectUrl = result.getRedirectUrl();

		// Guardamos el ID de transaccion en la sesion para despues poder recuperar la firma
		if (transactionId != null) {
			session.setAttribute("trId", transactionId); //$NON-NLS-1$
			System.out.println("trId: " + transactionId); //$NON-NLS-1$

		}

		// Redirigimos al usuario
		response.sendRedirect(redirectUrl);
	}



}
