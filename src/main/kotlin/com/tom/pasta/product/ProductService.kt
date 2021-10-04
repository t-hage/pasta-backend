package com.tom.pasta.product

import com.tom.pasta.product.model.Product

interface ProductService {
    fun getProducts(): List<Product>
    fun find(id: Long): Product?
    fun create(product: Product): Product
    fun update(product: Product): Product?
    fun delete(id: Long)
}
