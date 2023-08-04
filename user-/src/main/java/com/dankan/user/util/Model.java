package com.dankan.user.util;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Model {

    public static ModelMapper mapper;

    public Model(ModelMapper modelMapper) {
        mapper = modelMapper;
    }
}
