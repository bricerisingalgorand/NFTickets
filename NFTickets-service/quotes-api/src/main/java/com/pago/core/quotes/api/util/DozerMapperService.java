package com.pago.core.quotes.api.util;

import org.dozer.DozerBeanMapper;
import org.dozer.MappingException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DozerMapperService extends DozerBeanMapper {

    public DozerMapperService(DozerConfiguration dozerConfiguration) {
        super();
        super.setMappingFiles(dozerConfiguration.getMappingFiles());
    }

    public <T, V> Stream<V> mapStream(
            Stream<T> source,
            Class<V> destinationClass
    ) throws MappingException {
        return source.map(from -> {
            return super.map(from, destinationClass);
        });
    }

    public <T, V> List<V> mapList(
            List<T> source,
            Class<V> destinationClass
    ) throws MappingException {
        return this.mapStream(source.stream(), destinationClass)
                .collect(Collectors.toList());
    }
}
