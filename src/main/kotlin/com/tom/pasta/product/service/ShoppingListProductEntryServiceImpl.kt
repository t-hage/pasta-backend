package com.tom.pasta.product.service

import com.tom.pasta.product.ShoppingListProductEntryService
import com.tom.pasta.product.model.ProductEntry
import com.tom.pasta.product.repository.ShoppingListProductEntryRepository
import org.springframework.stereotype.Service

@Service
class ShoppingListProductEntryServiceImpl(val shoppingListProductEntryRepository: ShoppingListProductEntryRepository) :
    ShoppingListProductEntryService {
    override fun getAll(shoppingListId: Long): List<ProductEntry> {
        return shoppingListProductEntryRepository.getAll(shoppingListId)
    }

    override fun create(shoppingListId: Long, productEntry: ProductEntry): ProductEntry {
        return shoppingListProductEntryRepository.create(shoppingListId, productEntry)
    }

    override fun update(shoppingListId: Long, productEntry: ProductEntry) {
        shoppingListProductEntryRepository.update(shoppingListId, productEntry)
    }

    override fun delete(shoppingListId: Long, productEntryId: Long) {
        shoppingListProductEntryRepository.delete(shoppingListId, productEntryId)
    }
}
