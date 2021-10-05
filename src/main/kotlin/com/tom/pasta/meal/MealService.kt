package com.tom.pasta.meal

import com.tom.pasta.meal.model.Meal
import com.tom.pasta.product.model.ProductEntry

interface MealService {
    fun findById(id: Long): Meal?
    fun getAll(): List<Meal>
    fun create(meal: Meal): Meal
    fun update(meal: Meal)
    fun delete(id: Long)
}
