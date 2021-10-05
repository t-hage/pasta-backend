package com.tom.pasta.product.repository.impl

import com.tom.pasta.product.model.ProductEntry
import com.tom.pasta.product.model.Quantity
import com.tom.pasta.product.model.QuantityType
import com.tom.pasta.product.repository.MealProductEntryRepository
import com.tom.pasta.product.repository.ProductRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException

@Repository
internal class InMemoryMealProductEntryRepository(val productRepository: ProductRepository) :
    MealProductEntryRepository {

    private val allProductsEntries = mutableMapOf(
        Pair(
            1L,
            mutableListOf(
                asEntry(1, 1, 2, QuantityType.GRAM),
                asEntry(2, 2, 8, QuantityType.ML)
            )
        ),
        Pair(2L, mutableListOf(asEntry(3, 3, 10, QuantityType.PIECES)))
    )

    override fun getAll(id: Long): List<ProductEntry> {
        return allProductsEntries[id]?.toList() ?: emptyList()
    }

    override fun deleteAll(mealId: Long) {
        allProductsEntries[mealId]?.removeIf { true }
    }

    override fun create(mealId: Long, productEntry: ProductEntry): ProductEntry {
        val actualMealProductEntry = validatedMPE(productEntry)
        allProductsEntries.getOrPut(mealId){
            mutableListOf()
        }.add(actualMealProductEntry)
        return actualMealProductEntry
    }

    override fun update(mealId: Long, productEntry: ProductEntry) {
        val list = allProductsEntries[mealId] ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        list.firstOrNull { it.id == productEntry.id } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        list.removeIf { it.id == productEntry.id }
        list.add(productEntry)
    }

    override fun deleteForMeal(mealId: Long, productEntryId: Long) {
        allProductsEntries[mealId]?.removeIf { it.id == productEntryId }
    }

    private fun validatedMPE(mealProductEntry: ProductEntry): ProductEntry {
        val productId = mealProductEntry.product.id
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing Product Id")
        val actualProduct = productRepository.findById(productId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Product does not exist")

        return mealProductEntry.copy(id = getNewId(), product = actualProduct)
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
}
