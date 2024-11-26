package com.example.unique.wear.config;

import com.example.unique.wear.model.dto.category.CategoryTypeDto;
import com.example.unique.wear.model.dto.product.ProductDto;
import com.example.unique.wear.model.dto.product.ProductResourceDto;
import com.example.unique.wear.model.dto.product.ProductVariantsDto;
import com.example.unique.wear.model.entity.CategoryType;
import com.example.unique.wear.model.entity.Product;
import com.example.unique.wear.model.entity.ProductResources;
import com.example.unique.wear.model.entity.ProductVariants;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
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

        modelMapper.typeMap(ProductDto.class, Product.class).addMappings(mapper -> {
            mapper.map(ProductDto::getName, Product::setName); // Explicit mapping
            mapper.skip(Product::setProductResources);        // Skip ambiguous fields
            mapper.skip(Product::setProductVariants);         // Skip ambiguous fields
        });

        Converter<Product, String> thumbnailConverter = new Converter<>() {
            @Override
            public String convert(MappingContext<Product, String> context) {
                Product product = context.getSource();
                return product.getProductResources()
                        .stream()
                        .filter(ProductResources::getIsPrimary)
                        .findFirst()
                        .map(ProductResources::getUrl)
                        .orElse(null);
            }
        };
        modelMapper.typeMap(Product.class, ProductDto.class)
                .addMappings(mapper -> mapper
                        .using(thumbnailConverter)
                        .map(src -> src, ProductDto::setThumbnail));



        return modelMapper;
    }
}
