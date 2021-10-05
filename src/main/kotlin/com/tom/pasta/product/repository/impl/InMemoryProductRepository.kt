package com.tom.pasta.product.repository.impl

import com.tom.pasta.product.model.Product
import com.tom.pasta.product.repository.ProductRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException

@Repository
internal class InMemoryProductRepository : ProductRepository {

    private val allProducts = mutableListOf(
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

    override fun create(product: Product): Product {
        val newProduct = product.copy(id = getNewId())
        allProducts.add(newProduct)
        return newProduct
    }

    override fun update(product: Product) {
        findById(product.id!!) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        delete(product.id)
        allProducts.add(product)
    }

    override fun delete(id: Long) {
        val toRemove = findById(id) ?: return
        allProducts.remove(toRemove)
    }

    private fun getNewId(): Long {
        val currentHighestId = allProducts.map { it.id }.maxByOrNull { it!! } ?: 0L
        return currentHighestId + 1
    }
}
