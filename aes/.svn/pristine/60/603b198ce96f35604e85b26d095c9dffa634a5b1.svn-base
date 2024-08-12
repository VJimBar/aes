package es.e4f.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.opensaml.saml2.core.Response;
import org.opensaml.xml.XMLObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import es.clave.sp.ApplicationContextProvider;
import es.e4f.exception.ApplicationException;
import eu.eidas.auth.commons.xml.DocumentBuilderFactoryUtil;
import eu.eidas.auth.commons.xml.opensaml.OpenSamlHelper;
import eu.eidas.auth.engine.ProtocolEngineFactory;
import eu.eidas.auth.engine.ProtocolEngineI;
import eu.eidas.auth.engine.configuration.SamlEngineConfigurationException;
import eu.eidas.auth.engine.configuration.dom.ProtocolEngineConfigurationFactory;
import eu.eidas.encryption.exception.UnmarshallException;


public class SPUtil {

	private static final Logger LOG = LoggerFactory.getLogger(SPUtil.class);
	
	
	static ProtocolEngineConfigurationFactory protocolEngineConfigurationFactory = null;
	static ProtocolEngineFactory defaultProtocolEngineFactory = null;
	
	private static final String NO_ASSERTION = "no assertion found";

	private static final String ASSERTION_XPATH = "//*[local-name()='Assertion']";
	    
	private SPUtil() {
	}



    public static String getConfigFilePath() {
        //String envLocation = System.getenv().get(ConstantesSP.SP_CONFIG_REPOSITORY);
       // return System.getProperty(ConstantesSP.SP_CONFIG_REPOSITORY, envLocation);
    	 return ((String)ApplicationContextProvider.getApplicationContext().getBean(ConstantesSP.SP_REPO_BEAN_NAME)).trim();
    }

    private static Properties loadConfigs(String fileName) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(SPUtil.getConfigFilePath()+fileName));
        return properties;
    }

    public static Properties loadSPConfigs() throws ApplicationException {
        try {
            return SPUtil.loadConfigs(ConstantesSP.SP_PROPERTIES);
        } catch (IOException e) {
            LOG.error(e.getMessage());
            throw new ApplicationException("Could not load configuration file", e);
           
        }
    }

    /**
     * @return true when the metadata support should be active
     * @throws ApplicationException 
     */
    public static boolean isMetadataEnabled() throws ApplicationException {
    	Properties spProperties = loadSPConfigs();
        return spProperties.getProperty(ConstantesSP.SP_METADATA_ACTIVATE) == null || Boolean.parseBoolean(
        		spProperties.getProperty(ConstantesSP.SP_METADATA_ACTIVATE));
    }

    /**
     * @return true when the metadata support should be active
     * @throws ApplicationException 
     */
    public static boolean isMetadataHttpFetchEnabled() throws ApplicationException {
    	Properties spProperties = loadSPConfigs();
        return spProperties.getProperty(ConstantesSP.SP_METADATA_HTTPFETCH) == null || Boolean.parseBoolean(
        		spProperties.getProperty(ConstantesSP.SP_METADATA_HTTPFETCH));
    }

    /**
     * @return metadata directory
     * @throws ApplicationException 
     */
    public static String getMetadataRepositoryPath() throws ApplicationException {
    	Properties spProperties = loadSPConfigs();
        return spProperties.getProperty(ConstantesSP.SP_METADATA_REPOPATH);
    }

    /**
     * @return true metadata signature must be validated for those not in trusted list
     * @throws ApplicationException 
     */
    public static boolean isValidateEntityDescriptorSignatureEnabled() throws ApplicationException {
        Properties properties = SPUtil.loadSPConfigs();
        return properties.getProperty(ConstantesSP.SP_METADATA_VALIDATESIGN) == null || Boolean.parseBoolean(
                properties.getProperty(ConstantesSP.SP_METADATA_VALIDATESIGN));
    }

    public static String getTrustedEntityDescriptors() throws ApplicationException {
        Properties properties = SPUtil.loadSPConfigs();
        return properties.getProperty(ConstantesSP.SP_METADATA_TRUSTEDDS, "");
    }

    /**
     * Returns true when the input contains an encrypted SAML Response
     *
     * @param tokenSaml
     * @return
     * @throws EIDASSAMLEngineException
     */
    public static boolean isEncryptedSamlResponse(byte[] tokenSaml) throws UnmarshallException {
        XMLObject samlObject = OpenSamlHelper.unmarshall(tokenSaml);
        if (samlObject instanceof Response) {
            Response response = (Response) samlObject;
            return response.getEncryptedAssertions() != null && !response.getEncryptedAssertions().isEmpty();
        }
        return false;

    }
  
    /**
     * @param samlMsg the saml response as a string
     * @return a string representing the Assertion
     */
    public static String extractAssertionAsString(String samlMsg) {
        String assertion = NO_ASSERTION;
        try {
            Document doc = DocumentBuilderFactoryUtil.parse(samlMsg);

            XPath xPath = XPathFactory.newInstance().newXPath();
            Node node = (Node) xPath.evaluate(ASSERTION_XPATH, doc, XPathConstants.NODE);
            if (node != null) {
                assertion = DocumentBuilderFactoryUtil.toString(node);
            }
        } catch (ParserConfigurationException pce) {
            LOG.error("cannot parse response {}", pce);
        } catch (SAXException saxe) {
            LOG.error("cannot parse response {}", saxe);

        } catch (IOException ioe) {
            LOG.error("cannot parse response {}", ioe);

        } catch (XPathExpressionException xpathe) {
            LOG.error("cannot find the assertion {}", xpathe);

        } catch (TransformerException trfe) {
            LOG.error("cannot output the assertion {}", trfe);

        }

        return assertion;
    }
    
    public static synchronized ProtocolEngineI getProtocolEngine() {
		if (defaultProtocolEngineFactory == null) {
			protocolEngineConfigurationFactory = new ProtocolEngineConfigurationFactory(ConstantesSP.SP_SAMLENGINE_FILE,
					null, getConfigFilePath());
			try {
				defaultProtocolEngineFactory = new ProtocolEngineFactory(protocolEngineConfigurationFactory);
			} catch (SamlEngineConfigurationException e) {
				LOG.error("Error creating protocol engine factory : ", e);
			}
		}
		return defaultProtocolEngineFactory.getProtocolEngine(ConstantesSP.SAMLENGINE_NAME);
	}
}
