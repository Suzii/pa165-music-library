package cz.muni.fi.pa165.musiclib.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import cz.muni.fi.pa165.musiclib.config.ServiceConfiguration;
import cz.muni.fi.pa165.musiclib.dto.AlbumDTO;
import cz.muni.fi.pa165.musiclib.dto.SongDTO;
import cz.muni.fi.pa165.musiclib.mixins.AlbumDTOMixin;
import cz.muni.fi.pa165.musiclib.mixins.SongDTOMixin;
import cz.muni.fi.pa165.musiclib.sampledata.MusicLibSampleDataConfig;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Milan Seman
 * @version 15/12/12
 */
@EnableWebMvc
@Configuration
@Import({ServiceConfiguration.class, MusicLibSampleDataConfig.class})
@ComponentScan(basePackages = {"cz.muni.fi.pa165.musiclib.controllers"})
public class RootWebContext extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AllowOriginInterceptor()); 
    }
    
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    
    @Bean
    @Primary
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH));
        
        objectMapper.addMixIn(SongDTO.class, SongDTOMixin.class);
        objectMapper.addMixIn(AlbumDTO.class, AlbumDTOMixin.class);
        objectMapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        
        jsonConverter.setObjectMapper(objectMapper);
        return jsonConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customJackson2HttpMessageConverter());
    }
    
}
