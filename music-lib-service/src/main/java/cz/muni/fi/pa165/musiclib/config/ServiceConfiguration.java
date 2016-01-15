package cz.muni.fi.pa165.musiclib.config;

import cz.muni.fi.pa165.musiclib.PersistenceSampleApplicationContext;
import cz.muni.fi.pa165.musiclib.dto.AlbumDTO;
import cz.muni.fi.pa165.musiclib.dto.GenreDTO;
import cz.muni.fi.pa165.musiclib.dto.MajorAlbumGenreDTO;
import cz.muni.fi.pa165.musiclib.dto.MusicianDTO;
import cz.muni.fi.pa165.musiclib.dto.SongDTO;
import cz.muni.fi.pa165.musiclib.dto.UserDTO;
import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.entity.Genre;
import cz.muni.fi.pa165.musiclib.entity.Musician;
import cz.muni.fi.pa165.musiclib.entity.Song;
import cz.muni.fi.pa165.musiclib.entity.User;
import cz.muni.fi.pa165.musiclib.facade.UserFacadeImpl;
import cz.muni.fi.pa165.musiclib.service.AlbumService;
import cz.muni.fi.pa165.musiclib.service.AlbumServiceImpl;
import cz.muni.fi.pa165.musiclib.service.GenreService;
import cz.muni.fi.pa165.musiclib.service.GenreServiceImpl;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *
 * @author zdank
 * @version 16/11/2015
 */
@Configuration
@Import(PersistenceSampleApplicationContext.class)
@ComponentScan(basePackageClasses={GenreServiceImpl.class, UserFacadeImpl.class})
public class ServiceConfiguration {

    @Bean
    public Mapper dozer() {
        DozerBeanMapper dozer = new DozerBeanMapper();
        dozer.addMapping(new DozerCustomConfig());
        return dozer;
    }

    public class DozerCustomConfig extends BeanMappingBuilder {

        @Override
        protected void configure() {
            mapping(Album.class, AlbumDTO.class);
            mapping(Song.class, SongDTO.class);
            mapping(Musician.class, MusicianDTO.class);
            mapping(Genre.class, GenreDTO.class);
            mapping(User.class, UserDTO.class);
            mapping(AlbumServiceImpl.GenreResult.class, MajorAlbumGenreDTO.class);
        }
    }
}
