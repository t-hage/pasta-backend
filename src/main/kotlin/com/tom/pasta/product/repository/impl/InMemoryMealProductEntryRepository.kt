package com.tom.pasta.product.repository.impl

import com.tom.pasta.product.model.MealProductEntry
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

    private val allProductsEntries = mutableListOf(
        asEntry(1, 1, 1, 2, QuantityType.GRAM),
        asEntry(1, 2, 2, 8, QuantityType.ML),
        asEntry(2, 3, 3, 10, QuantityType.PIECES),
        asEntry(3, 4, 1, 1000, QuantityType.GRAM),
    )

    override fun getAllByMealId(id: Long): List<MealProductEntry> {
        return allProductsEntries.filter { it.mealId == id }
    }

    override fun upsertProductEntriesForMealId(
        mealId: Long,
        mealProductEntries: List<MealProductEntry>
    ): List<MealProductEntry> {
        deleteAllByMealId(mealId)
        for (mealProductEntry in mealProductEntries) {
            if (mealProductEntry.id == null) continue
            addProductEntry(mealProductEntry)
        }

        return getAllByMealId(mealId)
    }

    override fun deleteAllByMealId(mealId: Long) {
        allProductsEntries.removeIf { it.mealId == mealId }
    }

    fun find(mealId: Long, productEntryId: Long): MealProductEntry? {
        return allProductsEntries.firstOrNull {
            it.mealId == mealId && it.id == productEntryId
        }
    }

    private fun addProductEntry(mealProductEntry: MealProductEntry): MealProductEntry {
        val actualMealProductEntry = validatedMPE(mealProductEntry)

        allProductsEntries.add(actualMealProductEntry)
        return actualMealProductEntry
    }

    private fun validatedMPE(mealProductEntry: MealProductEntry): MealProductEntry {
        val productId = mealProductEntry.product.id
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing Product Id")
        val actualProduct = productRepository.findById(productId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Product does not exist")

        return mealProductEntry.copy(id = getNewId(), product = actualProduct)
    }

    private fun asEntry(
        mealId: Long,
        id: Long,
        productId: Long,
        amount: Int,
        quantityType: QuantityType
    ): MealProductEntry {
        val product = productRepository.findById(productId) ?: throw Exception("Product not found!")
        val quantity = Quantity(amount, quantityType)
        return MealProductEntry(mealId, id, product, quantity)
    }

    private fun getNewId(): Long {
        val currentHighestId = allProductsEntries.map { it.id }.maxByOrNull { it!! } ?: 0L
        return currentHighestId + 1
    }
}
