package es.clave.sp.actions;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.clave.sp.AbstractSPServlet;



/**
 *  This Action Generates a SAML Request with the data given by the user, then sends it to the selected node
 */
public class FirmarAction extends AbstractSPServlet {

	private static final long serialVersionUID = 3660074009157921579L;

	private static final Logger LOGGER = LoggerFactory.getLogger(FirmarAction.class);


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String numExpediente=""; 
		String idSol="";
		String subjectId="";
		String subjectNom="";
		String e4f="";

		String parametrosUrl=request.getQueryString();

		if(parametrosUrl!=null)
		{
			String[] parametros=parametrosUrl.toString().split("&",0);

			if(parametros!=null && parametros.length>0)
			{
				for(int i=0;i<parametros.length;i++) 
				{
					if(parametros[i].contains("CARPETA") && (parametros[i].toString().split("=",0)).length>1) {
						numExpediente=(parametros[i].toString().split("=",0))[1];
					}
					else if (parametros[i].contains("IDSOL") && (parametros[i].toString().split("=",0)).length>1){
						idSol=(parametros[i].toString().split("=",0))[1];
					}
					else if (parametros[i].contains("NIF") && (parametros[i].toString().split("=",0)).length>1){
						subjectId=(parametros[i].toString().split("=",0))[1];
					}
					else if (parametros[i].contains("NOMBRE") && (parametros[i].toString().split("=",0)).length>1){
						subjectNom=(parametros[i].toString().split("=",0))[1];
					} 
				}

			}

		}


		HttpSession session = request.getSession(false);

		if(session!=null) {
			
			e4f=String.valueOf(session.getAttribute("autentication")); 
			String samlId=String.valueOf(session.getAttribute("samlId"));

			request.setAttribute("e4f", e4f);
			request.setAttribute("subjectNom", subjectNom);
			request.setAttribute("subjectId", subjectId);
			request.setAttribute("idSol", idSol);
			request.setAttribute("numExpediente", numExpediente);
			request.setAttribute("samlId", samlId);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/fire/Sign.jsp");
			dispatcher.forward(request, response);

		} else
		{ 
			response.sendRedirect("fire/ErrorPage.jsp?msg=Ha expirado la sesión. Salga de la aplicación y vuelva a entrar identficándose de nuevo.");
		}



	}


	@Override
	protected Logger getLogger() {
		// TODO Auto-generated method stub
		return null;
	}


}
