package com.dankan.userservice.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Model {

    public static ModelMapper mapper;

    public Model(ModelMapper modelMapper) {
        mapper = modelMapper;
    }
}
