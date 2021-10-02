package com.tom.pasta.shoppinglist

import com.tom.pasta.shoppinglist.model.ProductQuantity
import com.tom.pasta.shoppinglist.model.ShoppingList

interface ShoppingListService {
    fun findById(id: Long): ShoppingList?
    fun getShoppingListMergedProductEntries(id: Long): List<ProductQuantity>
}
