package com.tom.pasta.product.repository.impl

import com.tom.pasta.product.model.Quantity
import com.tom.pasta.product.model.QuantityType
import com.tom.pasta.product.model.ShoppingListProductEntry
import com.tom.pasta.product.repository.ProductRepository
import com.tom.pasta.product.repository.ShoppingListProductEntryRepository
import org.springframework.stereotype.Repository

@Repository
internal class InMemoryShoppingListProductEntryRepository(val productRepository: ProductRepository) :
    ShoppingListProductEntryRepository {

    private val allProductsEntries = listOf(
        asEntry(1, 1, 1, 20, QuantityType.GRAM),
        asEntry(1, 2, 2, 800, QuantityType.ML),
        asEntry(1, 3, 3, 1, QuantityType.PIECES),
        asEntry(2, 4, 2, 1000, QuantityType.ML),
    )

    override fun getAllByShoppingListId(id: Long): List<ShoppingListProductEntry> {
        return allProductsEntries.filter { it.shoppingListId == id }
    }

    private fun asEntry(
        shoppingListId: Long,
        id: Long,
        productId: Long,
        amount: Int,
        quantityType: QuantityType
    ): ShoppingListProductEntry {
        val product = productRepository.findById(productId) ?: throw Exception("Product not found!")
        val quantity = Quantity(amount, quantityType)
        return ShoppingListProductEntry(shoppingListId, id, product, quantity)
    }
}
