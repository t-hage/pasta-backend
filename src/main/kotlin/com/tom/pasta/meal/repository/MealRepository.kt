package com.tom.pasta.meal.repository

import com.tom.pasta.meal.model.Meal

interface MealRepository {
    fun findMealById(id: Long): Meal?
    fun getAllMeals(): List<Meal>
}