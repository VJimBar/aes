package es.clave.sp.actions;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.opensaml.saml2.core.LogoutResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import es.clave.sp.AbstractSPServlet;
import es.clave.sp.ApplicationSpecificServiceException;
import es.clave.sp.Constants;
import es.clave.sp.SPUtil;
import es.clave.sp.SessionHolder;
import es.clave.sp.SpProtocolEngineFactory;
import es.e4f.utils.Encryption;
import eu.eidas.auth.commons.EidasErrorKey;
import eu.eidas.auth.commons.EidasErrors;
import eu.eidas.auth.commons.EidasParameterKeys;
import eu.eidas.auth.commons.EidasStringUtil;
import eu.eidas.auth.commons.attribute.AttributeDefinition;
import eu.eidas.auth.commons.attribute.AttributeValue;
import eu.eidas.auth.commons.exceptions.InvalidParameterEIDASException;
import eu.eidas.auth.commons.protocol.IAuthenticationResponseNoMetadata;
import eu.eidas.auth.engine.ProtocolEngineNoMetadataI;
import eu.eidas.auth.engine.xml.opensaml.SAMLEngineUtils;
import eu.eidas.auth.engine.xml.opensaml.SecureRandomXmlIdGenerator;
import eu.eidas.engine.exceptions.EIDASSAMLEngineException;

/**
 * This Action receives a SAML Response, shows it to the user and then validates
 * it getting the attributes values
 */
public class ReturnAction extends AbstractSPServlet {

	private static final long serialVersionUID = 3660074009157921579L;

	private static final String SAML_VALIDATION_ERROR = "Could not validate token for Saml Response";

	static final Logger logger = LoggerFactory.getLogger(IndexAction.class.getName());

	private final ProtocolEngineNoMetadataI protocolEngine = SpProtocolEngineFactory
			.getSpProtocolEngine(Constants.SP_CONF);

