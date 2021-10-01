package com.tom.pasta.api.converter

import com.tom.pasta.model.QuantityDto
import com.tom.pasta.product.model.Quantity

fun Quantity.toDto(): QuantityDto {
    return QuantityDto(
        amount = this.amount.toLong(),
        quantityType = this.quantityType.name
    )
}