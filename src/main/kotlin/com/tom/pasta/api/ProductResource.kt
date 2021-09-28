package com.tom.pasta.api

import com.tom.pasta.converter.toDto
import com.tom.pasta.model.ProductDto
import com.tom.pasta.product.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductResource(val productService: ProductService) : ProductsApi {
    override fun listProducts(): ResponseEntity<List<ProductDto>> {
        return ResponseEntity.ok(productService.getProducts().map { it.toDto() })
    }
}