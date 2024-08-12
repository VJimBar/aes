
package es.e4f.action.sp.metadata;


import es.e4f.exception.ApplicationException;
import es.e4f.utils.SPUtil;
import eu.eidas.auth.engine.metadata.MetadataFetcherI;
import eu.eidas.auth.engine.metadata.impl.CachingMetadataFetcher;
import eu.eidas.auth.commons.EIDASUtil;
import eu.eidas.auth.engine.metadata.IStaticMetadataChangeListener;
import eu.eidas.auth.engine.metadata.impl.FileMetadataLoader;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

/**
 * The implementation of the {@link MetadataFetcherI} interface for SP.
 *
 * @since 1.1
 */
public class SPCachingMetadataFetcher extends CachingMetadataFetcher implements IStaticMetadataChangeListener {

    private static final Logger LOG = LoggerFactory.getLogger(SPCachingMetadataFetcher.class);
    private Set<String> trustedEntityDescriptorsSet = new HashSet<String>();
    /**
     * initialized with a list of urls corresponding to EntityDescriptor not needing signature validation
     */
    private String trustedEntityDescriptors = "";

    public SPCachingMetadataFetcher() throws ApplicationException {
        super();
        setCache(new SPMetadataCache());
        if (StringUtils.isNotEmpty(SPUtil.getMetadataRepositoryPath())) {
            FileMetadataLoader fp = new FileMetadataLoader();
            fp.setRepositoryPath(SPUtil.getMetadataRepositoryPath());
            setMetadataLoaderPlugin(fp);
        }
        initProcessor();
    }

    @Override
    public boolean isHttpRetrievalEnabled() {
        try {
			return SPUtil.isMetadataHttpFetchEnabled();
		} catch (ApplicationException e) {
			return true;
		}
    }

    @Override
    protected boolean mustUseHttps() {
        return false;
    }

    @Override
    protected boolean mustValidateSignature(@Nonnull String url) {
        try {
			setTrustedEntityDescriptors(SPUtil.getTrustedEntityDescriptors());
		} catch (ApplicationException e) {
			LOG.error("Error al invocar al método SPUtil.getTrustedEntityDescriptors()");
		}
        return super.mustValidateSignature(url);
    }
    
    
    public void setTrustedEntityDescriptors(String trustedEntityDescriptors) {
        this.trustedEntityDescriptors = trustedEntityDescriptors;
        setTrustedEntityDescriptorsSet(EIDASUtil.parseSemicolonSeparatedList(trustedEntityDescriptors));
    }

    public void setTrustedEntityDescriptorsSet(Set<String> trustedEntityDescriptorsSet) {
        this.trustedEntityDescriptorsSet = trustedEntityDescriptorsSet;
    }


}
