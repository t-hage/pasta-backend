package com.tom.pasta.product.service

import com.tom.pasta.product.ProductService
import com.tom.pasta.product.model.Product
import com.tom.pasta.product.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
private class ProductServiceImpl(val productRepository: ProductRepository) : ProductService {
    override fun getProducts(): List<Product> {
        return productRepository.getAll()
    }

    override fun find(id: Long): Product? {
        return productRepository.findById(id)
    }

    override fun create(product: Product): Product {
        return productRepository.create(product.copy(id = null))
    }

    override fun update(product: Product) {
        productRepository.update(product)
    }

    override fun delete(id: Long) {
        productRepository.delete(id)
    }
}
