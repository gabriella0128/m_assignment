package com.musinsa.m_backend.mapper;

import com.musinsa.m_backend.common.mapper.GenericMapper;
import com.musinsa.m_backend.dto.base.ProductDto;
import com.musinsa.m_backend.entity.ProductEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProductMapper extends GenericMapper<ProductDto, ProductEntity> {

    ProductEntity map(Long productIdx);

    ProductDto.Info toInfoDto(ProductEntity productEntity);

    ProductEntity fromInfoToEntity(ProductDto.Info productDto);

    @IterableMapping(elementTargetType = ProductDto.Info.class)
    List<ProductDto.Info> toInfoDto(List<ProductEntity> productEntity);
}
