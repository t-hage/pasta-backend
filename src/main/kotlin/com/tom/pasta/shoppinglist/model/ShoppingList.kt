package com.tom.pasta.shoppinglist.model

import com.tom.pasta.meal.model.Meal
import com.tom.pasta.product.model.ProductEntry

data class ShoppingList(
    val id: Long?,
    val name: String,
    val productEntries: List<ProductEntry> = emptyList(),
    val meals: List<Meal> = emptyList()
)
