package cz.muni.fi.pa165.musiclib.service;

import java.util.Collection;
import java.util.List;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Service for mapping beans.
 * 
 * @author zdank
 * @version 16/11/2015
 */
public interface BeanMappingService {
    
    public <T> List<T> mappTo(Collection<?> source, Class<T> mapToClass);
    
    public <T> T mapTo(Object source, Class<T> mapToClass);
    
    public Mapper getMapper();
}
