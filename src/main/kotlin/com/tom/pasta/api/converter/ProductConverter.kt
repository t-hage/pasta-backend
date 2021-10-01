package com.tom.pasta.api.converter

import com.tom.pasta.model.ProductDto
import com.tom.pasta.product.model.Product

fun ProductDto.fromDto(): Product {
    return Product(
        id = this.id,
        name = this.name
    )
}

fun Product.toDto(): ProductDto {
    return ProductDto(
        id = this.id,
        name = this.name
    )
}