package cz.muni.fi.pa165.musiclib.service;

import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * Service for mapping beans.
 * 
 * @author Zuzana Dankovcikova
 * @version 15/11/16
 */
public interface BeanMappingService {
    
    public <T> List<T> mapTo(Collection<?> source, Class<T> mapToClass);
    
    public <T> T mapTo(Object source, Class<T> mapToClass);
    
    public Mapper getMapper();
}
