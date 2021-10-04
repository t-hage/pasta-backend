package com.tom.pasta.api.converter

import com.tom.pasta.model.ShoppingListDto
import com.tom.pasta.shoppinglist.model.ShoppingList

fun ShoppingListDto.fromDto(): ShoppingList {
    return ShoppingList(
        id = this.id,
        name = this.name,
        meals = this.meals?.map { it.fromDto() } ?: emptyList(),
        productEntries =
        if (this.id == null)
            emptyList()
        else
            this.productEntries.map { it.fromDto() }
    )
}

fun ShoppingList.toDto(): ShoppingListDto {
    return ShoppingListDto(
        id = this.id,
        name = this.name,
        meals = this.meals.map { it.toDto() },
        productEntries = this.productEntries.map { it.toDto() }
    )
}
