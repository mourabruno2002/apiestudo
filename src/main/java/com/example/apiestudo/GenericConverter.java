package com.example.apiestudo;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GenericConverter {

    private final ModelMapper modelMapper;

    public GenericConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <S, T> T map(S source, Class<T> targetClass ) {

        return modelMapper.map(source, targetClass);
    }
}
