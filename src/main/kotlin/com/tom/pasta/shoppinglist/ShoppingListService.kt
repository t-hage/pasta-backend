package com.tom.pasta.shoppinglist

import com.tom.pasta.meal.model.Meal
import com.tom.pasta.shoppinglist.model.ProductQuantity
import com.tom.pasta.shoppinglist.model.ShoppingList

interface ShoppingListService {
    fun findById(id: Long): ShoppingList?
    fun getShoppingListMergedProductEntries(id: Long): List<ProductQuantity>
    fun getAllMeals(shoppingListId: Long): List<Meal>
    fun addMeal(shoppingListId: Long, mealId: Long)
    fun deleteMeal(shoppingListId: Long, mealId: Long)
}
