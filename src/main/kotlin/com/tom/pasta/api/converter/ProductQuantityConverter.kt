package com.tom.pasta.api.converter

import com.tom.pasta.model.ShoppingListItemDto
import com.tom.pasta.shoppinglist.model.ShoppingListItem

fun ShoppingListItem.toDto(): ShoppingListItemDto {
    return ShoppingListItemDto(
        product = this.product.toDto(),
        quantity = this.quantity.toDto(),
        checked = this.checked
    )
}