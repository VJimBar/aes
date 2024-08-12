package es.e4f.action.firma.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.clave.sp.ApplicationContextProvider;
import es.e4f.exception.ApplicationException;
import es.e4f.utils.ConstantesSP;
import eu.eidas.auth.engine.ProtocolEngineFactory;
import eu.eidas.auth.engine.ProtocolEngineI;
import eu.eidas.auth.engine.configuration.SamlEngineConfigurationException;
import eu.eidas.auth.engine.configuration.dom.ProtocolEngineConfigurationFactory;


public class FirmaUtil {

	private static final Logger LOG = LoggerFactory.getLogger(FirmaUtil.class);
	
	
	static ProtocolEngineConfigurationFactory protocolEngineConfigurationFactory = null;
	static ProtocolEngineFactory defaultProtocolEngineFactory = null;
	
	private FirmaUtil() {
	}



    public static String getConfigFilePath() {
      //  String envLocation = System.getenv().get(ConstantesFirma.SP_CONFIG_REPOSITORY);
      //  return System.getProperty(ConstantesFirma.SP_CONFIG_REPOSITORY, envLocation);   
    	return ((String)ApplicationContextProvider.getApplicationContext().getBean(ConstantesSP.SP_REPO_BEAN_NAME)).trim();
        
    }

    private static Properties loadConfigs(String fileName) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(FirmaUtil.getConfigFilePath()+fileName));
        return properties;
    }

    public static Properties loadFirmaConfigs() throws ApplicationException {
        try {
            return FirmaUtil.loadConfigs(ConstantesFirma.FIRMA_PROPERTIES);
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
    	Properties spProperties = loadFirmaConfigs();
        return spProperties.getProperty(ConstantesFirma.FIRMA_METADATA_ACTIVATE) == null || Boolean.parseBoolean(
        		spProperties.getProperty(ConstantesFirma.FIRMA_METADATA_ACTIVATE));
    }

    /**
     * @return true when the metadata support should be active
     * @throws ApplicationException 
     */
    public static boolean isMetadataHttpFetchEnabled() throws ApplicationException {
    	Properties spProperties = loadFirmaConfigs();
        return spProperties.getProperty(ConstantesFirma.FIRMA_METADATA_HTTPFETCH) == null || Boolean.parseBoolean(
        		spProperties.getProperty(ConstantesFirma.FIRMA_METADATA_HTTPFETCH));
    }

    /**
     * @return metadata directory
     * @throws ApplicationException 
     */
    public static String getMetadataRepositoryPath() throws ApplicationException {
    	Properties spProperties = loadFirmaConfigs();
        return spProperties.getProperty(ConstantesFirma.FIRMA_METADATA_REPOPATH);
    }

    /**
     * @return true metadata signature must be validated for those not in trusted list
     * @throws ApplicationException 
     */
    public static boolean isValidateEntityDescriptorSignatureEnabled() throws ApplicationException {
        Properties properties = FirmaUtil.loadFirmaConfigs();
        return properties.getProperty(ConstantesFirma.FIRMA_METADATA_VALIDATESIGN) == null || Boolean.parseBoolean(
                properties.getProperty(ConstantesFirma.FIRMA_METADATA_VALIDATESIGN));
    }

    public static String getTrustedEntityDescriptors() throws ApplicationException {
        Properties properties = FirmaUtil.loadFirmaConfigs();
        return properties.getProperty(ConstantesFirma.FIRMA_METADATA_TRUSTEDDS, "");
    }

  
    public static synchronized ProtocolEngineI getProtocolEngine() {
		if (defaultProtocolEngineFactory == null) {
			protocolEngineConfigurationFactory = new ProtocolEngineConfigurationFactory(ConstantesFirma.SP_SAMLENGINE_FILE,
					null, getConfigFilePath());
			try {
				defaultProtocolEngineFactory = new ProtocolEngineFactory(protocolEngineConfigurationFactory);
			} catch (SamlEngineConfigurationException e) {
				LOG.error("Error creating protocol engine factory : ", e);
			}
		}
		return defaultProtocolEngineFactory.getProtocolEngine(ConstantesFirma.SAMLENGINE_NAME);
	}
}
