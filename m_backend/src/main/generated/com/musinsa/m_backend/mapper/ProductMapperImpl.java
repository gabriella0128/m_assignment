package com.musinsa.m_backend.mapper;

import com.musinsa.m_backend.dto.base.ProductDto;
import com.musinsa.m_backend.entity.BrandEntity;
import com.musinsa.m_backend.entity.CategoryEntity;
import com.musinsa.m_backend.entity.ProductEntity;
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
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDto toDto(ProductEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ProductDto productDto = new ProductDto();

        return productDto;
    }

    @Override
    public ProductEntity toEntity(ProductDto dto) {
        if ( dto == null ) {
            return null;
        }

        ProductEntity.ProductEntityBuilder<?, ?> productEntity = ProductEntity.builder();

        return productEntity.build();
    }

    @Override
    public List<ProductDto> getDtoList(List<ProductEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ProductDto> list = new ArrayList<ProductDto>( entityList.size() );
        for ( ProductEntity productEntity : entityList ) {
            list.add( toDto( productEntity ) );
        }

        return list;
    }

    @Override
    public ProductEntity map(Long productIdx) {
        if ( productIdx == null ) {
            return null;
        }

        ProductEntity.ProductEntityBuilder<?, ?> productEntity = ProductEntity.builder();

        productEntity.productIdx( productIdx );

        return productEntity.build();
    }

    @Override
    public ProductDto.Info toInfoDto(ProductEntity productEntity) {
        if ( productEntity == null ) {
            return null;
        }

        ProductDto.Info.InfoBuilder info = ProductDto.Info.builder();

        info.brandIdx( productEntityBrandBrandIdx( productEntity ) );
        info.categoryIdx( productEntityCategoryCategoryIdx( productEntity ) );
        info.productIdx( productEntity.getProductIdx() );
        info.productName( productEntity.getProductName() );
        info.productDesc( productEntity.getProductDesc() );
        info.productImage( productEntity.getProductImage() );
        info.productPrice( productEntity.getProductPrice() );
        info.version( productEntity.getVersion() );

        return info.build();
    }

    @Override
    public ProductEntity fromInfoToEntity(ProductDto.Info productDto) {
        if ( productDto == null ) {
            return null;
        }

        ProductEntity.ProductEntityBuilder<?, ?> productEntity = ProductEntity.builder();

        productEntity.brand( infoToBrandEntity( productDto ) );
        productEntity.category( infoToCategoryEntity( productDto ) );
        productEntity.version( productDto.getVersion() );
        productEntity.productIdx( productDto.getProductIdx() );
        productEntity.productName( productDto.getProductName() );
        productEntity.productDesc( productDto.getProductDesc() );
        productEntity.productPrice( productDto.getProductPrice() );
        productEntity.productImage( productDto.getProductImage() );

        return productEntity.build();
    }

    @Override
    public List<ProductDto.Info> toInfoDto(List<ProductEntity> productEntity) {
        if ( productEntity == null ) {
            return null;
        }

        List<ProductDto.Info> list = new ArrayList<ProductDto.Info>( productEntity.size() );
        for ( ProductEntity productEntity1 : productEntity ) {
            list.add( toInfoDto( productEntity1 ) );
        }

        return list;
    }

    private Long productEntityBrandBrandIdx(ProductEntity productEntity) {
        if ( productEntity == null ) {
            return null;
        }
        BrandEntity brand = productEntity.getBrand();
        if ( brand == null ) {
            return null;
        }
        Long brandIdx = brand.getBrandIdx();
        if ( brandIdx == null ) {
            return null;
        }
        return brandIdx;
    }

    private Long productEntityCategoryCategoryIdx(ProductEntity productEntity) {
        if ( productEntity == null ) {
            return null;
        }
        CategoryEntity category = productEntity.getCategory();
        if ( category == null ) {
            return null;
        }
        Long categoryIdx = category.getCategoryIdx();
        if ( categoryIdx == null ) {
            return null;
        }
        return categoryIdx;
    }

    protected BrandEntity infoToBrandEntity(ProductDto.Info info) {
        if ( info == null ) {
            return null;
        }

        BrandEntity.BrandEntityBuilder<?, ?> brandEntity = BrandEntity.builder();

        brandEntity.brandIdx( info.getBrandIdx() );

        return brandEntity.build();
    }

    protected CategoryEntity infoToCategoryEntity(ProductDto.Info info) {
        if ( info == null ) {
            return null;
        }

        CategoryEntity.CategoryEntityBuilder<?, ?> categoryEntity = CategoryEntity.builder();

        categoryEntity.categoryIdx( info.getCategoryIdx() );

        return categoryEntity.build();
    }
}
