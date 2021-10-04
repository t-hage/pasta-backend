package com.tom.pasta.api.converter

import com.tom.pasta.model.QuantityDto
import com.tom.pasta.product.model.Quantity
import com.tom.pasta.product.model.QuantityType

fun QuantityDto.fromDto(): Quantity {
    return Quantity(
        amount = this.amount.toInt(),
        quantityType = QuantityType.valueOf(this.quantityType)
    )
}

fun Quantity.toDto(): QuantityDto {
    return QuantityDto(
        amount = this.amount.toLong(),
        quantityType = this.quantityType.name
    )
}
