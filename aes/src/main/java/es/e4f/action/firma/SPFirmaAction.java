package es.e4f.action.firma;



import com.aesa.test.TestClientGRegistro;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;

import es.safcce.comun.util.bean.GenClavesUtil;
import es.clave.sp.AbstractSPServlet;
import es.e4f.action.firma.util.ConstantesFirma;
import es.e4f.action.firma.util.FirmaUtil;
import es.e4f.bean.ResponseSAMLBean;
import es.e4f.bean.security.UsuarioAutenticado;
import es.e4f.exception.ApplicationException;
import es.e4f.utils.SPUtil;
import eu.eidas.auth.commons.EidasErrorKey;
import eu.eidas.auth.commons.EidasStringUtil;
import eu.eidas.auth.commons.attribute.AttributeDefinition;
import eu.eidas.auth.commons.attribute.AttributeValue;
import eu.eidas.auth.commons.attribute.AttributeValueMarshaller;
import eu.eidas.auth.commons.attribute.AttributeValueMarshallingException;
import eu.eidas.auth.commons.attribute.AttributeValueTransliterator;
import eu.eidas.auth.commons.attribute.ImmutableAttributeMap;
import eu.eidas.auth.commons.protocol.IAuthenticationResponse;
import eu.eidas.auth.commons.protocol.IRequestMessage;
import eu.eidas.auth.commons.protocol.eidas.LevelOfAssurance;
import eu.eidas.auth.commons.protocol.eidas.LevelOfAssuranceComparison;
import eu.eidas.auth.commons.protocol.eidas.SpType;
import eu.eidas.auth.commons.protocol.eidas.impl.EidasAuthenticationRequest;
import eu.eidas.auth.commons.protocol.impl.EidasSamlBinding;
import eu.eidas.auth.commons.protocol.impl.SamlNameIdFormat;
import eu.eidas.auth.commons.xml.opensaml.OpenSamlHelper;
import eu.eidas.auth.engine.AbstractProtocolEngine;
import eu.eidas.auth.engine.ProtocolEngineI;
import eu.eidas.auth.engine.xml.opensaml.CorrelatedResponse;
import eu.eidas.auth.engine.xml.opensaml.SAMLEngineUtils;
import eu.eidas.encryption.exception.MarshallException;
import eu.eidas.encryption.exception.UnmarshallException;
import eu.eidas.engine.exceptions.EIDASSAMLEngineException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.opensaml.xml.XMLObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;


