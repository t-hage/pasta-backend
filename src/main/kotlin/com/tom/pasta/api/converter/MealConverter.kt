package com.tom.pasta.api.converter

import com.tom.pasta.meal.model.Meal
import com.tom.pasta.model.MealDto

fun MealDto.fromDto(): Meal {
    return Meal(
        id = this.id,
        name = this.name,
        productEntries =
        if (this.id == null)
            emptyList()
        else
            this.productEntries.map { it.fromDto() }
    )
}

fun Meal.toDto(): MealDto {
    return MealDto(
        id = this.id,
        name = this.name,
        productEntries = this.productEntries.map { it.toDto() }
    )
}
