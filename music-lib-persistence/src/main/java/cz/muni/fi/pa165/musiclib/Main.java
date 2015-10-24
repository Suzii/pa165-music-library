package cz.muni.fi.pa165.musiclib;

import cz.muni.fi.pa165.musiclib.dao.SongDao;
import cz.muni.fi.pa165.musiclib.entity.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xstefank
 * @version 10/24/15
 */
@ContextConfiguration(classes=PersistenceSampleApplicationContext.class)
public class Main {

    //TODO not working
    @Autowired
    private static SongDao songDao;

    public static void main(String[] args) {
//        // The following line is here just to start up a in-memory database
//        new AnnotationConfigApplicationContext(InMemoryDatabaseSpring.class);


        System.out.println("main method");
        songDao.create(new Song());
    }
}
