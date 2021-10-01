package com.tom.pasta.shoppinglist

import com.tom.pasta.meal.model.Meal
import com.tom.pasta.shoppinglist.model.ShoppingList

interface ShoppingListService {
    fun findById(id: Long): ShoppingList?
    fun getAll(): List<ShoppingList>
}
