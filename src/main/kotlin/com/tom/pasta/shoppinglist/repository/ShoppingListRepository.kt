package com.tom.pasta.shoppinglist.repository

import com.tom.pasta.meal.model.Meal
import com.tom.pasta.shoppinglist.model.ShoppingList

interface ShoppingListRepository {
    fun findById(id: Long): ShoppingList?
    fun getMeals(shoppingListId: Long): List<Meal>
    fun addMeal(shoppingListId: Long, mealId: Long)
    fun deleteMeal(shoppingListId: Long, mealId: Long)
}
