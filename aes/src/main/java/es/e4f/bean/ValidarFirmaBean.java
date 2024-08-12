package es.e4f.bean;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

/**
 * Bean para la pantalla Validación de firmas
 * 
 */

public class ValidarFirmaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long validarFirmaId;

	@Valid
	@NotNull(message = "{NotNull.validarFirma.documento_original}")
	private MultipartFile documentoOriginal;

	@Valid
	@NotNull(message = "{NotNull.validarFirma.documento_firmado}")
	private MultipartFile documentoFirmado;
	
	

	public Long getValidarFirmaId() {
		return validarFirmaId;
	}

	public void setValidarFirmaId(Long validarFirmaId) {
		this.validarFirmaId = validarFirmaId;
	}

	public MultipartFile getDocumentoOriginal() {
		return documentoOriginal;
	}

	public void setDocumentoOriginal(MultipartFile documentoOriginal) {
		this.documentoOriginal = documentoOriginal;
	}

	public MultipartFile getDocumentoFirmado() {
		return documentoFirmado;
	}

	public void setDocumentoFirmado(MultipartFile documentoFirmado) {
		this.documentoFirmado = documentoFirmado;
	}
	
	public boolean tieneDocumentos() {
		return documentoOriginal != null && !documentoOriginal.isEmpty()
				&& documentoFirmado != null && !documentoFirmado.isEmpty();
    }
}