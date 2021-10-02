package com.tom.pasta.api.converter

import com.tom.pasta.model.ProductQuantityDto
import com.tom.pasta.shoppinglist.model.ProductQuantity

fun ProductQuantity.toDto(): ProductQuantityDto {
    return ProductQuantityDto(
        product = this.product.toDto(),
        quantity = this.quantity.toDto()
    )
}