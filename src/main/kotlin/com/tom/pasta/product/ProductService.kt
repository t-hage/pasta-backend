package com.tom.pasta.product

import com.tom.pasta.product.model.Product

interface ProductService {
    fun getProducts(): List<Product>
}