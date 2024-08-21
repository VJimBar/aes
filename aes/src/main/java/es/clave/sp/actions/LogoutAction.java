package es.clave.sp.actions;

import static es.clave.sp.Constants.SP_CONF;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.opensaml.saml2.core.LogoutResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.clave.sp.AbstractSPServlet;
import es.clave.sp.ApplicationSpecificServiceException;
import es.clave.sp.Constants;
import es.clave.sp.SPUtil;
import es.clave.sp.SessionHolder;
import es.clave.sp.SpProtocolEngineFactory;
import es.clave.sp.SpProtocolEngineI;
import eu.eidas.auth.commons.EidasStringUtil;
import eu.eidas.engine.exceptions.EIDASSAMLEngineException;

/**
 * This Action receives a SAML Response, shows it to the user and then validates it getting the attributes values
 */
public class LogoutAction extends AbstractSPServlet {

	private static final long serialVersionUID = 3660074009157921579L;

	private static final String SAML_VALIDATION_ERROR = "Could not validate token for Saml Response";


	static final Logger logger = LoggerFactory.getLogger(LogoutAction.class.getName());

	private String SAMLResponse;
	private Properties configs;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("\n"+"------------> doGet Logout method");
		if (acceptsHttpRedirect()) {
			doPost(request, response);

		} else {
			logger.warn("BUSINESS EXCEPTION : redirect binding is not allowed");
		}
	}

	/**
	 * Post method
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("-------------->doPost LogoutAction method");
		SAMLResponse = request.getParameter("SAMLResponse");
		String relayState = request.getParameter("RelayState");
		configs = SPUtil.loadSPConfigs();
		if (logger.isDebugEnabled()) {
			logger.debug("Solicitud de logout recibida: " + SAMLResponse);
		}

		byte[] decSamlToken = EidasStringUtil.decodeBytesFromBase64(SAMLResponse);
		LogoutResponse logoutResponse;
		RequestDispatcher dispatcher;

		try {
			SpProtocolEngineI engine = SpProtocolEngineFactory.getSpProtocolEngine(SP_CONF);
			// validate SAML Token
			logoutResponse = engine.unmarshallLogoutResponseAndValidate(decSamlToken, request.getRemoteHost(), 0, 0, 
					configs.getProperty(Constants.SP_RETURN));

			// Check session
			String prevRelayState = SessionHolder.sessionsSAML.get(logoutResponse.getInResponseTo());
			if (prevRelayState == null || !prevRelayState.equals(relayState)) {

				request.setAttribute("Title","SAML_VALIDATION_ERROR");
				request.setAttribute("Message", "La respuesta recibida no corresponde con ninguna "
						+ "request o no coincide el RelayState");

				String dateTime= DateTimeFormatter.ofPattern("dd/MM/yy hh:mm:ss").format(LocalDateTime.now());
				request.setAttribute("Date", dateTime);

				dispatcher = request.getRequestDispatcher("/errorPage.jsp");
				dispatcher.forward(request, response);

				throw new EIDASSAMLEngineException("La respuesta recibida no corresponde con ninguna "
						+ "request o no coincide el RelayState");
			}

			SessionHolder.sessionsSAML.remove(logoutResponse.getInResponseTo());

			HttpSession session = request.getSession(true);
			session.setAttribute("samlId", null); 

			// Se redirige al usuario
			dispatcher = request.getRequestDispatcher("/index.jsp");
			dispatcher.forward(request, response);

		} catch (EIDASSAMLEngineException e) {
			logger.error(e.getMessage(), e);

			request.setAttribute("Title","SAML_VALIDATION_ERROR");
			request.setAttribute("Message", e.getMessage());

			String dateTime= DateTimeFormatter.ofPattern("dd/MM/yy hh:mm:ss").format(LocalDateTime.now());
			request.setAttribute("Date", dateTime);

			dispatcher = request.getRequestDispatcher("/errorPage.jsp");
			dispatcher.forward(request, response);

			throw new ApplicationSpecificServiceException(SAML_VALIDATION_ERROR, e.getMessage());
		}

	}
}