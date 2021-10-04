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
        Pair(2L, mutableListOf(asEntry(3, 3, 10, QuantityType.PIECES))),
        Pair(3L, mutableListOf(asEntry(4, 1, 1000, QuantityType.GRAM)))
    )

    override fun getAllByMealId(id: Long): List<ProductEntry> {
        return allProductsEntries[id]?.toList() ?: emptyList()
    }

    override fun upsertProductEntriesForMealId(
        mealId: Long,
        mealProductEntries: List<ProductEntry>
    ): List<ProductEntry> {
        deleteAllByMealId(mealId)
        for (mealProductEntry in mealProductEntries) {
            if (mealProductEntry.id == null) continue
            addProductEntry(mealId, mealProductEntry)
        }

        return getAllByMealId(mealId)
    }

    override fun deleteAllByMealId(mealId: Long) {
        allProductsEntries[mealId]?.removeIf { true }
    }

    fun find(mealId: Long, productEntryId: Long): ProductEntry? {
        return allProductsEntries[mealId]?.firstOrNull {
            it.id == productEntryId
        }
    }

    private fun addProductEntry(mealId: Long, mealProductEntry: ProductEntry): ProductEntry {
        val actualMealProductEntry = validatedMPE(mealProductEntry)

        allProductsEntries.getOrDefault(mealId, mutableListOf()).add(actualMealProductEntry)
        return actualMealProductEntry
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
