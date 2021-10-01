package com.tom.pasta.product.repository.impl

import com.tom.pasta.product.model.MealProductEntry
import com.tom.pasta.product.model.Quantity
import com.tom.pasta.product.model.QuantityType
import com.tom.pasta.product.repository.MealProductEntryRepository
import com.tom.pasta.product.repository.ProductRepository
import org.springframework.stereotype.Repository

@Repository
internal class InMemoryMealProductEntryRepository(val productRepository: ProductRepository) : MealProductEntryRepository {

    private val allProductsEntries = listOf(
        asEntry(1, 1, 1, 2, QuantityType.GRAM),
        asEntry(1, 2, 2, 8, QuantityType.ML),
        asEntry(2, 3, 3, 10, QuantityType.PIECES),
        asEntry(3, 4, 1, 1000, QuantityType.GRAM),
    )

    override fun getAllByMealId(id: Long): List<MealProductEntry> {
        return allProductsEntries.filter { it.mealId == id }
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
}
