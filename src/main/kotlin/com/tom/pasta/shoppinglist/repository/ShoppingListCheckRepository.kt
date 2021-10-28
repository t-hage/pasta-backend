package com.tom.pasta.shoppinglist.repository

import com.tom.pasta.shoppinglist.model.ShoppingListCheck

interface ShoppingListCheckRepository {
    fun getAllShoppingListChecks(shoppingListId: Long): List<ShoppingListCheck>
    fun updateCheck(shoppingListCheck: ShoppingListCheck)
}
