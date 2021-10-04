package com.tom.pasta.api.converter

import com.tom.pasta.model.ProductEntryDto
import com.tom.pasta.product.model.ProductEntry

fun ProductEntryDto.fromDto(): ProductEntry {
    return ProductEntry(
        id = this.id,
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