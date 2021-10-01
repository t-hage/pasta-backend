package com.tom.pasta.product

import com.tom.pasta.product.model.ProductEntry
import com.tom.pasta.product.repository.MealProductEntryRepository
import com.tom.pasta.product.repository.ShoppingListProductEntryRepository
import org.springframework.stereotype.Service

@Service
class ShoppingListProductEntryService(val shoppingListProductEntryRepository: ShoppingListProductEntryRepository) {
    fun getAllByShoppingListId(id: Long): List<ProductEntry> {
        return shoppingListProductEntryRepository.getAllByShoppingListId(id)
    }
}