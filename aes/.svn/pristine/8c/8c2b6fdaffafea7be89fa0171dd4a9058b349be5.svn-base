package es.e4f.bean;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import es.safcce.comun.util.bean.datosfirmaxml.Firma;
import es.safcce.comun.util.constantes.ConstantesConversores;

public class ValidacionFirmaBean {
		
	private String datosFirmanteNumIdentificacion;
	
	private String datosFirmanteTipoDocumento;
	
	private String datosFirmantePais;
	
	private String datosFirmanteNombreApellidosRazonSocial;
	
	private String datosFirmaHashDocumento;
	
	private String datosFirmaHuellaFirma;
	
	private Date datosFirmaTimeStampFirma;
	
	private String datosFirmaNombreDocumento;
	
	private String hashDocumentoOriginal;

	public ValidacionFirmaBean() {}
	
	public ValidacionFirmaBean(Firma firma, String hashDocumentoOriginal) {
		this.setDatosFirmanteNumIdentificacion(firma.getDatosFirmante().getNumIdentificacion());
		this.setDatosFirmantePais(firma.getDatosFirmante().getPais());
		this.setDatosFirmanteTipoDocumento(firma.getDatosFirmante().getTipoDocumento());
		this.setDatosFirmaHuellaFirma(firma.getDatosFirma().getHuellaFirma());
		this.setDatosFirmaHashDocumento(firma.getDatosFirma().getHashDocumento().getHash());
		this.setDatosFirmaTimeStampFirma(
				firma.getDatosFirma().getTimeStampFirma().toGregorianCalendar().getTime());
		this.setDatosFirmanteNombreApellidosRazonSocial(
				firma.getDatosFirmante().getNombreApellidosRazonSocial());
		this.setDatosFirmaNombreDocumento(firma.getDatosFirma().getNombreDocumento());
		
		this.setHashDocumentoOriginal(hashDocumentoOriginal);
	}

	public String getDatosFirmanteNumIdentificacion() {
		return datosFirmanteNumIdentificacion;
	}

	public void setDatosFirmanteNumIdentificacion(
			String datosFirmanteNumIdentificacion) {
		this.datosFirmanteNumIdentificacion = datosFirmanteNumIdentificacion;
	}

	public String getDatosFirmanteTipoDocumento() {
		return datosFirmanteTipoDocumento;
	}

	public void setDatosFirmanteTipoDocumento(String datosFirmanteTipoDocumento) {
		this.datosFirmanteTipoDocumento = datosFirmanteTipoDocumento;
	}

	public String getDatosFirmantePais() {
		return datosFirmantePais;
	}

	public void setDatosFirmantePais(String datosFirmantePais) {
		this.datosFirmantePais = datosFirmantePais;
	}

	public String getDatosFirmaHashDocumento() {
		return datosFirmaHashDocumento;
	}

	public void setDatosFirmaHashDocumento(String datosFirmaHashDocumento) {
		this.datosFirmaHashDocumento = datosFirmaHashDocumento;
	}

	public String getDatosFirmaHuellaFirma() {
		return datosFirmaHuellaFirma;
	}

	public void setDatosFirmaHuellaFirma(String datosFirmaHuellaFirma) {
		this.datosFirmaHuellaFirma = datosFirmaHuellaFirma;
	}

	public Date getDatosFirmaTimeStampFirma() {
		return datosFirmaTimeStampFirma;
	}

	public void setDatosFirmaTimeStampFirma(Date datosFirmaTimeStampFirma) {
		this.datosFirmaTimeStampFirma = datosFirmaTimeStampFirma;
	}

	public String getHashDocumentoOriginal() {
		return hashDocumentoOriginal;
	}

	public void setHashDocumentoOriginal(String hashDocumentoOriginal) {
		this.hashDocumentoOriginal = hashDocumentoOriginal;
	}

	public String getDatosFirmanteNombreApellidosRazonSocial() {
		return datosFirmanteNombreApellidosRazonSocial;
	}

	public void setDatosFirmanteNombreApellidosRazonSocial(
			String datosFirmanteNombreApellidosRazonSocial) {
		this.datosFirmanteNombreApellidosRazonSocial = datosFirmanteNombreApellidosRazonSocial;
	}

	public String getDatosFirmaNombreDocumento() {
		return datosFirmaNombreDocumento;
	}

	public void setDatosFirmaNombreDocumento(String datosFirmaNombreDocumento) {
		this.datosFirmaNombreDocumento = datosFirmaNombreDocumento;
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>();
			
		map.put(ConstantesConversores.ValidacionFirmaBeanConstantes.NUM_IDENT, 
				this.getDatosFirmanteNumIdentificacion());
		map.put(ConstantesConversores.ValidacionFirmaBeanConstantes.PAIS, 
				this.datosFirmantePais);
		map.put(ConstantesConversores.ValidacionFirmaBeanConstantes.TIPO_DOC, 
				this.getDatosFirmanteTipoDocumento());
		map.put(ConstantesConversores.ValidacionFirmaBeanConstantes.HUELLA_FIRMA, 
				this.getDatosFirmaHuellaFirma());
		map.put(ConstantesConversores.ValidacionFirmaBeanConstantes.HASH_DOC_FIRMA, 
				this.datosFirmaHashDocumento);
		map.put(ConstantesConversores.ValidacionFirmaBeanConstantes.TIMESTAMP, 
				this.getDatosFirmaTimeStampFirma());
		map.put(ConstantesConversores.ValidacionFirmaBeanConstantes.HASH_DOC_ORIGINAL, 
				this.getHashDocumentoOriginal());
		map.put(ConstantesConversores.ValidacionFirmaBeanConstantes.NOMBRE_INTERESADO, 
				this.getDatosFirmanteNombreApellidosRazonSocial());
		map.put(ConstantesConversores.ValidacionFirmaBeanConstantes.NOMBRE_DOCUMENTO, 
				this.getDatosFirmaNombreDocumento());
		
		return map;
	}
}
