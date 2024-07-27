package com.musinsa.m_backend.controller;


import com.musinsa.m_backend.dto.ProductProcessDto;
import com.musinsa.m_backend.dto.ProductReadDto;
import com.musinsa.m_backend.service.ProductProcessService;
import com.musinsa.m_backend.service.ProductReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductProcessService productProcessService;
    private final ProductReadService productReadService;

    @GetMapping("/list")
    public ResponseEntity<ProductReadDto.ProductReadListResponse> readProductList(){
        return ResponseEntity.ok().body(productReadService.readProductList());
    }

    @GetMapping("/detail")
    public ResponseEntity<ProductReadDto.ProductReadDetailResponse> readProductDetail(
            @RequestParam("productIdx") final Long productIdx){
        return ResponseEntity.ok().body(productReadService.readProductDetail(productIdx));
    }

    @PostMapping("/create")
    public ResponseEntity<ProductProcessDto.ProductProcessResponse> createProduct(@RequestBody final ProductProcessDto.ProductUpsertRequest request) {
        return ResponseEntity.ok().body(productProcessService.insertProduct(request));
    }

    @PatchMapping("/update")
    public ResponseEntity<ProductProcessDto.ProductProcessResponse> updateProduct(@RequestBody final ProductProcessDto.ProductUpsertRequest request) {
        return ResponseEntity.ok().body(productProcessService.updateProduct(request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ProductProcessDto.ProductProcessResponse> deleteProduct(@RequestParam("productIdx") final Long productIdx) {
        return ResponseEntity.ok().body(productProcessService.deleteProduct(productIdx));
    }


}
