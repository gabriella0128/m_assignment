package com.musinsa.m_backend.mapper;

import com.musinsa.m_backend.common.mapper.GenericMapper;
import com.musinsa.m_backend.dto.base.CategoryDto;
import com.musinsa.m_backend.entity.CategoryEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper extends GenericMapper<CategoryDto, CategoryEntity> {

    CategoryEntity map(Long categoryIdx);

    CategoryDto.Info toInfoDto(CategoryEntity categoryEntity);

    CategoryEntity fromInfoToEntity(CategoryDto.Info categoryDto);

    @IterableMapping(elementTargetType = CategoryDto.Info.class)
    List<CategoryDto.Info> toInfoDto(List<CategoryEntity> categoryEntity);
}
