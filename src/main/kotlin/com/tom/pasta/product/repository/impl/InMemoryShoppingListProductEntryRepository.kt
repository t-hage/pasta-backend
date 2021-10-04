package com.tom.pasta.product.repository.impl

import com.tom.pasta.product.model.ProductEntry
import com.tom.pasta.product.model.Quantity
import com.tom.pasta.product.model.QuantityType
import com.tom.pasta.product.repository.ProductRepository
import com.tom.pasta.product.repository.ShoppingListProductEntryRepository
import org.springframework.stereotype.Repository

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

    override fun getAllByShoppingListId(id: Long): List<ProductEntry> {
        return allProductsEntries[id]?.toList() ?: emptyList()
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
}
