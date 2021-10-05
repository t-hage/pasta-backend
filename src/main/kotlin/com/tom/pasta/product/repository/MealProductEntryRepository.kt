package com.tom.pasta.product.repository

import com.tom.pasta.product.model.ProductEntry

interface MealProductEntryRepository {
    fun getAll(id: Long): List<ProductEntry>
    fun deleteAll(mealId: Long)
    fun create(mealId: Long, productEntry: ProductEntry): ProductEntry
    fun update(mealId: Long, productEntry: ProductEntry)
    fun deleteForMeal(mealId: Long, productEntryId: Long)

}
