package com.tom.pasta.api

import com.tom.pasta.api.converter.fromDto
import com.tom.pasta.api.converter.toDto
import com.tom.pasta.model.MealDto
import com.tom.pasta.model.ProductEntryDto
import com.tom.pasta.model.ProductQuantityDto
import com.tom.pasta.model.ShoppingListDto
import com.tom.pasta.product.ShoppingListProductEntryService
import com.tom.pasta.shoppinglist.ShoppingListService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ShoppingListResource(
    val shoppingListService: ShoppingListService,
    val shoppingListProductEntryService: ShoppingListProductEntryService
) : ShoppingListsApi {
    override fun getShoppingList(shoppingListId: Long): ResponseEntity<ShoppingListDto> {
        val shoppingListDto = shoppingListService.findById(shoppingListId)?.toDto()
        return if (shoppingListDto == null) ResponseEntity(HttpStatus.NOT_FOUND)
        else ResponseEntity.ok(shoppingListDto)
    }

    override fun getShoppingListMergedProductEntries(shoppingListId: Long): ResponseEntity<List<ProductQuantityDto>> {
        val productQuantities = shoppingListService.getShoppingListMergedProductEntries(shoppingListId)
            .map { it.toDto() }
        return ResponseEntity.ok(productQuantities)
    }

    override fun getShoppingListEntries(shoppingListId: Long): ResponseEntity<List<ProductEntryDto>> {
        val entries = shoppingListProductEntryService.getAll(shoppingListId).map { it.toDto() }
        return ResponseEntity.ok(entries)
    }

    override fun addShoppingListEntry(
        shoppingListId: Long,
        productEntryDto: ProductEntryDto
    ): ResponseEntity<ProductEntryDto> {
        val createdProductEntry =
            shoppingListProductEntryService.create(shoppingListId, productEntryDto.fromDto()).toDto()
        return ResponseEntity.ok(createdProductEntry)
    }

    override fun updateShoppingListEntry(
        shoppingListId: Long,
        entryId: Long,
        productEntryDto: ProductEntryDto
    ): ResponseEntity<Unit> {
        shoppingListProductEntryService.update(shoppingListId, productEntryDto.copy(id = entryId).fromDto())
        return ResponseEntity(HttpStatus.OK)
    }

    override fun deleteShoppingListEntry(shoppingListId: Long, entryId: Long): ResponseEntity<Unit> {
        shoppingListProductEntryService.delete(shoppingListId, entryId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    override fun getShoppingListMeals(shoppingListId: Long): ResponseEntity<List<MealDto>> {
        val meals = shoppingListService.getAllMeals(shoppingListId).map { it.toDto() }
        return ResponseEntity.ok(meals)
    }

    override fun addMealToShoppingList(shoppingListId: Long, mealId: Long): ResponseEntity<Unit> {
        shoppingListService.addMeal(shoppingListId, mealId)
        return ResponseEntity(HttpStatus.OK)
    }

    override fun deleteMealFromShoppingList(shoppingListId: Long, mealId: Long): ResponseEntity<Unit> {
        shoppingListService.deleteMeal(shoppingListId, mealId)
        return ResponseEntity(HttpStatus.OK)
    }
}
