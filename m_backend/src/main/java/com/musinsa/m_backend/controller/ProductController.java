package com.musinsa.m_backend.controller;


import com.musinsa.m_backend.dto.ProductProcessDto;
import com.musinsa.m_backend.service.ProductProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductProcessService productProcessService;

    @PostMapping("/create")
    public ResponseEntity<ProductProcessDto.ProductProcessResponse> createProduct(@RequestBody final ProductProcessDto.ProductUpsertRequest request) {
        return ResponseEntity.ok().body(productProcessService.insertProduct(request));
    }

    @PatchMapping("/update")
    public ResponseEntity<ProductProcessDto.ProductProcessResponse> updateProduct(@RequestBody final ProductProcessDto.ProductUpsertRequest request) {
        return ResponseEntity.ok().body(productProcessService.updateProduct(request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ProductProcessDto.ProductProcessResponse> deleteProduct(@RequestBody final ProductProcessDto.ProductDeleteRequest request) {
        return ResponseEntity.ok().body(productProcessService.deleteProduct(request));
    }


}
