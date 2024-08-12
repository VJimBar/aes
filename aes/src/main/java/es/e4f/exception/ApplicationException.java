package es.e4f.exception;

public class ApplicationException extends GeneralException  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -333820018537826359L;

	public static final String SAML_VALIDACION_ERROR = "saml.validacion.error";
	public static final String SAML_GENERACION_ERROR = "saml.generacion.error";
	public static final String SAML_RESPONSE_ERROR = "saml.response.error";
	
	
	public ApplicationException(String codigo) {
		super(codigo);
	}
	
	public ApplicationException(String codigo, String mensaje) {
		super(codigo, mensaje);
	}

	public ApplicationException(String codigo, Exception e) {
		super(codigo, e);
	}

}