package com.tom.pasta.product

import com.tom.pasta.product.model.Product
import com.tom.pasta.product.model.ProductEntry

interface ShoppingListProductEntryService {
    fun getAll(shoppingListId: Long): List<ProductEntry>
    fun create(shoppingListId: Long, productEntry: ProductEntry): ProductEntry
    fun update(shoppingListId: Long, productEntry: ProductEntry)
    fun delete(shoppingListId: Long, productEntryId: Long)
}
