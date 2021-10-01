package com.tom.pasta.api.converter

import com.tom.pasta.meal.model.Meal
import com.tom.pasta.model.MealDto
import com.tom.pasta.model.ProductDto
import com.tom.pasta.product.model.Product

fun Meal.toDto(): MealDto {
    return MealDto(
        id = this.id,
        name = this.name,
        productEntries = this.productEntries.map { it.toDto() }
    )
}