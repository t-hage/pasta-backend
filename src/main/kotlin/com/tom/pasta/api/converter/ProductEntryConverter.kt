package com.tom.pasta.api.converter

import com.tom.pasta.model.ProductDto
import com.tom.pasta.model.ProductEntryDto
import com.tom.pasta.product.model.MealProductEntry
import com.tom.pasta.product.model.Product
import com.tom.pasta.product.model.ProductEntry
import com.tom.pasta.product.model.ShoppingListProductEntry

fun ProductEntryDto.mealProductEntryFromDto(mealId: Long): MealProductEntry {
    return MealProductEntry(
        id = this.id,
        mealId = mealId,
        product = this.product.fromDto(),
        quantity = this.quantity.fromDto()
    )
}

fun ProductEntryDto.shoppingListProductEntryFromDto(shoppingListId: Long): ShoppingListProductEntry {
    return ShoppingListProductEntry(
        id = this.id,
        shoppingListId = shoppingListId,
        product = this.product.fromDto(),
        quantity = this.quantity.fromDto()
    )
}

fun ProductEntry.toDto(): ProductEntryDto {
    return ProductEntryDto(
        id = this.id,
        product = this.product.toDto(),
        quantity = this.quantity.toDto()
    )
}