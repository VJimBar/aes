package es.safcce.comun.util.bean;

/**
 * Representación de un fichero como array de bytes
 *
 */
public class Fichero {

	private String nombreFichero;
	private byte[] contenido;
	private String mimeType;

	public Fichero(String nombreFichero, byte[] contenido, String mimeType) {
		super();
		this.nombreFichero = nombreFichero;
		this.contenido = contenido;
		this.mimeType = mimeType;
	}
	
	public Fichero() {
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public byte[] getContenido() {
		return contenido;
	}

	public void setContenido(byte[] contenido) {
		this.contenido = contenido;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

}
