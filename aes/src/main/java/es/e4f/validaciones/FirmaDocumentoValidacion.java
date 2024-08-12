package es.e4f.validaciones;



import org.springframework.validation.Errors;
import org.springframework.validation.Validator;



import es.e4f.bean.DocFirmaBean;


public class FirmaDocumentoValidacion implements Validator {

	
	@Override
	public boolean supports(Class<?> clazz) {
		return DocFirmaBean.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		DocFirmaBean docFirmaBean = (DocFirmaBean) target;

		
		
		if (docFirmaBean.getDocumento() == null || docFirmaBean.getDocumento().getSize() == 0) {
			errors.reject("NotEmpty.firma.documento");

		}

		

	}

}