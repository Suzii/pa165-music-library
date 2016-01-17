package cz.muni.fi.pa165.musiclib.sampledata;

import java.io.IOException;

/**
 * Inserts initial data to the database.
 *
 * @author Martin Stefanko
 * @version 15/12/5
 */
public interface SampleDataLoadingFacade {

    void loadData() throws IOException;
}
