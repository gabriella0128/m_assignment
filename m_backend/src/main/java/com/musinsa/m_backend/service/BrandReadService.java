package com.musinsa.m_backend.service;

import com.musinsa.m_backend.dto.BrandProcessDto;
import com.musinsa.m_backend.dto.BrandReadDto;
import com.musinsa.m_backend.dto.base.BrandDto;
import com.musinsa.m_backend.service.dtoService.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BrandReadService {
    private final BrandService brandService;

    public BrandReadDto.BrandReadListResponse readBrandList(){
        try {
            List<BrandDto.Info> brandList = brandService.findAll();
            List<BrandReadDto.BrandReadItem> brandItemList = brandList.stream().map(brand -> BrandReadDto.BrandReadItem.builder()
                    .brandIdx(brand.getBrandIdx())
                    .brandName(brand.getBrandName())
                    .brandImage(brand.getBrandImage())
                    .brandDesc(brand.getBrandDesc())
                    .build()).toList();

            return BrandReadDto.BrandReadListResponse.builder()
                    .result(true)
                    .reason("SUCCESS")
                    .brandList(brandItemList)
                    .build();
        } catch (Exception e){
            e.printStackTrace();
        }

        return BrandReadDto.BrandReadListResponse.builder()
                .result(false)
                .reason("Unexpected Error")
                .build();
    }

    public BrandReadDto.BrandReadListSelectResponse readBrandSelectList(){
        try {
            List<BrandDto.Info> brandSelectList = brandService.findAll();
            List<BrandReadDto.BrandReadSelectItem> brandItemSelectList = brandSelectList.stream().map(brand -> BrandReadDto.BrandReadSelectItem.builder()
                    .brandIdx(brand.getBrandIdx())
                    .brandName(brand.getBrandName())
                    .build()).toList();

            return BrandReadDto.BrandReadListSelectResponse.builder()
                    .result(true)
                    .reason("SUCCESS")
                    .brandSelectList(brandItemSelectList)
                    .build();
        } catch (Exception e){
            e.printStackTrace();
        }

        return BrandReadDto.BrandReadListSelectResponse.builder()
                .result(false)
                .reason("Unexpected Error")
                .build();
    }

    public BrandReadDto.BrandReadDetailResponse readBrandDetail(Long brandIdx){
        try {
            BrandDto.Info foundBrand = brandService.findBrandByBrandIdx(brandIdx);
            if(Objects.isNull(foundBrand)){
                return BrandReadDto.BrandReadDetailResponse.builder()
                        .result(false)
                        .reason("Brand Not Found")
                        .build();
            }

            BrandReadDto.BrandReadItem brandDetail = BrandReadDto.BrandReadItem.builder()
                    .brandIdx(foundBrand.getBrandIdx())
                    .brandName(foundBrand.getBrandName())
                    .brandImage(foundBrand.getBrandImage())
                    .brandDesc(foundBrand.getBrandDesc())
                    .build();

            return BrandReadDto.BrandReadDetailResponse.builder()
                    .result(true)
                    .reason("SUCCESS")
                    .brandDetail(brandDetail)
                    .build();

        }catch (Exception e){
            e.printStackTrace();
        }
        return BrandReadDto.BrandReadDetailResponse.builder()
                .result(false)
                .reason("Unexpected Error")
                .build();
    }


}
