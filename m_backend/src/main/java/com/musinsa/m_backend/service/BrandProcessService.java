package com.musinsa.m_backend.service;

import com.musinsa.m_backend.dto.BrandProcessDto;
import com.musinsa.m_backend.dto.base.BrandDto;
import com.musinsa.m_backend.service.dtoService.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BrandProcessService {

    private final BrandService brandService;

    public BrandProcessDto.BrandProcessResponse insertBrand(BrandProcessDto.BrandUpsertRequest request){
        try {
            BrandDto.Info brand = BrandDto.Info.builder()
                    .brandName(request.getBrandName())
                    .brandDesc(request.getBrandDesc())
                    .brandImage(request.getBrandImage())
                    .build();

            brandService.save(brand);

            return BrandProcessDto.BrandProcessResponse.builder()
                    .result(true)
                    .reason("SUCCESS")
                    .build();

        }catch (Exception e){
            e.printStackTrace();
        }

        return BrandProcessDto.BrandProcessResponse.builder()
                .result(false)
                .reason("Unexpected Error").build();

    }

    public BrandProcessDto.BrandProcessResponse updateBrand(BrandProcessDto.BrandUpsertRequest request){
        try {
            BrandDto.Info foundBrand = brandService.findBrandByBrandIdx(request.getBrandIdx());

            if(Objects.isNull(foundBrand)){
                return BrandProcessDto.BrandProcessResponse.builder()
                        .result(false)
                        .reason("Brand Not Found").build();
            }

            brandService.save(foundBrand.toBuilder()
                    .brandName(request.getBrandName())
                    .brandDesc(request.getBrandDesc())
                    .brandImage(request.getBrandImage())
                    .build());

            return BrandProcessDto.BrandProcessResponse.builder()
                    .result(true)
                    .reason("SUCCESS")
                    .build();

        }catch (Exception e){
            e.printStackTrace();
        }
        return BrandProcessDto.BrandProcessResponse.builder()
                .result(false)
                .reason("Unexpected Error").build();

    }

    public BrandProcessDto.BrandProcessResponse deleteBrand(BrandProcessDto.BrandDeleteRequest request){
        try {
            BrandDto.Info foundBrand = brandService.findBrandByBrandIdx(request.getBrandIdx());

            if(Objects.isNull(foundBrand)){
                return BrandProcessDto.BrandProcessResponse.builder()
                        .result(false)
                        .reason("Brand Not Found").build();
            }

            brandService.save(foundBrand.toBuilder()
                    .useYn(false)
                    .deleteDt(LocalDateTime.now())
                    .build());

            return BrandProcessDto.BrandProcessResponse.builder()
                    .result(true)
                    .reason("SUCCESS")
                    .build();
        }catch (Exception e){
            e.printStackTrace();
        }

        return BrandProcessDto.BrandProcessResponse.builder()
                .result(false)
                .reason("Unexpected Error").build();
    }

}
