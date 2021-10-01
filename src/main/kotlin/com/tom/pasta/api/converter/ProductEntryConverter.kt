package com.tom.pasta.api.converter

import com.tom.pasta.model.ProductDto
import com.tom.pasta.model.ProductEntryDto
import com.tom.pasta.product.model.Product
import com.tom.pasta.product.model.ProductEntry

fun ProductEntry.toDto(): ProductEntryDto {
    return ProductEntryDto(
        id = this.id,
        product = this.product.toDto(),
        quantity = this.quantity.toDto()
    )
}