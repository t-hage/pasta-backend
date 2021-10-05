package com.tom.pasta.product

import com.tom.pasta.product.model.ProductEntry

interface MealProductEntryService {
    fun getAll(mealId: Long): List<ProductEntry>
    fun create(mealId: Long, productEntry: ProductEntry): ProductEntry
    fun update(mealId: Long, productEntry: ProductEntry)
    fun delete(mealId: Long, productEntryId: Long)
}
