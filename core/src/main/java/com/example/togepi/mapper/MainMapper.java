package com.example.togepi.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;

public class MainMapper extends ConfigurableMapper {

    @Override
    public void configure(MapperFactory mapperFactory) {
        mapperFactory.registerMapper(new OrderCsvMapper());
    }
}
