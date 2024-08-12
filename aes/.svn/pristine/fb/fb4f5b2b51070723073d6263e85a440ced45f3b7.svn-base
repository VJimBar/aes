package es.e4f.bean;

import java.io.Serializable;



/**
 * bean 
 * 
 */
public class ResponseSAMLBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2341212363103714671L;
	
	private String codTramite;
	
	// Autenticacion
	private String tipoDocumento;
	
	private String numIdentificativo;
	
	private String nombre;
	
	private String apellidos;
	
	private String codPais;
	
	private String levelOfAssurance;
	
	private String samlResponse;
	
	
	// FIRMA
		
	private String datosFirma;
		
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumIdentificativo() {
		return numIdentificativo;
	}

	public void setNumIdentificativo(String numIdentificativo) {
		this.numIdentificativo = numIdentificativo;
	}

	public String getCodPais() {
		return codPais;
	}

	public void setCodPais(String codPais) {
		this.codPais = codPais;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	
	
	public String getCodTramite() {
		return codTramite;
	}

	public void setCodTramite(String codTramite) {
		this.codTramite = codTramite;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombreCompleto() {
		StringBuilder nombreCompleto = new StringBuilder(this.nombre);
		if (this.apellidos != null && !"".equals(this.apellidos.trim())) {
			nombreCompleto.append(" ").append(this.apellidos);
		}
		return nombreCompleto.toString();
	}

	public String getLevelOfAssurance() {
		return levelOfAssurance;
	}

	public void setLevelOfAssurance(String levelOfAssurance) {
		this.levelOfAssurance = levelOfAssurance;
	}

	public String getSamlResponse() {
		return samlResponse;
	}

	public void setSamlResponse(String samlResponse) {
		this.samlResponse = samlResponse;
	}

	

	public String getDatosFirma() {
		return datosFirma;
	}

	public void setDatosFirma(String datosFirma) {
		this.datosFirma = datosFirma;
	}

	
	
	
	

}