package com.tom.pasta.api

import com.tom.pasta.api.converter.fromDto
import com.tom.pasta.api.converter.toDto
import com.tom.pasta.model.ProductDto
import com.tom.pasta.product.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductResource(val productService: ProductService) : ProductsApi {
    override fun getProducts(): ResponseEntity<List<ProductDto>> {
        return ResponseEntity.ok(productService.getProducts().map { it.toDto() })
    }

    override fun getProduct(productId: Long): ResponseEntity<ProductDto> {
        val product = productService.find(productId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(product.toDto())
    }

    override fun createProduct(productDto: ProductDto): ResponseEntity<ProductDto> {
        return ResponseEntity.ok(productService.create(productDto.fromDto()).toDto())
    }

    override fun editProduct(productId: Long, productDto: ProductDto): ResponseEntity<ProductDto> {
        val product = productDto.fromDto().copy(id = productId)
        val updatedProduct = productService.update(product) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(updatedProduct.toDto())
    }

    override fun deleteProduct(productId: Long): ResponseEntity<Unit> {
        productService.delete(productId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}
