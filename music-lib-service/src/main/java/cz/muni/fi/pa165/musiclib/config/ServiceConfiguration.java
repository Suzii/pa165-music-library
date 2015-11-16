/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.musiclib.config;

import cz.muni.fi.pa165.musiclib.PersistenceSampleApplicationContext;
import cz.muni.fi.pa165.musiclib.dto.AlbumDTO;
import cz.muni.fi.pa165.musiclib.dto.GenreDTO;
import cz.muni.fi.pa165.musiclib.dto.MusicianDTO;
import cz.muni.fi.pa165.musiclib.dto.SongDTO;
import cz.muni.fi.pa165.musiclib.dto.UserDTO;
import cz.muni.fi.pa165.musiclib.entity.Album;
import cz.muni.fi.pa165.musiclib.entity.Genre;
import cz.muni.fi.pa165.musiclib.entity.Musician;
import cz.muni.fi.pa165.musiclib.entity.Song;
import cz.muni.fi.pa165.musiclib.entity.User;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *
 * @author zdank
 * @version 16/11/2015
 */
@Configuration
@Import(PersistenceSampleApplicationContext.class)
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

            mapping(AlbumDTO.class, Album.class);
            mapping(SongDTO.class, Song.class);
            mapping(MusicianDTO.class, Musician.class);
            mapping(GenreDTO.class, Genre.class);
            mapping(UserDTO.class, User.class);
        }
    }
}
