package com.tom.pasta.meal.repository

import com.tom.pasta.meal.model.Meal

interface MealRepository {
    fun findById(id: Long): Meal?
    fun getAllMeals(): List<Meal>
    fun create(meal: Meal): Meal
    fun update(meal: Meal): Meal?
    fun delete(id: Long)
}