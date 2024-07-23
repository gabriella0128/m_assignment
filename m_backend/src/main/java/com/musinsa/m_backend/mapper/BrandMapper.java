package com.musinsa.m_backend.mapper;

import com.musinsa.m_backend.common.mapper.GenericMapper;
import com.musinsa.m_backend.dto.base.BrandDto;
import com.musinsa.m_backend.entity.BrandEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface BrandMapper extends GenericMapper<BrandDto, BrandEntity> {

    BrandEntity map(Long brandIdx);

    BrandDto.Info toInfoDto(BrandEntity brandEntity);

    BrandEntity fromInfoToEntity(BrandDto.Info brandDto);

    @IterableMapping(elementTargetType = BrandDto.Info.class)
    List<BrandDto.Info> toInfoDto(List<BrandEntity> brandEntity);
}
