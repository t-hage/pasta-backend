package com.tom.pasta.api.converter

import com.tom.pasta.model.ShoppingListDto
import com.tom.pasta.shoppinglist.model.ShoppingList

fun ShoppingListDto.fromDto(): ShoppingList {
    return ShoppingList(
        id = this.id,
        name = this.name
    )
}

fun ShoppingList.toDto(): ShoppingListDto {
    return ShoppingListDto(
        id = this.id,
        name = this.name
    )
}
