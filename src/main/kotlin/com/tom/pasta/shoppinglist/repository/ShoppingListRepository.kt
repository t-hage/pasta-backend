package com.tom.pasta.shoppinglist.repository

import com.tom.pasta.meal.model.Meal
import com.tom.pasta.shoppinglist.model.ShoppingList

interface ShoppingListRepository {
    fun findById(id: Long): ShoppingList?
    fun getAll(): List<ShoppingList>
}
