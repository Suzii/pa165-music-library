package cz.muni.fi.pa165.musiclib.sampledata;

import cz.muni.fi.pa165.musiclib.config.ServiceConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;

/**
 * Adds the SampleDataLoadingFacade bean to the ServiceConfiguration.
 *
 * @author xstefank (422697@mail.muni.cz)
 * @version 12/5/15
 */
@Configuration
@Import(ServiceConfiguration.class)
@ComponentScan(basePackageClasses = SampleDataLoadingFacadeImpl.class)
public class MusicLibSampleDataConfig {

    private static final Logger log = LoggerFactory.getLogger(MusicLibSampleDataConfig.class);

    @Inject
    private SampleDataLoadingFacade sampleDataLoadingFacade;

    @PostConstruct
    public void dataLoad() throws IOException {
        log.debug("dataLoad()");
        sampleDataLoadingFacade.loadData();
    }
}
