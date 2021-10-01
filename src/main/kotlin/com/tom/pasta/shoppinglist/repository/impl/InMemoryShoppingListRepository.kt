package com.tom.pasta.shoppinglist.repository.impl

import com.tom.pasta.meal.repository.MealRepository
import com.tom.pasta.product.repository.ShoppingListProductEntryRepository
import com.tom.pasta.shoppinglist.model.ShoppingList
import com.tom.pasta.shoppinglist.repository.ShoppingListRepository
import com.tom.pasta.shoppinglist.repository.entity.MealToShoppingListEntity
import org.springframework.stereotype.Repository

@Repository
internal class InMemoryShoppingListRepository(
    val shoppingListProductEntryRepository: ShoppingListProductEntryRepository,
    val mealRepository: MealRepository
) : ShoppingListRepository {

    private val mealToShoppingList = listOf(
        MealToShoppingListEntity(1, 1),
        MealToShoppingListEntity(1, 2),
        MealToShoppingListEntity(2, 2),
    )

    private val allShoppingLists = listOf(
        asShoppingList(1, "List Number one"),
        asShoppingList(2, "List Number twooo"),
    )

    override fun findById(id: Long): ShoppingList? {
        return allShoppingLists.firstOrNull { it.id == id }
    }

    override fun getAll(): List<ShoppingList> {
        return allShoppingLists
    }

    private fun asShoppingList(
        id: Long,
        name: String,
    ): ShoppingList {
        val productEntries = shoppingListProductEntryRepository.getAllByShoppingListId(id)
        val meals = mealToShoppingList.filter { it.shoppingListId == id }
            .mapNotNull { mealRepository.findMealById(it.mealId) }
        return ShoppingList(id, name, productEntries, meals)
    }
}
