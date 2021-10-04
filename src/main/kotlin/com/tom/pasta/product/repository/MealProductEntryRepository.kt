package com.tom.pasta.product.repository

import com.tom.pasta.product.model.ProductEntry

interface MealProductEntryRepository {
    fun getAllByMealId(id: Long): List<ProductEntry>
    fun upsertProductEntriesForMealId(mealId: Long, mealProductEntries: List<ProductEntry>): List<ProductEntry>
    fun deleteAllByMealId(mealId: Long)
}
