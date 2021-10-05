package com.tom.pasta.meal.model

import com.tom.pasta.product.model.ProductEntry

data class Meal(val id: Long?, val name: String, val productEntries: List<ProductEntry> = emptyList())