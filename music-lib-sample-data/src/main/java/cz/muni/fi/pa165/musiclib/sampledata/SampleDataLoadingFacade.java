package cz.muni.fi.pa165.musiclib.sampledata;

import java.io.IOException;

/**
 * Inserts initial data to the database.
 *
 * @author xstefank (422697@mail.muni.cz)
 * @version 12/5/15
 */
public interface SampleDataLoadingFacade {

    void loadData() throws IOException;
}