	private String SAMLResponse = null;
	private String logoutResponse = null;
	private ImmutableMap<AttributeDefinition<?>, ImmutableSet<? extends AttributeValue<?>>> attrMap = null;
	private Properties configs = null;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("--> en el metodo doGet de ReturnAction");
		if (acceptsHttpRedirect()) {
			HttpSession session = request.getSession();
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			System.out.println(timestamp.toString() + " session id " + session.getId());
			System.out.println(timestamp.toString() + " samlId " + (String) session.getAttribute("samlId"));
			doPost(request, response);

		} else {
			logger.warn("BUSINESS EXCEPTION : redirect binding is not allowed");
			System.out.println("BUSINESS EXCEPTION : redirect binding is not allowed");
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
		System.out.println("\n" + "------------>doPost ReturnAction method");
		RequestDispatcher dispatcher;
		HttpSession session = request.getSession();

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String dateTime = DateTimeFormatter.ofPattern("dd/MM/yy hh:mm:ss").format(LocalDateTime.now());

		SAMLResponse = request.getParameter("SAMLResponse");
		logoutResponse = request.getParameter("logoutResponse");

		String relayState = request.getParameter("RelayState");

		if (SAMLResponse != null && !SAMLResponse.trim().isEmpty()) {
			System.out.println("entra por el if");

			configs = SPUtil.loadSPConfigs();
			byte[] decSamlToken = EidasStringUtil.decodeBytesFromBase64(SAMLResponse);
			IAuthenticationResponseNoMetadata authnResponse = null;
			try {
				// validate SAML Token
				authnResponse = protocolEngine.unmarshallResponseAndValidate(decSamlToken, request.getRemoteHost(), 0,
						0, configs.getProperty(Constants.SP_RETURN));

				// Check session
				String prevRelayState = SessionHolder.sessionsSAML.get(authnResponse.getInResponseToId());
				if (prevRelayState == null || !prevRelayState.equals(relayState)) {
					throw new EIDASSAMLEngineException("La respuesta recibida no corresponde con ninguna "
							+ "request o no coincide el RelayState");
				}
			} catch (EIDASSAMLEngineException e) {
				logger.error(e.getMessage(), e);
				request.setAttribute("Title", "SAML_VALIDATION_ERROR");
				request.setAttribute("Message", e.getMessage());

				request.setAttribute("Date", dateTime);

				dispatcher = request.getRequestDispatcher("/errorPage.jsp");
				dispatcher.forward(request, response);

				throw new ApplicationSpecificServiceException(SAML_VALIDATION_ERROR, e.getMessage());
			}

			if (authnResponse.isFailure()) {
				request.setAttribute("Title", "Saml Response is fail");
				request.setAttribute("Message", authnResponse.getStatusMessage());

				request.setAttribute("Date", dateTime);

				dispatcher = request.getRequestDispatcher("/errorPage.jsp");
				dispatcher.forward(request, response);

				throw new ApplicationSpecificServiceException("Saml Response is fail",
						authnResponse.getStatusMessage());
			} else {
				attrMap = authnResponse.getAttributes().getAttributeMap();
			}

			request.setAttribute("attrMap", attrMap);

			int i = 0;

			for (Iterator<ImmutableSet<? extends AttributeValue<?>>> itr2 = attrMap.values().iterator(); itr2
					.hasNext();) {
				ImmutableSet<? extends AttributeValue<?>> attr = itr2.next();

				if (attrMap.keySet().asList().get(i).getFriendlyName().toString().equals("PersonIdentifier"))

				{
					String clave = "";

					try {

						clave = Encryption.encryptAES(attr.toString().replace("[", "").replace("]", ""),
								"clave_secreta_OV");

						System.out.println(timestamp.toString() + " Clave: " + clave);

					} catch (Exception e) {
						logger.error(e.getMessage(), e);

					}

					request.setAttribute("ID", clave);
				}

				i++;
			}

			dispatcher = request.getRequestDispatcher("/returnPage.jsp");

			// logout data
			String samlId = SAMLEngineUtils.generateNCName();
			request.setAttribute("logoutRequest", generateLogout(configs.getProperty(Constants.SP_RETURN),
					configs.getProperty(Constants.PROVIDER_NAME), configs.getProperty("service.url"), samlId));
			String nonce = SecureRandomXmlIdGenerator.INSTANCE.generateIdentifier(8);
			SessionHolder.sessionsSAML.put(nonce, samlId);
			request.setAttribute("RelayState", nonce);
			request.setAttribute("nodeServiceUrl", configs.getProperty("service.url"));
			request.setAttribute(EidasParameterKeys.BINDING.toString(), getRedirectMethod());

			/*
			 * **************------> COOKIE
			 */
			Cookie sessionCookie = new Cookie("JSESSIONID", session.getId());
			sessionCookie.setMaxAge(30 * 60);
			response.addCookie(sessionCookie);

			session.setAttribute("samlId", samlId); //$NON-NLS-1$
			session.setAttribute("RelayState", nonce); //$NON-NLS-1$
			session.setAttribute("nodeServiceUrl", configs.getProperty("service.url")); //$NON-NLS-1$
			session.setAttribute(EidasParameterKeys.BINDING.toString(), getRedirectMethod()); // $NON-NLS-1$
			session.setAttribute("logoutRequest", generateLogout(configs.getProperty(Constants.SP_RETURN),
					configs.getProperty(Constants.PROVIDER_NAME), configs.getProperty("service.url"), samlId));
			session.setAttribute("autentication", configs.getProperty("autentication"));
			/*
			 * 
			 */
			session.setMaxInactiveInterval(5 * 60);

			System.out.println(timestamp.toString() + " session id: " + session.getId());
			System.out.println("samlId: " + samlId);
			System.out.println("RelayState: " + nonce);
			System.out.println("nodeServiceUrl: " + configs.getProperty("service.url"));
			System.out.println("autentication: " + configs.getProperty("autentication"));
			System.out.println(EidasParameterKeys.BINDING.toString() + ": " + getRedirectMethod() + "\n");

			dispatcher.forward(request, response);

		} else if (logoutResponse != null && !logoutResponse.trim().isEmpty()) {
			System.out.println("entra por el else if");

			// Se valida la request recibida del SP
			if (logger.isDebugEnabled()) {
				logger.debug("Se procede a validar una respuesta de logout recibida desde un IdP");
				logger.debug("Respuesta a validar: " + response);
			}
			LogoutResponse logoutResp = null;

			try {
				byte[] decSamlToken = EidasStringUtil.decodeBytesFromBase64(logoutResponse);
				// validate SAML Token
				logoutResp = protocolEngine.unmarshallLogoutResponseAndValidate(decSamlToken, request.getRemoteHost(),
						0, 0, configs.getProperty(Constants.SP_RETURN));

			} catch (Exception e) {
				logger.error("No se pudo validar la respuesta", e);
				request.setAttribute("Title", "SAML_VALIDATION_ERROR");
				request.setAttribute("Message", e.getMessage());

				request.setAttribute("Date", dateTime);

				dispatcher = request.getRequestDispatcher("/errorPage.jsp");
				dispatcher.forward(request, response);
				throw new ApplicationSpecificServiceException(SAML_VALIDATION_ERROR, e.getMessage());
			}
			logger.info("Saml LogoutResponse is SUCCESS", logoutResp.getStatus());

			// Check session
			String requestSamlId = SessionHolder.sessionsSAML.get(relayState);

			if (requestSamlId == null || !requestSamlId.equals(logoutResp.getInResponseTo())) {
				request.setAttribute("Title", "SAML_VALIDATION_ERROR");
				request.setAttribute("Message",
						"La respuesta recibida no corresponde con ninguna " + "request o no coincide el RelayState");

				request.setAttribute("Date", dateTime);

				System.out.println("Saml LogoutResponse exception");

				dispatcher = request.getRequestDispatcher("/errorPage.jsp");
				dispatcher.forward(request, response);
				throw new InvalidParameterEIDASException(
						"La respuesta recibida no corresponde con ninguna " + "request o no coincide el RelayState");
			}

			session = request.getSession(true);

			session.setAttribute("samlId", null);
			session.setAttribute("logoutRequest", null);

			SessionHolder.sessionsSAML.remove(logoutResp.getInResponseTo());

			System.out.println("***SALIDA****");
			System.out.println(timestamp.toString() + " session id: " + session.getId());
			System.out.println("samlId " + (String) session.getAttribute("samlId"));

			// invalidar la sesion
			if (session != null) {
				session.invalidate(); // Solo invalida la sesión si aún existe
			}

			// COOKIE ELIMINATED
			clearSessionCookie(request, response);

			// Se redirige al usuario
			dispatcher = request.getRequestDispatcher("/");
			dispatcher.forward(request, response);
		} else {
			System.out.println("entra por el else");
			logger.error("Se ha recibido una respuesta vacia");

			request.setAttribute("Title", "SAML_VALIDATION_ERROR");
			request.setAttribute("Message", "Se ha recibido una respuesta vacia");

			request.setAttribute("Date", dateTime);

			System.out.println("Se ha recibido una respuesta vacia");

			dispatcher = request.getRequestDispatcher("/errorPage.jsp");
			dispatcher.forward(request, response);
			throw new InvalidParameterEIDASException(
					EidasErrors.get(EidasErrorKey.COLLEAGUE_RESP_INVALID_SAML.errorCode()),
					EidasErrors.get(EidasErrorKey.COLLEAGUE_RESP_INVALID_SAML.errorMessage()));
		}

		// dispatcher.forward(request, response);
	}

