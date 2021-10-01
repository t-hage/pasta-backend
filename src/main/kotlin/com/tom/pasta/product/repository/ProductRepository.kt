package com.tom.pasta.product.repository

import com.tom.pasta.product.model.Product

interface ProductRepository {
    fun getAll(): List<Product>
    fun findById(id: Long): Product?
}
