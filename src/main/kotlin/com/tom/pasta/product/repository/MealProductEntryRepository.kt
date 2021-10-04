package com.tom.pasta.product.repository

import com.tom.pasta.product.model.MealProductEntry

interface MealProductEntryRepository {
    fun getAllByMealId(id: Long): List<MealProductEntry>
    fun upsertProductEntriesForMealId(mealId: Long, mealProductEntries: List<MealProductEntry>): List<MealProductEntry>
    fun deleteAllByMealId(mealId: Long)
//    fun addProductEntry(mealProductEntry: MealProductEntry): MealProductEntry
//    fun editProductEntry(mealProductEntry: MealProductEntry): MealProductEntry
//    fun deleteProductEntry(mealId: Long, productEntryId: Long)
}
