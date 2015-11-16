package cz.muni.fi.pa165.musiclib.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author zdank
 * @version 16/11/2015
 */
@Service
public class BeanMappingServiceImpl implements BeanMappingService {

    @Inject
    private Mapper mapper;
    
    @Override
    public <T> List<T> mappTo(Collection<?> source, Class<T> mapToClass) {
        List<T> result = new ArrayList<>();
        for(Object o : source){
            result.add(mapTo(o, mapToClass));
        }
        
        return result;
    }

    @Override
    public <T> T mapTo(Object source, Class<T> mapToClass) {
        return mapper.map(source, mapToClass);
    }

    @Override
    public Mapper getMapper() {
        return mapper;
    }
    
}
