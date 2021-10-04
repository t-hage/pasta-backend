package com.tom.pasta.product.model

data class MealProductEntry(
    val mealId: Long,
    override val id: Long?,
    override val product: Product,
    override val quantity: Quantity
) : ProductEntry(id, product, quantity)
