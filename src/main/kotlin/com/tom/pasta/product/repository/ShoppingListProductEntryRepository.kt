package com.tom.pasta.product.repository

import com.tom.pasta.product.model.ProductEntry

interface ShoppingListProductEntryRepository {
    fun getAll(id: Long): List<ProductEntry>
    fun deleteAll(mealId: Long)
    fun create(mealId: Long, productEntry: ProductEntry): ProductEntry
    fun update(mealId: Long, productEntry: ProductEntry)
    fun delete(mealId: Long, productEntryId: Long)
}