	private String generateLogout(String assertionConsumerUrl, String providerName, String destination, String nonce) {
		try {
			final byte[] token = protocolEngine.generateLogoutRequestMessage(assertionConsumerUrl, providerName,
					destination, nonce);
			return EidasStringUtil.encodeToBase64(token);
		} catch (EIDASSAMLEngineException e) {
			logger.error(e.getMessage());
			logger.error("", e);

			throw new ApplicationSpecificServiceException("Could not generate token for Saml Request", e.getMessage());
		}
	}

	/**
	 * Method to be used by configuration. See sp.properties -> redirect.method key.
	 * This allows to be able to function eihter in EIDAS or STORK mode
	 * respectively.
	 *
	 * @return a redirect method
	 */
	public String getRedirectMethod() {
		String ret = "post";
		if (configs.containsKey(Constants.REDIRECT_METHOD)) {
			ret = configs.getProperty(Constants.REDIRECT_METHOD);
			if (ret == null || ret.trim().isEmpty() || !(ret.equals("post") || ret.equals("get"))) {
				logger.error("La variable de configuración redirect.method no contiene un valor adecuado: " + ret);
				ret = "post";
			}
		}
		return ret;
	}

//	private void clearSessionCookie(HttpServletResponse response) {
//
//		Cookie sessionCookie = new Cookie("JSESSIONID", null);
//		sessionCookie.setMaxAge(0); // Set expiration to 0
//		sessionCookie.setPath("/"); // Set path used when the cookie was created
//		response.addCookie(sessionCookie);
//		
//
//	}

	private void clearSessionCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("JSESSIONID".equals(cookie.getName())) {
					cookie.setMaxAge(0); // Set expiration to 0
					cookie.setPath("/"); // Set path used when the cookie was created
					response.addCookie(cookie);
				}
			}
		}
	}

}