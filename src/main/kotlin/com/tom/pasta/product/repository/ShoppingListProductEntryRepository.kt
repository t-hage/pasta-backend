package com.tom.pasta.product.repository

import com.tom.pasta.product.model.ShoppingListProductEntry

interface ShoppingListProductEntryRepository {
    fun getAllByShoppingListId(id: Long): List<ShoppingListProductEntry>
}