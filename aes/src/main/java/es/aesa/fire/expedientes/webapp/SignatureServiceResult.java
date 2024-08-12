package es.aesa.fire.expedientes.webapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aesa.test.TestClientGRegistro;

import es.gob.fire.client.FireClient;
import es.gob.fire.client.TransactionResult;


public class SignatureServiceResult extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String CONFIG_FILE = "C:/Fire/Config/registro.properties"; //$NON-NLS-1$

	private static final String REDIRECT_SUCCESS_PAGE = "fire/RecoverSign.jsp"; //$NON-NLS-1$
	private static final String REDIRECT_ERROR_PAGE = "fire/ErrorTransactionPage.jsp"; //$NON-NLS-1$


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignatureServiceResult() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		// Cabeceras de la peticion
		System.out.println(" === " + request.getRequestedSessionId());
		System.out.println(" === Cabeceras");
		final Enumeration<String> headers = request.getHeaderNames();
		while (headers.hasMoreElements()) {
			final String header = headers.nextElement();
			System.out.println(header + ": " + request.getHeader(header));
		}

		try {

			HttpSession session = request.getSession(false);
			
			System.out.print("\n sesion 3 " + session + "\n");

			String appId = (String) session.getAttribute("appId"); //$NON-NLS-1$
			String subjectId = (String) session.getAttribute("subjectId"); //$NON-NLS-1$
			String subjectNom = (String) session.getAttribute("subjectNom"); //$NON-NLS-1$
			String trId = (String) session.getAttribute("trId"); //$NON-NLS-1$
			String fileName = (String) session.getAttribute("fileName"); //$NON-NLS-1$
			String numExpediente = (String) session.getAttribute("numExpediente"); //$NON-NLS-1$


			//Configuración de la aplicación de registro
			FileReader reader=new FileReader(CONFIG_FILE);  
			Properties p=new Properties();  
			p.load(reader); 

			String nombreUsuario=p.getProperty("nombreUsuario");
			String claveUsuario=p.getProperty("claveUsuario");
			String url=p.getProperty("url");
			String codUniOrgDestino=p.getProperty("codUniOrgDestino");
			String codAsunto=p.getProperty("codAsunto");
			String extracto=p.getProperty("extracto");

			String fileNamePath=p.getProperty("fileNamePath") + numExpediente+ "/";
			String tempPath=p.getProperty("tempPath") + numExpediente+ "/";
			
			File directorio = new File(tempPath);
	        if (!directorio.exists()) {
	            if (directorio.mkdirs()) {
	                System.out.println("Directorio creado");
	            } else {
	                System.out.println("Error al crear directorio");
	            }
	        }

			File carpeta=new File(fileNamePath);
			String[] fileNames=carpeta.list();
			String[] fileNamesPaths=new String[fileNames.length];

			for(int i=0;i<fileNames.length;i++) {
				if(fileNames[i].contains(fileName))
				{
					fileNamesPaths[i]=tempPath;
					fileNames[i]=fileName.substring(0, fileName.length()-4) + "_F.pdf";
				}else 
				{
					fileNamesPaths[i]=fileNamePath;
				}
			}

			String[] interesados=new String[1];
			interesados[0] = subjectId + "~" + subjectNom + "~ ~ ";

			final FireClient fireClient = new FireClient(appId);

			final TransactionResult result = fireClient.recoverSignResult(
					trId,
					subjectId,
					null);

			System.out.println("result estado: " + result.getState()); //$NON-NLS-1$

			if (result.getState() == TransactionResult.STATE_ERROR) {
				System.err.println("Error: " + result.getErrorCode()); //$NON-NLS-1$
				System.err.println("Mensaje: " + result.getErrorMessage()); //$NON-NLS-1$
				redirect(false, request, response);
				return;
			}

			// Guardamos la firma
			byte[] firma=result.getResult();
			try (FileOutputStream fos = new FileOutputStream(new File( tempPath  +  fileName.substring(0, fileName.length()-4) + "_F.pdf"))) {
				fos.write(firma);
			}


			final byte[] data = result.encodeResult();
			try (FileOutputStream fos = new FileOutputStream(new File( tempPath  +  fileName.substring(0, fileName.length()-4) + "_F.xsig"))) {
				fos.write(data);
			}


			//Llamamos a Registro.
			for(int e=0;e<fileNames.length;e++) {
				System.out.print(fileNamesPaths[e] + "\n");
				System.out.print(fileNames[e] + "\n");
			}
			System.out.print(tempPath + "\n");

			String idAsiento=TestClientGRegistro.registroTelematico(nombreUsuario, claveUsuario, url,
					codUniOrgDestino, codAsunto, extracto, fileNames, fileNamesPaths,
					tempPath, numExpediente, interesados);

			System.out.print(idAsiento + "\n");

			request.setAttribute("idAsiento", idAsiento);


			redirect(true, request, response);
		}
		catch (final Exception e) {
			redirect(false, request, response);
		}
	}


	private static void redirect(final boolean success, final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("result", new Boolean(success));

		System.out.println("Succes: " + success); //$NON-NLS-1$

		if(success)
			request.getRequestDispatcher(REDIRECT_SUCCESS_PAGE).forward(request, response);
		else
			request.getRequestDispatcher(REDIRECT_ERROR_PAGE).forward(request, response);
	}
}
