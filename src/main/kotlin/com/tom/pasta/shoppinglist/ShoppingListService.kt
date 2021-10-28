package com.tom.pasta.shoppinglist

import com.tom.pasta.meal.model.Meal
import com.tom.pasta.shoppinglist.model.ShoppingList
import com.tom.pasta.shoppinglist.model.ShoppingListCheck
import com.tom.pasta.shoppinglist.model.ShoppingListItem

interface ShoppingListService {
    fun findById(id: Long): ShoppingList?
    fun getShoppingListItems(id: Long): List<ShoppingListItem>
    fun checkShoppingListItem(shoppingListCheck: ShoppingListCheck)
    fun getAllMeals(shoppingListId: Long): List<Meal>
    fun addMeal(shoppingListId: Long, mealId: Long)
    fun deleteMeal(shoppingListId: Long, mealId: Long)
}
