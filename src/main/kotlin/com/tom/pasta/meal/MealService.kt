package com.tom.pasta.meal

import com.tom.pasta.meal.model.Meal

interface MealService {
    fun findById(id: Long): Meal?
    fun getAll(): List<Meal>
}
