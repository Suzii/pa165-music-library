package cz.muni.fi.pa165.musiclib.service;

import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 
 * @author Zuzana Dankovcikova
 * @version 15/11/16
 */
@Service
public class BeanMappingServiceImpl implements BeanMappingService {

    @Inject
    private Mapper mapper;
    
    @Override
    public <T> List<T> mapTo(Collection<?> source, Class<T> mapToClass) {
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
