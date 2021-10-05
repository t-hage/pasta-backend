package com.tom.pasta.product.repository.impl

import com.tom.pasta.product.model.ProductEntry
import com.tom.pasta.product.model.Quantity
import com.tom.pasta.product.model.QuantityType
import com.tom.pasta.product.repository.ProductRepository
import com.tom.pasta.product.repository.ShoppingListProductEntryRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException

@Repository
internal class InMemoryShoppingListProductEntryRepository(val productRepository: ProductRepository) :
    ShoppingListProductEntryRepository {

    private val allProductsEntries = mutableMapOf(
        Pair(
            1L,
            mutableListOf(
                asEntry(1, 1, 20, QuantityType.GRAM),
                asEntry(2, 2, 800, QuantityType.ML),
                asEntry(3, 3, 1, QuantityType.PIECES)
            )
        ),

        Pair(
            2L, mutableListOf(asEntry(4, 2, 1000, QuantityType.ML))
        )
    )

    override fun getAll(id: Long): List<ProductEntry> {
        return allProductsEntries[id]?.toList() ?: emptyList()
    }

    override fun deleteAll(shoppingListId: Long) {
        allProductsEntries[shoppingListId]?.removeIf { true }
    }

    override fun create(shoppingListId: Long, productEntry: ProductEntry): ProductEntry {
        val actualProductEntry = validatedMPE(productEntry)
        allProductsEntries.getOrDefault(shoppingListId, mutableListOf()).add(actualProductEntry)
        return actualProductEntry
    }

    override fun update(shoppingListId: Long, productEntry: ProductEntry) {
        val list = allProductsEntries[shoppingListId] ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        list.firstOrNull { it.id == productEntry.id } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        list.removeIf { it.id == productEntry.id }
        list.add(productEntry)
    }

    override fun delete(shoppingListId: Long, productEntryId: Long) {
        allProductsEntries[shoppingListId]?.removeIf { it.id == productEntryId }
    }

    private fun validatedMPE(productEntry: ProductEntry): ProductEntry {
        val productId = productEntry.product.id
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing Product Id")
        val actualProduct = productRepository.findById(productId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Product does not exist")

        return productEntry.copy(id = getNewId(), product = actualProduct)
    }

    private fun asEntry(
        id: Long,
        productId: Long,
        amount: Int,
        quantityType: QuantityType
    ): ProductEntry {
        val product = productRepository.findById(productId) ?: throw Exception("Product not found!")
        val quantity = Quantity(amount, quantityType)
        return ProductEntry(id, product, quantity)
    }

    private fun getNewId(): Long {
        val currentHighestId = allProductsEntries.flatMap { it.value }.map { it.id }.maxByOrNull { it!! } ?: 0L
        return currentHighestId + 1
    }

//    override fun getAllByShoppingListId(id: Long): List<ProductEntry> {
//        return allProductsEntries[id]?.toList() ?: emptyList()
//    }
//
//    private fun asEntry(
//        id: Long,
//        productId: Long,
//        amount: Int,
//        quantityType: QuantityType
//    ): ProductEntry {
//        val product = productRepository.findById(productId) ?: throw Exception("Product not found!")
//        val quantity = Quantity(amount, quantityType)
//        return ProductEntry(id, product, quantity)
//    }
}
