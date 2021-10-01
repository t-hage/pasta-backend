package com.tom.pasta.product.model

class MealProductEntry(
    val mealId: Long,
    id: Long?,
    product: Product,
    quantity: Quantity
) : ProductEntry(id, product, quantity)
