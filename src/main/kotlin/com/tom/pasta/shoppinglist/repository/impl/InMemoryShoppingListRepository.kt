package com.tom.pasta.shoppinglist.repository.impl

import com.tom.pasta.meal.model.Meal
import com.tom.pasta.meal.repository.MealRepository
import com.tom.pasta.product.model.ProductEntry
import com.tom.pasta.product.repository.ShoppingListProductEntryRepository
import com.tom.pasta.shoppinglist.model.ShoppingList
import com.tom.pasta.shoppinglist.repository.ShoppingListRepository
import com.tom.pasta.shoppinglist.repository.entity.MealToShoppingListEntity
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException

@Repository
internal class InMemoryShoppingListRepository(
    val shoppingListProductEntryRepository: ShoppingListProductEntryRepository,
    val mealRepository: MealRepository
) : ShoppingListRepository {

    private val mealToShoppingList = mutableListOf(
        MealToShoppingListEntity(1, 1),
        MealToShoppingListEntity(2, 1),
    )

    private val allShoppingLists = listOf(
        asShoppingList(1, "List Number one")
    )

    override fun findById(id: Long): ShoppingList? {
        val shoppingList = allShoppingLists.firstOrNull { it.id == id } ?: return null
        val meals = getMeals(id)
        val productEntries = shoppingListProductEntryRepository.getAll(id)
        return shoppingList.copy(meals = meals, productEntries = productEntries)
    }

    override fun addMeal(shoppingListId: Long, mealId: Long) {
        mealRepository.findById(mealId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        findById(shoppingListId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        val existingMealToShoppingList =
            mealToShoppingList.firstOrNull { it.mealId == mealId && shoppingListId == shoppingListId }
        if (existingMealToShoppingList == null) {
            mealToShoppingList.add(MealToShoppingListEntity(mealId, shoppingListId))
        }
    }

    override fun deleteMeal(shoppingListId: Long, mealId: Long) {
        mealToShoppingList.removeIf { it.mealId == mealId && it.shoppingListId == shoppingListId }
    }

    override fun getMeals(shoppingListId: Long): List<Meal> {
        return mealToShoppingList.filter { it.shoppingListId == shoppingListId }
            .mapNotNull { mealRepository.findById(it.mealId) }
    }

    private fun getProductEntries(id: Long): List<ProductEntry> {
        return shoppingListProductEntryRepository.getAll(id)
    }

    private fun asShoppingList(
        id: Long,
        name: String,
    ): ShoppingList {
        return ShoppingList(id, name, emptyList(), emptyList())
    }
}
