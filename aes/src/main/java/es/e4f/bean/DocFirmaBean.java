package es.e4f.bean;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

/**
 * Bean para la pantalla Solicitud
 * 
 */
public class DocFirmaBean implements Serializable {


	private static final long serialVersionUID = 4585540196239534714L;

	private MultipartFile documento;

	public DocFirmaBean() {
		// Empty default constructor
	}

	public MultipartFile getDocumento() {
		return documento;
	}

	public void setDocumento(MultipartFile documento) {
		this.documento = documento;
	}

}