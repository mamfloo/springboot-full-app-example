package com.library.demo.utils;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MapperConfig {

    @Bean @Primary
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