@SuppressWarnings("serial")
public class SPFirmaAction extends AbstractSPServlet {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SPFirmaAction.class);

	public static final String RUTA_RETORNO_FIRMA = "/firmae4f";

	private static final String CONFIG_REGISTRO_FILE = "C:/Fire/Config/registro.properties"; //$NON-NLS-1$


	@Override
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {

		System.out.println(" Firmando E4f");
		
		// Se coge el contexto de seguridad, donde en la autentificación habremos guardado el Token SAML
		// de autenticación
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String numExpediente = request.getParameter("numExpediente"); 
		
		System.out.println("numExpediente");
		
		//Configuración de la aplicación
		FileReader readerR=new FileReader(CONFIG_REGISTRO_FILE);  
		Properties pR=new Properties();  
		pR.load(readerR); 

		Date date=new Date();
		System.out.println("Fecha" + date + "\n");
		System.out.println("NUMEXPEDIENTE " + numExpediente + "\n");

		// Se valida que se ha subido un documento para firmar
		String fileNamePath=pR.getProperty("fileNamePath") + numExpediente+ "/";
		File carpeta=new File(fileNamePath);
		String[] fileNames=carpeta.list();
		String fileName="";
		String descripcion="";

		if(fileNames!=null) {
			for(int i=0;i<fileNames.length;i++) {
				if(fileNames[i].contains("Solicitud"))
				{
					fileName=fileNames[i];
					descripcion=fileNames[i];

				}
			}
		}else {
			LOGGER.error("Error durante la operacion de firma"); //$NON-NLS-1$
			response.sendRedirect("fire/ErrorPage.jsp?msg=" + URLEncoder.encode("Error en la llamada a la operacion de firma. No se ha generado ninguna solicitud")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$));
			return;
		}

		
		//gets the configuration values needed to build the request: provider name, IdP URL, Metadata and 
		//attributes supported by the IdP
		Properties configs;
		try {
			configs = FirmaUtil.loadFirmaConfigs();
			// nombre de la aplicación que se ha indicado en la solicitud de alta.
			String providerName = configs.getProperty(ConstantesFirma.PROVIDER_NAME_FIRMA);
			// URL del nodo de integración facilitada por el Área de Administración Electrónica de AESA
			String nodeUrl = configs.getProperty(ConstantesFirma.FIRMA_DESTINATION);
			// Identificador del nodo de integración 
			String nodeMetadataUrl = configs.getProperty(ConstantesFirma.FIRMA_DESTINATION_METADATA);
			//The list of attributes supported by the IdP
			// Indica los atributos que se solicitan y envían en el proceso de firma. Se obtienen de un fichero de configuración
			//create the requested attributes list and fill the values that have to be included in the request

			ProtocolEngineI protocolEngine = FirmaUtil.getProtocolEngine();
			final ImmutableSortedSet<AttributeDefinition<?>> allSupportedAttributesSet = protocolEngine.getProtocolProcessor().getAllSupportedAttributes();
			List<AttributeDefinition<?>> reqAttrList = new ArrayList<>(allSupportedAttributesSet);


			ImmutableAttributeMap.Builder mapBuilder = ImmutableAttributeMap.builder();
			Iterator<AttributeDefinition<?>> it = reqAttrList.iterator();
			while (it.hasNext()) {
				AttributeDefinition<?> attr = it.next();

				//fill the attributes that has a value in the SAML request
				if ("http://eidas.europa.eu/attributes/firma/NameDoc".equals(attr.getNameUri().toString())) {
					//NameDoc includes the document filename
					addAttributeValues(attr, fileName, mapBuilder);
				}

				else if ("http://eidas.europa.eu/attributes/firma/DocumentH".equals(attr.getNameUri().toString())) {
					//DocumentH includes the document SHA-512 Hash
					addAttributeValues(attr, GenClavesUtil.generaHash(FileUtils.readFileToByteArray(new File(fileNamePath+fileName)), "SHA-512"), mapBuilder);		
				}

				else if ("http://eidas.europa.eu/attributes/operacion/CodTramite".equals(attr.getNameUri().toString())) {
					//CodTramite includes the code that identifies the task
					String codTramite = configs.getProperty(ConstantesFirma.FIRMA_COD_TRAMITE);
					addAttributeValues(attr, codTramite, mapBuilder);
				}

				else if ("http://eidas.europa.eu/attributes/firma/TipoOperacion".equals(attr.getNameUri().toString())) {
					//Constant value
					addAttributeValues(attr, "FIRMA_AES", mapBuilder);
				}

				else if ("http://eidas.europa.eu/attributes/firma/AuthSAMLResponse".equals(attr.getNameUri().toString())) {
					//AuthSAMLResponse includes the SAML authentication token received by the SP as the response 
					//to the user authentication in the SP, before the execution of the sign task in the SP,
					//encoded in base64
					addAttributeValues(attr, GenClavesUtil.encode(EidasStringUtil.getBytes(((UsuarioAutenticado)auth.getPrincipal()).getSamlResponse())), mapBuilder);
				}


				else {
					mapBuilder.put((AttributeDefinition) attr);
				}

			}

			ImmutableAttributeMap reqAttrMap = mapBuilder.build();

			byte[] token = null;
			// build the request
			EidasAuthenticationRequest.Builder reqBuilder = new EidasAuthenticationRequest.Builder();
			reqBuilder.destination(nodeUrl);
			reqBuilder.providerName(providerName);
			reqBuilder.requestedAttributes(reqAttrMap);
			reqBuilder.levelOfAssurance(LevelOfAssurance.SUBSTANTIAL.stringValue());
			reqBuilder.spType(SpType.PUBLIC.toString());

			reqBuilder.levelOfAssuranceComparison(LevelOfAssuranceComparison.MINIMUM.stringValue());
			reqBuilder.nameIdFormat(SamlNameIdFormat.UNSPECIFIED.toString());
			reqBuilder.binding(EidasSamlBinding.EMPTY.getName());

			String metadataUrl = configs.getProperty(ConstantesFirma.FIRMA_METADATA_URL);
			if (metadataUrl != null && !metadataUrl.isEmpty() && FirmaUtil.isMetadataEnabled()) {
				reqBuilder.issuer(metadataUrl);
			}

			reqBuilder.citizenCountryCode("ES");

			IRequestMessage binaryRequestMessage = null;

			try {

				reqBuilder.id(SAMLEngineUtils.generateNCName());
				binaryRequestMessage = protocolEngine.generateRequestMessage(reqBuilder.build(), nodeMetadataUrl);
			} catch (EIDASSAMLEngineException e) {
				LOGGER.error("", e);
				response.sendRedirect("fire/ErrorPage.jsp?msg=" + URLEncoder.encode(e.getErrorMessage())); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$));
				throw new ApplicationException(ApplicationException.SAML_GENERACION_ERROR, e.getErrorMessage());
			}

			if (binaryRequestMessage != null) {
				token = binaryRequestMessage.getMessageBytes();
			}


			String samlRequestXML = EidasStringUtil.toString(token);
			String samlRequest = EidasStringUtil.encodeToBase64(token);

			request.setAttribute("samlRequest", samlRequest);
			request.setAttribute("samlRequestXML", samlRequestXML);
			request.setAttribute("nodeUrl", nodeUrl);
			request.setAttribute("fileName", fileName);

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			response.sendRedirect("fire/ErrorPage.jsp?msg=" + URLEncoder.encode(e.getMessage()));
			LOGGER.error(e.getMessage(), e);
		}


		RequestDispatcher dispatcher = request.getRequestDispatcher("E4f/redirectEidas.jsp");
		dispatcher.forward(request, response);
	}


	/**
	 * @param attr
	 * @param value
	 * @param mapBuilder
	 * @return
	 */
	private ImmutableAttributeMap.Builder addAttributeValues(AttributeDefinition attr, String value, ImmutableAttributeMap.Builder mapBuilder) {
		String attrName = attr.getNameUri().toASCIIString();
		ArrayList<String> values = new ArrayList<>();
		values.addAll(getValuesOfAttribute(attrName, value));

		if (!values.isEmpty()) {
			AttributeValueMarshaller<?> attributeValueMarshaller = attr.getAttributeValueMarshaller();
			ImmutableSet.Builder<AttributeValue<?>> builder = ImmutableSet.builder();
			for (final String val : values) {
				AttributeValue<?> attributeValue = null;
				try {
					if (AttributeValueTransliterator.needsTransliteration(val)) {
						attributeValue = attributeValueMarshaller.unmarshal(val, true);
					} else {
						attributeValue = attributeValueMarshaller.unmarshal(val, false);
					}
				} catch (AttributeValueMarshallingException e) {
					throw new IllegalStateException(e);
				}
				builder.add(attributeValue);
			}
			mapBuilder.put((AttributeDefinition) attr, (ImmutableSet) builder.build());
		}
		return mapBuilder;
	}


	/**
	 * @param attrName
	 * @param value
	 * @return
	 */
	private List<String> getValuesOfAttribute(String attrName, String value) {

		ArrayList<String> tmp = new ArrayList<>();
		tmp.add(value);
		if (AttributeValueTransliterator.needsTransliteration(value)) {
			String trValue = AttributeValueTransliterator.transliterate(value);
			tmp.add(trValue);

		}
		return tmp;
	}

	/**
	 * Controlador de la respuesta de la firma
	 * @param model ModelMap
	 * @param request
	 * @param sAMLResponse respuesta del Idp
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ServletException 
	 */
	public void doPost (HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException, IOException, ServletException {

		// LLama al método que gestionará la respuesta de la firma
		String sAMLResponse = request.getParameter("SAMLResponse");
		ResponseSAMLBean respuesta=null;
		RequestDispatcher dispatcher =null;
		
		try {
			respuesta = respuestaDatosSAML(sAMLResponse, request, response);
			request.setAttribute("respuestaFirma", respuesta);
		} catch (ApplicationException | UnmarshallException e) {
			// TODO Auto-generated catch block
			response.sendRedirect("fire/ErrorPage.jsp?msg=" + e.getMessage().toString());
			LOGGER.error(e.getMessage(), e);
		}
		
		
		//Configuración de la aplicación de registro
		FileReader reader=new FileReader(CONFIG_REGISTRO_FILE);  
		Properties p=new Properties();  
		p.load(reader); 

		String nombreUsuario=p.getProperty("nombreUsuario");
		String claveUsuario=p.getProperty("claveUsuario");
		String url=p.getProperty("url");
		String codUniOrgDestino=p.getProperty("codUniOrgDestino");
		String codAsunto=p.getProperty("codAsunto");
		String extracto=p.getProperty("extracto");
		
		String numExpediente = request.getParameter("numExpediente"); 
		String fileName = request.getParameter("fileName"); 
		String subjectId = request.getParameter("subjectId"); 
		String subjectNom = request.getParameter("subjectNom"); 
		
		
		String fileNamePath=p.getProperty("fileNamePath") + numExpediente+ "/";
		String tempPath=p.getProperty("tempPath") + numExpediente+ "/";
		
		File directorio = new File(tempPath);
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado");
            } else {
                System.out.println("Error al crear directorio");
            }
        }

		File carpeta=new File(fileNamePath);
		String[] fileNames=carpeta.list();
		String[] fileNamesPaths=new String[fileNames.length];

		for(int i=0;i<fileNames.length;i++) {
			if(fileNames[i].contains(fileName))
			{
				fileNamesPaths[i]=tempPath;
				fileNames[i]=fileName.substring(0, fileName.length()-4) + "_F.pdf";
			}else 
			{
				fileNamesPaths[i]=fileNamePath;
			}
		}

		String[] interesados=new String[1];
		interesados[0] = subjectId + "~" + subjectNom + "~ ~ ";

		
		// Guardamos la firma
		byte[] firma=respuesta.getDatosFirma().getBytes();
		try (FileOutputStream fos = new FileOutputStream(new File( tempPath  +  fileName.substring(0, fileName.length()-4) + "_F.pdf"))) {
			fos.write(firma);
		}


		//Llamamos a Registro.
		for(int e=0;e<fileNames.length;e++) {
			System.out.print(fileNamesPaths[e] + "\n");
			System.out.print(fileNames[e] + "\n");
		}
		System.out.print(tempPath + "\n");

		String idAsiento;
		try {
			idAsiento = TestClientGRegistro.registroTelematico(nombreUsuario, claveUsuario, url,
					codUniOrgDestino, codAsunto, extracto, fileNames, fileNamesPaths,
					tempPath, numExpediente, interesados);
			
			System.out.print(idAsiento + "\n");

			request.setAttribute("idAsiento", idAsiento);
			
		} catch (ServiceException | IOException e) {
			// TODO Auto-generated catch block
			response.sendRedirect("fire/ErrorPage.jsp?msg=" + URLEncoder.encode("Error en la llamada a la operacion de firma:<br>" + e.toString(), "utf-8")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$));
			LOGGER.error(e.getMessage(), e);
		}

	
		dispatcher = request.getRequestDispatcher("fire/RecoverSign.jsp");
		dispatcher.forward(request, response);

	}

	/**
	 * Método que gestiona la respuesta de la Firma
	 * @param sAMLResponse
	 * @param request
	 * @return
	 * @throws ApplicationException
	 * @throws UnmarshallException
	 * @throws IOException 
	 */
	private ResponseSAMLBean respuestaDatosSAML(String sAMLResponse, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, UnmarshallException, IOException {

		//Decodes SAML Response
		byte[] decSamlToken = EidasStringUtil.decodeBytesFromBase64(sAMLResponse);

		//Obtiene la configuración necesaria para obtener la respuesta del token SAML
		Properties configs = FirmaUtil.loadFirmaConfigs();
		String metadataUrl = configs.getProperty(ConstantesFirma.FIRMA_METADATA_URL);

		//Get SAMLEngine instance
		IAuthenticationResponse authnResponse = null;
		String samlUnencryptedResponseXML = null;
		try {
			ProtocolEngineI engine = FirmaUtil.getProtocolEngine();
			//validate SAML Token
			authnResponse = engine.unmarshallResponseAndValidate(decSamlToken, request.getRemoteHost(), 0, 0, metadataUrl);

			// Obtenemos el tokem SAML desencriptado
			boolean encryptedResponse = SPUtil.isEncryptedSamlResponse(decSamlToken);
			if (encryptedResponse) {
				byte[] eidasTokenSAML = checkAndDecryptResponse(decSamlToken, engine);
				samlUnencryptedResponseXML = EidasStringUtil.toString(eidasTokenSAML);
			}

		} catch (EIDASSAMLEngineException e) {
			// Si hay algun error al validar el TOKEN SAML se lanza una excepcion
			LOGGER.error("", e);
			if (StringUtils.isEmpty(e.getErrorDetail())) {
				response.sendRedirect("fire/ErrorPage.jsp?msg=" + e.getErrorMessage().toString());
				throw new ApplicationException(ApplicationException.SAML_VALIDACION_ERROR, e.getErrorMessage());
			} else {
				response.sendRedirect("fire/ErrorPage.jsp?msg=" + e.getErrorDetail());
				throw new ApplicationException(ApplicationException.SAML_VALIDACION_ERROR, e.getErrorDetail());
			}
		}

		ImmutableMap<AttributeDefinition<?>, ImmutableSet<? extends AttributeValue<?>>> attrMap = null;
		ResponseSAMLBean responseSAMLBean = null;
		// Si la respuesta obtenida de la validacion y obtencion de la respuesta es incorrecta también se lanza una excepcion
		if (authnResponse.isFailure()) {
			if (authnResponse.getStatusMessage() == null || authnResponse.getStatusMessage().isEmpty()) {
				response.sendRedirect("fire/ErrorPage.jsp?msg=" + ApplicationException.SAML_RESPONSE_ERROR);
				throw new ApplicationException(ApplicationException.SAML_RESPONSE_ERROR);
			} else {
				response.sendRedirect("fire/ErrorPage.jsp?msg=" + authnResponse.getStatusMessage());
				throw new ApplicationException(authnResponse.getStatusMessage());
			}
		} else {

			// Si la respuesta es válida, obtenemos los parámetros que esperamos y los mapeamos a un objeto de Respuesta
			responseSAMLBean = new ResponseSAMLBean();
			responseSAMLBean.setLevelOfAssurance(authnResponse.getLevelOfAssurance());
			attrMap = authnResponse.getAttributes().getAttributeMap();
			ImmutableSet<AttributeDefinition<?>> definition = attrMap.keySet();
			Iterator<AttributeDefinition<?>> it = definition.iterator();
			while (it.hasNext()) {
				AttributeDefinition<?> attr = it.next();

				// Obtenemos el número del documento de la persona autenticada
				if ("http://eidas.europa.eu/attributes/naturalperson/PersonIdentifier".equals(attr.getNameUri().toString()) && attrMap.get(attr) != null) {

					ImmutableSet<? extends AttributeValue<?>> values = attrMap.get(attr);
					List<AttributeValue<?>> attrValueList = new ArrayList<>(values);
					if (attrValueList != null && attrValueList.size() == 1) {
						String numIdentificacion = (String)attrValueList.get(0).getValue();
						if (numIdentificacion != null) {
							int i = numIdentificacion.lastIndexOf('/');
							if (i != -1) {
								numIdentificacion = numIdentificacion.substring(i+1);
							}
						}
						responseSAMLBean.setNumIdentificativo(numIdentificacion);
					}

				}

				// Obtenemos el Nombre de la persona autenticada
				if ("http://eidas.europa.eu/attributes/naturalperson/CurrentFamilyName".equals(attr.getNameUri().toString()) && attrMap.get(attr) != null) {

					ImmutableSet<? extends AttributeValue<?>> values = attrMap.get(attr);
					List<AttributeValue<?>> attrValueList = new ArrayList<>(values);
					if (attrValueList != null && attrValueList.size() == 1) {
						responseSAMLBean.setApellidos((String)attrValueList.get(0).getValue());
					}

				}

				// Obtenemos los apellidos de la persona autenticada
				if ("http://eidas.europa.eu/attributes/naturalperson/CurrentGivenName".equals(attr.getNameUri().toString()) && attrMap.get(attr) != null) {

					ImmutableSet<? extends AttributeValue<?>> values = attrMap.get(attr);
					List<AttributeValue<?>> attrValueList = new ArrayList<>(values);
					if (attrValueList != null && attrValueList.size() == 1) {
						responseSAMLBean.setNombre((String)attrValueList.get(0).getValue());
					}

				}

				// Obtenemos el pais de nacimiento de la persona autenticada
				if ("http://eidas.europa.eu/attributes/naturalperson/PlaceOfBirth".equals(attr.getNameUri().toString()) && attrMap.get(attr) != null) {

					ImmutableSet<? extends AttributeValue<?>> values = attrMap.get(attr);
					List<AttributeValue<?>> attrValueList = new ArrayList<>(values);
					if (attrValueList != null && attrValueList.size() == 1) {
						responseSAMLBean.setCodPais((String)attrValueList.get(0).getValue());
					}

				}

				// Obtenemos el tipo de documento de la persona autenticada
				if ("http://eidas.europa.eu/attributes/naturalperson/TipoDocumento".equals(attr.getNameUri().toString()) && attrMap.get(attr) != null) {

					ImmutableSet<? extends AttributeValue<?>> values = attrMap.get(attr);
					List<AttributeValue<?>> attrValueList = new ArrayList<>(values);
					if (attrValueList != null && attrValueList.size() == 1) {
						responseSAMLBean.setTipoDocumento((String)attrValueList.get(0).getValue());
					}

				}

				// Obtenemos el CodTramite
				if ("http://eidas.europa.eu/attributes/operacion/CodTramite".equals(attr.getNameUri().toString()) && attrMap.get(attr) != null) {

					ImmutableSet<? extends AttributeValue<?>> values = attrMap.get(attr);
					List<AttributeValue<?>> attrValueList = new ArrayList<>(values);
					if (attrValueList != null && attrValueList.size() == 1) {
						responseSAMLBean.setCodTramite((String)attrValueList.get(0).getValue());
					}

				}

				// Obtenemos el xml con la evidencia de la firma
				if ("http://eidas.europa.eu/attributes/firma/DatosFirma".equals(attr.getNameUri().toString()) && attrMap.get(attr) != null) {

					ImmutableSet<? extends AttributeValue<?>> values = attrMap.get(attr);
					List<AttributeValue<?>> attrValueList = new ArrayList<>(values);
					if (attrValueList != null && attrValueList.size() == 1) {
						responseSAMLBean.setDatosFirma(EidasStringUtil.toString(GenClavesUtil.decode((String)attrValueList.get(0).getValue())));
					}

				}
			}

			responseSAMLBean.setSamlResponse(samlUnencryptedResponseXML);
		}


		return responseSAMLBean;
	}

	/**
	 * @param responseBytes
	 * @param engine
	 * @return
	 * @throws EIDASSAMLEngineException
	 */
	private byte[] checkAndDecryptResponse(byte[] responseBytes, ProtocolEngineI engine) throws EIDASSAMLEngineException {
		// This decrypts the given responseBytes:
		CorrelatedResponse response = (CorrelatedResponse) engine.unmarshallResponse(responseBytes);

		try {
			// re-transform the decrypted bytes to another byte array, without signing:
			return marshall(response.getResponse());
		} catch (EIDASSAMLEngineException e) {
			LOGGER.debug(AbstractProtocolEngine.SAML_EXCHANGE, "BUSINESS EXCEPTION : checkAndResignEIDASTokenSAML : Sign and Marshall.", e);
			LOGGER.info(AbstractProtocolEngine.SAML_EXCHANGE, "BUSINESS EXCEPTION : checkAndResignEIDASTokenSAML : Sign and Marshall.",
					e.getMessage());
			throw new EIDASSAMLEngineException(EidasErrorKey.INTERNAL_ERROR.errorCode(),
					EidasErrorKey.INTERNAL_ERROR.errorMessage(), e);
		}
	}

	/**
	 * Method that transform the received SAML object into a byte array representation.
	 *
	 * @param samlToken the SAML token.
	 * @return the byte[] of the SAML token.
	 * @throws EIDASSAMLEngineException the SAML engine exception
	 */
	private byte[] marshall(XMLObject samlToken) throws EIDASSAMLEngineException {
		try {
			return OpenSamlHelper.marshall(samlToken);
		} catch (MarshallException e) {
			LOGGER.error(e.getMessage(), e);
			throw new EIDASSAMLEngineException(e);
		}
	}


	@Override
	protected Logger getLogger() {
		// TODO Auto-generated method stub
		return null;
	}



}
