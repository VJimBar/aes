package es.e4f.exception;

/**
 * <p>
 * Title: GeneralException
 * </p>
 * <p>
 * Description: Excepción genérica de la aplicación
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * <p>
 * Company: Indra Software Labs. Málaga
 * </p>
 * 
 * @author
 * @version 1.0
 */
public class GeneralException extends Exception {

	private static final long serialVersionUID = 3305970270524198454L;

	/**
	 * Variable que almacena el código del error para poder ser mapeado
	 * posteriormente y mostrar un mensaje. Se le asigna el valor por Defecto.
	 */
	private String codigo = CLAVE_ERROR_DEFECTO;

	/**
	 * Constante que almacena la clave por defecto de los mensajes de error.
	 */
	public static final String CLAVE_ERROR_DEFECTO = "errorGenerico.defecto";

	/**
	 * Constructor de clase.
	 * 
	 * @param codigo
	 *            Código del error de la excepción.
	 */
	public GeneralException(String codigo) {
		super();
		this.codigo = codigo;
	}

	/**
	 * Constructor de clase.
	 * 
	 * @param e
	 *            Excepción encapsulada.
	 */
	public GeneralException(Exception e) {
		super(e);
		if (e instanceof GeneralException) {
			this.codigo = ((GeneralException) e).getCodigo();
		}
	}

	/**
	 * Constructor de clase.
	 * 
	 * @param codigo
	 * @param e
	 *            Excepción encapsulada.
	 */
	public GeneralException(String codigo, Exception e) {
		super(e);
		this.codigo = codigo;
	}

	/**
	 * Constructor de clase.
	 * 
	 * @param codigo
	 *            - Código de error.
	 * @param mensaje
	 *            - Mensaje asociado a la excepción.
	 * @param e
	 *            - Excepción encapsulada.
	 */
	public GeneralException(String codigo, String mensaje, Exception e) {
		super(mensaje, e);
		this.codigo = codigo;
	}

	/**
	 * Constructor de clase.
	 * 
	 * @param codigo
	 *            - Código de error.
	 * @param mensaje
	 *            - Mensaje asociado a la excepción.
	 */
	public GeneralException(String codigo, String mensaje) {
		super(mensaje);
		this.codigo = codigo;
	}

	/**
	 * Método que asigna el código del error.
	 * 
	 * @param sCodError
	 *            Código del error.
	 */
	public void setCodigo(String sCodError) {
		codigo = sCodError;
	}

	/**
	 * Método que devuelve el código de error.
	 * 
	 * @return Código de error.
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Método que devuelve la excepción raíz del error.
	 * 
	 * @return Excepción raíz del error.
	 */
	public Exception getException() {
		return (Exception) this.getCause();
	}

}