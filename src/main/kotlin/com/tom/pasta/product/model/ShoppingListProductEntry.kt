package com.tom.pasta.product.model

class ShoppingListProductEntry(
    val shoppingListId: Long,
    id: Long?,
    product: Product,
    quantity: Quantity
) : ProductEntry(id, product, quantity)
