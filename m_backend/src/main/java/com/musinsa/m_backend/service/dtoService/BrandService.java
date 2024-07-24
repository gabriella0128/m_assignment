package com.musinsa.m_backend.service.dtoService;

import com.musinsa.m_backend.dto.base.BrandDto;
import com.musinsa.m_backend.entity.BrandEntity;
import com.musinsa.m_backend.mapper.BrandMapper;
import com.musinsa.m_backend.repository.brand.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Transactional(readOnly = true)
    public List<BrandDto.Info> findAll(){
        return brandMapper.toInfoDto(brandRepository.findAll());
    }

    @Transactional(readOnly = true)
    public BrandDto.Info findBrandByBrandIdx(Long brandIdx) {
        return brandMapper.toInfoDto(brandRepository.findBrandByBrandIdx(brandIdx).orElse(null));
    }

    @Transactional
    public BrandDto.Info save(BrandDto.Info brand) {
        BrandEntity brandEntity = brandMapper.fromInfoToEntity(brand);

        BrandEntity saved = brandRepository.save(brandEntity);

        return brandMapper.toInfoDto(saved);
    }

    @Transactional
    public void deleteBrand(Long brandIdx){
        brandRepository.deleteBrandByBrandIdx(brandIdx);
    }
}
