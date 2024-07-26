package com.musinsa.m_backend.mapper;

import com.musinsa.m_backend.dto.base.BrandDto;
import com.musinsa.m_backend.entity.BrandEntity;
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
public class BrandMapperImpl implements BrandMapper {

    @Override
    public BrandDto toDto(BrandEntity entity) {
        if ( entity == null ) {
            return null;
        }

        BrandDto brandDto = new BrandDto();

        return brandDto;
    }

    @Override
    public BrandEntity toEntity(BrandDto dto) {
        if ( dto == null ) {
            return null;
        }

        BrandEntity.BrandEntityBuilder<?, ?> brandEntity = BrandEntity.builder();

        return brandEntity.build();
    }

    @Override
    public List<BrandDto> getDtoList(List<BrandEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<BrandDto> list = new ArrayList<BrandDto>( entityList.size() );
        for ( BrandEntity brandEntity : entityList ) {
            list.add( toDto( brandEntity ) );
        }

        return list;
    }

    @Override
    public BrandEntity map(Long brandIdx) {
        if ( brandIdx == null ) {
            return null;
        }

        BrandEntity.BrandEntityBuilder<?, ?> brandEntity = BrandEntity.builder();

        brandEntity.brandIdx( brandIdx );

        return brandEntity.build();
    }

    @Override
    public BrandDto.Info toInfoDto(BrandEntity brandEntity) {
        if ( brandEntity == null ) {
            return null;
        }

        BrandDto.Info.InfoBuilder info = BrandDto.Info.builder();

        info.brandIdx( brandEntity.getBrandIdx() );
        info.brandName( brandEntity.getBrandName() );
        info.brandDesc( brandEntity.getBrandDesc() );
        info.brandImage( brandEntity.getBrandImage() );
        info.useYn( brandEntity.getUseYn() );
        info.deleteDt( brandEntity.getDeleteDt() );
        info.version( brandEntity.getVersion() );

        return info.build();
    }

    @Override
    public BrandEntity fromInfoToEntity(BrandDto.Info brandDto) {
        if ( brandDto == null ) {
            return null;
        }

        BrandEntity.BrandEntityBuilder<?, ?> brandEntity = BrandEntity.builder();

        brandEntity.version( brandDto.getVersion() );
        brandEntity.useYn( brandDto.getUseYn() );
        brandEntity.deleteDt( brandDto.getDeleteDt() );
        brandEntity.brandIdx( brandDto.getBrandIdx() );
        brandEntity.brandName( brandDto.getBrandName() );
        brandEntity.brandDesc( brandDto.getBrandDesc() );
        brandEntity.brandImage( brandDto.getBrandImage() );

        return brandEntity.build();
    }

    @Override
    public List<BrandDto.Info> toInfoDto(List<BrandEntity> brandEntity) {
        if ( brandEntity == null ) {
            return null;
        }

        List<BrandDto.Info> list = new ArrayList<BrandDto.Info>( brandEntity.size() );
        for ( BrandEntity brandEntity1 : brandEntity ) {
            list.add( toInfoDto( brandEntity1 ) );
        }

        return list;
    }
}
