package com.tom.pasta.product.repository

import com.tom.pasta.product.model.ProductEntry

interface ShoppingListProductEntryRepository {
    fun getAllByShoppingListId(id: Long): List<ProductEntry>
}