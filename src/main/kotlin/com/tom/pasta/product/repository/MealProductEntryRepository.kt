package com.tom.pasta.product.repository

import com.tom.pasta.product.model.MealProductEntry

interface MealProductEntryRepository {
    fun getAllByMealId(id: Long) : List<MealProductEntry>
}