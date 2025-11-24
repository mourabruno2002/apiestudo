package com.example.apiestudo.utils;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MapperUtils {

    private final ModelMapper modelMapper;

    public MapperUtils(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <S, I> I map(S source, Class<I> targetClass) {
        return modelMapper.map(source, targetClass);
    }
}
