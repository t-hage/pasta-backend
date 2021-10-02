package com.tom.pasta.shoppinglist.model

import com.tom.pasta.product.model.Product
import com.tom.pasta.product.model.Quantity

data class ProductQuantity(val product: Product, val quantity: Quantity)
