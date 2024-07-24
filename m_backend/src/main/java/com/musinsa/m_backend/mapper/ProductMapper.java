package com.musinsa.m_backend.mapper;

import com.musinsa.m_backend.common.mapper.GenericMapper;
import com.musinsa.m_backend.dto.base.ProductDto;
import com.musinsa.m_backend.entity.ProductEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProductMapper extends GenericMapper<ProductDto, ProductEntity> {

    ProductEntity map(Long productIdx);

    @Mapping(source = "brand.brandIdx", target = "brandIdx")
    @Mapping(source = "category.categoryIdx", target = "categoryIdx")
    ProductDto.Info toInfoDto(ProductEntity productEntity);

    @Mapping(source = "brandIdx", target = "brand.brandIdx")
    @Mapping(source = "categoryIdx", target = "category.categoryIdx")
    ProductEntity fromInfoToEntity(ProductDto.Info productDto);

    @Mapping(source = "brand.brandIdx", target = "brandIdx")
    @Mapping(source = "category.categoryIdx", target = "categoryIdx")
    @IterableMapping(elementTargetType = ProductDto.Info.class)
    List<ProductDto.Info> toInfoDto(List<ProductEntity> productEntity);
}
