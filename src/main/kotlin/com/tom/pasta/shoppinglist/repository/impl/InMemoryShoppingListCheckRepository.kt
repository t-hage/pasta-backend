package com.tom.pasta.shoppinglist.repository.impl

import com.tom.pasta.shoppinglist.model.ShoppingListCheck
import com.tom.pasta.shoppinglist.repository.ShoppingListCheckRepository
import org.springframework.stereotype.Repository

@Repository
internal class InMemoryShoppingListCheckRepository : ShoppingListCheckRepository {

    private val shoppingListCheckList = mutableListOf<ShoppingListCheck>()

    override fun getAllShoppingListChecks(shoppingListId: Long): List<ShoppingListCheck> {
        return shoppingListCheckList
    }

    override fun updateCheck(shoppingListCheck: ShoppingListCheck) {
        shoppingListCheckList.removeIf {
            it.shoppingListId == shoppingListCheck.shoppingListId && it.productId == shoppingListCheck.productId
        }
        shoppingListCheckList.add(shoppingListCheck)
    }
}
