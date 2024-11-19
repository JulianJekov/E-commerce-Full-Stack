package com.example.unique.wear.config;

import com.example.unique.wear.model.dto.category.CategoryTypeDto;
import com.example.unique.wear.model.entity.CategoryType;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.addMappings(new PropertyMap<CategoryTypeDto, CategoryType>() {
            @Override
            protected void configure() {
                skip(destination.getCategory()); // Skip mapping the parent
            }
        });
        return modelMapper;
    }
}
