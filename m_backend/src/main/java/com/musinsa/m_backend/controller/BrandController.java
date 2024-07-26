package com.musinsa.m_backend.controller;


import com.musinsa.m_backend.dto.BrandProcessDto;
import com.musinsa.m_backend.dto.BrandReadDto;
import com.musinsa.m_backend.service.BrandProcessService;
import com.musinsa.m_backend.service.BrandReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/brand")
@RequiredArgsConstructor
public class BrandController {

    private final BrandProcessService brandProcessService;
    private final BrandReadService brandReadService;

    @GetMapping("/list")
    public ResponseEntity<BrandReadDto.BrandReadListResponse> readBrandList(){
        return ResponseEntity.ok().body(brandReadService.readBrandList());
    }

    @GetMapping("/list/select")
    public ResponseEntity<BrandReadDto.BrandReadListSelectResponse> readBrandListSelect(){
        return ResponseEntity.ok().body(brandReadService.readBrandSelectList());
    }

    @GetMapping("/detail")
    public ResponseEntity<BrandReadDto.BrandReadDetailResponse> readBrandDetail(
            @RequestParam("brandIdx") final Long brandIdx){
        return ResponseEntity.ok().body(brandReadService.readBrandDetail(brandIdx));
    }

    @PostMapping("/create")
    public ResponseEntity<BrandProcessDto.BrandProcessResponse> createBrand(
            @RequestBody final BrandProcessDto.BrandUpsertRequest request) {
        return ResponseEntity.ok().body(brandProcessService.insertBrand(request));
    }

    @PatchMapping("/update")
    public ResponseEntity<BrandProcessDto.BrandProcessResponse> updateBrand(
            @RequestBody final BrandProcessDto.BrandUpsertRequest request) {
        System.out.println(request);
        return ResponseEntity.ok().body(brandProcessService.updateBrand(request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BrandProcessDto.BrandProcessResponse> deleteBrand(
            @RequestParam("brandIdx") final Long brandIdx){
        return ResponseEntity.ok().body(brandProcessService.deleteBrand(brandIdx));
    }



}
