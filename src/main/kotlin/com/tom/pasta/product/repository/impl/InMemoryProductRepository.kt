package com.tom.pasta.product.repository.impl

import com.tom.pasta.product.model.Product
import com.tom.pasta.product.repository.ProductRepository
import org.springframework.stereotype.Repository

@Repository
internal class InMemoryProductRepository : ProductRepository {

    private val allProducts = listOf(
        asProduct(1, "chicken"),
        asProduct(2, "tomato"),
        asProduct(3, "cucumber"),
    )

    private fun asProduct(id: Long, name: String) = Product(id, name)

    override fun getAll(): List<Product> {
        return allProducts
    }

    override fun findById(id: Long): Product? {
        return allProducts.firstOrNull { it.id == id }
    }
}
