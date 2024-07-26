package com.musinsa.m_backend.mapper;

import com.musinsa.m_backend.dto.base.CategoryDto;
import com.musinsa.m_backend.entity.CategoryEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-24T09:10:49+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryDto toDto(CategoryEntity entity) {
        if ( entity == null ) {
            return null;
        }

        CategoryDto categoryDto = new CategoryDto();

        return categoryDto;
    }

    @Override
    public CategoryEntity toEntity(CategoryDto dto) {
        if ( dto == null ) {
            return null;
        }

        CategoryEntity.CategoryEntityBuilder<?, ?> categoryEntity = CategoryEntity.builder();

        return categoryEntity.build();
    }

    @Override
    public List<CategoryDto> getDtoList(List<CategoryEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CategoryDto> list = new ArrayList<CategoryDto>( entityList.size() );
        for ( CategoryEntity categoryEntity : entityList ) {
            list.add( toDto( categoryEntity ) );
        }

        return list;
    }

    @Override
    public CategoryEntity map(Long categoryIdx) {
        if ( categoryIdx == null ) {
            return null;
        }

        CategoryEntity.CategoryEntityBuilder<?, ?> categoryEntity = CategoryEntity.builder();

        categoryEntity.categoryIdx( categoryIdx );

        return categoryEntity.build();
    }

    @Override
    public CategoryDto.Info toInfoDto(CategoryEntity categoryEntity) {
        if ( categoryEntity == null ) {
            return null;
        }

        CategoryDto.Info.InfoBuilder info = CategoryDto.Info.builder();

        info.categoryIdx( categoryEntity.getCategoryIdx() );
        info.categoryName( categoryEntity.getCategoryName() );

        return info.build();
    }

    @Override
    public CategoryEntity fromInfoToEntity(CategoryDto.Info categoryDto) {
        if ( categoryDto == null ) {
            return null;
        }

        CategoryEntity.CategoryEntityBuilder<?, ?> categoryEntity = CategoryEntity.builder();

        categoryEntity.categoryIdx( categoryDto.getCategoryIdx() );
        categoryEntity.categoryName( categoryDto.getCategoryName() );

        return categoryEntity.build();
    }

    @Override
    public List<CategoryDto.Info> toInfoDto(List<CategoryEntity> categoryEntity) {
        if ( categoryEntity == null ) {
            return null;
        }

        List<CategoryDto.Info> list = new ArrayList<CategoryDto.Info>( categoryEntity.size() );
        for ( CategoryEntity categoryEntity1 : categoryEntity ) {
            list.add( toInfoDto( categoryEntity1 ) );
        }

        return list;
    }
}
