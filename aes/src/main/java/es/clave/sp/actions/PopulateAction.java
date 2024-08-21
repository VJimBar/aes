package es.clave.sp.actions;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.clave.sp.AbstractSPServlet;
import es.clave.sp.Constants;
import es.clave.sp.SPUtil;
import eu.eidas.auth.commons.EidasParameterKeys;

/**
 * This Action Generates a SAML Request with the data given by the user, then
 * sends it to the selected node
 */
public class PopulateAction extends AbstractSPServlet {

	private static final long serialVersionUID = 3660074009157921579L;

	private static final Logger LOGGER = LoggerFactory.getLogger(PopulateAction.class);

	private static Properties configs;
	private static String nodeServiceUrl;
	private static String providerName;
	private static String spApplication;
	private static String returnUrl;

	private static void loadGlobalConfig() {
		configs = SPUtil.loadSPConfigs();
		nodeServiceUrl = configs.getProperty("service.url");
		providerName = configs.getProperty(Constants.PROVIDER_NAME);
		spApplication = configs.getProperty(Constants.SP_APLICATION);
		returnUrl = configs.getProperty(Constants.SP_RETURN);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		System.out.println("\n" + "--> en el metodo doGet de PopulateAction");
		System.out.println("session id: " + session.getId());
		System.out.println("samlId " + (String) session.getAttribute("samlId"));
		doPost(request, response);
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
		System.out.println("\n" + "------------>doPost PopulateAction method");
		HttpSession session = request.getSession();

		PopulateAction.loadGlobalConfig();

		Date date = new Date();

		RequestDispatcher dispatcher = null;
		if ((String) session.getAttribute("samlId") != null) {
			System.out.println("entra por el if");
			System.out.println("session id: " + session.getId());
			System.out.println(date + " samlId1:  " + (String) session.getAttribute("samlId"));

			String samlId = (String) session.getAttribute("samlId"); //$NON-NLS-1$
			String relayState = (String) session.getAttribute("RelayState"); //$NON-NLS-1$
			String nodeServiceUrl = (String) session.getAttribute("nodeServiceUrl"); //$NON-NLS-1$
			String binding = (String) session.getAttribute("binding"); //$NON-NLS-1$
			String logoutRequest = (String) session.getAttribute("logoutRequest"); //$NON-NLS-1$

			request.setAttribute("samlId", samlId);
			request.setAttribute("RelayState", relayState);
			request.setAttribute("nodeServiceUrl", nodeServiceUrl);
			request.setAttribute("binding", binding);
			request.setAttribute("logoutRequest", logoutRequest);

			dispatcher = request.getRequestDispatcher("/logout.jsp");
			dispatcher.forward(request, response);
		} else {
			System.out.println("entra por el else");
			System.out.println("session id: " + session.getId());
			System.out.println(date + " samlId2: " + (String) session.getAttribute("samlId"));

			request.setAttribute("providerName", providerName);
			request.setAttribute("spApplication", spApplication);
			request.setAttribute("returnUrl", returnUrl);
			request.setAttribute("nodeServiceUrl", nodeServiceUrl);
			request.setAttribute(EidasParameterKeys.BINDING.toString(), getRedirectMethod());

			dispatcher = request.getRequestDispatcher("/selectAttributes.jsp");
			dispatcher.forward(request, response);
		}

	}

	public String getProviderName() {
		return providerName;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	// public void setReturnUrl(String url) {
	// returnUrl = url;
	// }

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
				LOGGER.error("La variable de configuración redirect.method no contiene un valor adecuado: " + ret);
				ret = "post";
			}
		}
		return ret;
	}
}
