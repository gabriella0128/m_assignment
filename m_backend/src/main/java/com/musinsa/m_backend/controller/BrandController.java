package com.musinsa.m_backend.controller;


import com.musinsa.m_backend.dto.BrandProcessDto;
import com.musinsa.m_backend.service.BrandProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/brand")
@RequiredArgsConstructor
public class BrandController {

    private final BrandProcessService brandProcessService;


    @PostMapping("/create")
    public ResponseEntity<BrandProcessDto.BrandProcessResponse> createBrand(
            @RequestBody final BrandProcessDto.BrandUpsertRequest request) {
        return ResponseEntity.ok().body(brandProcessService.insertBrand(request));
    }

    @PatchMapping("/update")
    public ResponseEntity<BrandProcessDto.BrandProcessResponse> updateBrand(
            @RequestBody final BrandProcessDto.BrandUpsertRequest request) {
        return ResponseEntity.ok().body(brandProcessService.updateBrand(request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BrandProcessDto.BrandProcessResponse> deleteBrand(
            @RequestBody final BrandProcessDto.BrandDeleteRequest request){
        return ResponseEntity.ok().body(brandProcessService.deleteBrand(request));
    }



}
