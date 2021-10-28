package com.tom.pasta.shoppinglist.service

import com.tom.pasta.meal.model.Meal
import com.tom.pasta.product.model.ProductEntry
import com.tom.pasta.product.model.Quantity
import com.tom.pasta.product.model.QuantityType
import com.tom.pasta.shoppinglist.ShoppingListService
import com.tom.pasta.shoppinglist.model.ShoppingList
import com.tom.pasta.shoppinglist.model.ShoppingListCheck
import com.tom.pasta.shoppinglist.model.ShoppingListItem
import com.tom.pasta.shoppinglist.repository.ShoppingListCheckRepository
import com.tom.pasta.shoppinglist.repository.ShoppingListRepository
import org.springframework.stereotype.Service

@Service
class ShoppingListServiceImpl(
    val shoppingListRepository: ShoppingListRepository,
    val shoppingListCheckRepository: ShoppingListCheckRepository
) : ShoppingListService {
    override fun findById(id: Long): ShoppingList? {
        return shoppingListRepository.findById(id)
    }

    override fun getShoppingListItems(id: Long): List<ShoppingListItem> {
        val shoppingList = findById(id) ?: return emptyList()

        val shoppingListProductEntries = shoppingList.productEntries.toMutableList()
        shoppingListProductEntries.addAll(shoppingList.meals.flatMap { it.productEntries })

        val allProductEntries = shoppingListProductEntries.toList()

        val groupedByProducts = allProductEntries.groupBy { it.product.id }

        val shoppingListItems: List<ShoppingListItem> = groupedByProducts.flatMap { mergeProductEntries(it.value) }
        val shoppingListChecks = shoppingListCheckRepository.getAllShoppingListChecks(id)
        return mergeItemsWithChecks(shoppingListItems, shoppingListChecks)
    }

    override fun checkShoppingListItem(shoppingListCheck: ShoppingListCheck) {
        shoppingListCheckRepository.updateCheck(shoppingListCheck)
    }

    override fun getAllMeals(shoppingListId: Long): List<Meal> {
        return shoppingListRepository.getMeals(shoppingListId)
    }

    override fun addMeal(shoppingListId: Long, mealId: Long) {
        shoppingListRepository.addMeal(shoppingListId, mealId)
    }

    override fun deleteMeal(shoppingListId: Long, mealId: Long) {
        shoppingListRepository.deleteMeal(shoppingListId, mealId)
    }

    private fun mergeProductEntries(productEntries: List<ProductEntry>): List<ShoppingListItem> {
        if (productEntries.isEmpty()) return emptyList()
        if (!allEqualsBy(productEntries) { it.product.id }) throw Exception("Cannot merge for different products")

        val groupedByQuantityType: Map<QuantityType, List<ProductEntry>> =
            productEntries.groupBy { it.quantity.quantityType }

        return groupedByQuantityType.map { mergeProductEntriesForQuantityType(it.value) }
    }

    private fun mergeProductEntriesForQuantityType(productEntries: List<ProductEntry>): ShoppingListItem {
        if (productEntries.isEmpty()) throw Exception("Cannot merge for empty list")
        if (!allEqualsBy(productEntries) { it.quantity.quantityType })
            throw Exception("Cannot merge for different quantity types")

        val combinedAmounts = productEntries.map { it.quantity.amount }.sum()

        return ShoppingListItem(
            productEntries.first().product,
            Quantity(combinedAmounts, productEntries.first().quantity.quantityType)
        )
    }

    private fun mergeItemsWithChecks(
        shoppingListItems: List<ShoppingListItem>,
        shoppingListChecks: List<ShoppingListCheck>
    ): List<ShoppingListItem> {
        return shoppingListItems.map { mergeItemWithCheck(it, shoppingListChecks) }
    }

    private fun mergeItemWithCheck(
        shoppingListItem: ShoppingListItem,
        shoppingListChecks: List<ShoppingListCheck>
    ): ShoppingListItem {
        val checked = shoppingListChecks.find { it.productId == shoppingListItem.product.id }?.checked ?: false
        return ShoppingListItem(shoppingListItem.product, shoppingListItem.quantity, checked)
    }

    private fun <T, S> allEqualsBy(
        list: List<S>,
        valueProducer: (item: S) -> T
    ): Boolean {
        if (list.isEmpty()) return true

        val itemValue = valueProducer(list.first())
        return list.filter { valueProducer(it) == itemValue }.size == list.size
    }
}
