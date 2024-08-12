package es.e4f.action.firma.metadata;

import com.google.common.cache.CacheBuilder;

import es.e4f.action.firma.util.ConstantesFirma;
import es.e4f.action.firma.util.FirmaUtil;
import es.e4f.exception.ApplicationException;
import eu.eidas.auth.commons.EidasStringUtil;
import eu.eidas.auth.commons.xml.opensaml.OpenSamlHelper;
import eu.eidas.auth.engine.metadata.*;
import eu.eidas.encryption.exception.MarshallException;
import eu.eidas.encryption.exception.UnmarshallException;
import eu.eidas.engine.exceptions.EIDASMetadataProviderException;

import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.SignableXMLObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class SPMetadataCache implements IMetadataCachingService {

    private static final Logger LOG = LoggerFactory.getLogger(SPMetadataCache.class);

    private static final String SIGNATURE_HOLDER_ID_PREFIX="signatureholder";

    private ConcurrentMap<String, SerializedEntityDescriptor> map = null;

    protected Map<String, SerializedEntityDescriptor> getMap() {
        if (map == null) {
           try {
			map = CacheBuilder.newBuilder()
			            .expireAfterAccess(Long.parseLong(FirmaUtil.loadFirmaConfigs().getProperty(ConstantesFirma.FIRMA_METADATA_RETENTION, "86400")),
			                    TimeUnit.SECONDS)
			            .maximumSize(10000L).<String, SerializedEntityDescriptor>build().asMap();
		
           } catch (ApplicationException e) {
        	   LOG.error("Error al invocar a getMap()");
           }
        }
        return map;
    }

    private class SerializedEntityDescriptor {
        /**
         * the entitydescriptor serialized as xml
         */
        private String serializedEntityDescriptor;

        /**
         * the type/origin (either statically loaded or retrieved from the network)
         */
        private EntityDescriptorType type;

        public SerializedEntityDescriptor(String descriptor, EntityDescriptorType type) {
            setSerializedEntityDescriptor(descriptor);
            setType(type);
        }

        public String getSerializedEntityDescriptor() {
            return serializedEntityDescriptor;
        }

        public void setSerializedEntityDescriptor(String serializedEntityDescriptor) {
            this.serializedEntityDescriptor = serializedEntityDescriptor;
        }

        public EntityDescriptorType getType() {
            return type;
        }

        public void setType(EntityDescriptorType type) {
            this.type = type;
        }
    }


    @Override
    public final MetadataPair getDescriptor(String url) throws EIDASMetadataProviderException {
        if(getMap()!=null){
            SerializedEntityDescriptor content=getMap().get(url);
            if(content!=null && !content.getSerializedEntityDescriptor().isEmpty()) {
                try {
                	EntityDescriptor ed= deserializeEntityDescriptor(content.getSerializedEntityDescriptor());
                    MetadataPair mp = new MetadataPair();
                    mp.setEntityDescriptor(ed);
                    return mp;
                } catch (UnmarshallException e) {
                    LOG.error("Unable to deserialize metadata entity descriptor from cache for "+url);
                    LOG.error(e.getStackTrace().toString());
                    throw new EIDASMetadataProviderException(e.getMessage());
                }
            }
        }
        return null;
    }

    @Override
    public final void putDescriptor(String url,  MetadataPair mp, EntityDescriptorType type) {
        if(getMap()!=null){
        	if(mp==null || mp.getEntityDescriptor()==null){
                getMap().remove(url);
            }else {
            	String content = serializeEntityDescriptor(mp.getEntityDescriptor());
                if (content != null && !content.isEmpty()) {
                    getMap().put(url, new SerializedEntityDescriptor(content, type));
                }
            }
        }
    }
    
    @Override
    public final EntityDescriptorType getDescriptorType(String url) {
        if (getMap() != null) {
            SerializedEntityDescriptor content = getMap().get(url);
            if (content != null) {
                return content.getType();
            }
        }
        return null;
    }

    private String serializeEntityDescriptor(XMLObject ed){
        try {
            return EidasStringUtil.toString(OpenSamlHelper.marshall(ed));
        } catch (MarshallException e) {
            throw new IllegalStateException(e);
        }
    }

    private EntityDescriptor deserializeEntityDescriptor(String content) throws UnmarshallException {
        EntityDescriptorContainer container = MetadataUtil.deserializeEntityDescriptor(content);
        return container.getEntityDescriptors().isEmpty()?null:container.getEntityDescriptors().get(0);
    }

    @Override
    public void putDescriptorSignatureHolder(String url, SignableXMLObject container){
        getMap().put(SIGNATURE_HOLDER_ID_PREFIX+url, new SerializedEntityDescriptor(serializeEntityDescriptor(container), EntityDescriptorType.NONE));
    }

    @Override
    public void putDescriptorSignatureHolder(String url, EntityDescriptorContainer container){
        if(container.getSerializedEntitesDescriptor()!=null){
            getMap().put(SIGNATURE_HOLDER_ID_PREFIX+url, new SerializedEntityDescriptor(EidasStringUtil.toString(container.getSerializedEntitesDescriptor()), EntityDescriptorType.SERIALIZED_SIGNATURE_HOLDER));
        }else{
            putDescriptorSignatureHolder(url, container.getEntitiesDescriptor());
        }
    }

	

}
