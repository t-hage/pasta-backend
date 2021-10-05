package com.tom.pasta.api

import com.tom.pasta.api.converter.fromDto
import com.tom.pasta.api.converter.toDto
import com.tom.pasta.meal.MealService
import com.tom.pasta.model.MealDto
import com.tom.pasta.model.ProductEntryDto
import com.tom.pasta.product.MealProductEntryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class MealResource(val mealService: MealService, val mealProductEntryService: MealProductEntryService) : MealsApi {
    override fun getMeals(): ResponseEntity<List<MealDto>> {
        return ResponseEntity.ok(mealService.getAll().map { it.toDto() })
    }

    override fun getMeal(mealId: Long): ResponseEntity<MealDto> {
        val meal = mealService.findById(mealId) ?: return ResponseEntity(HttpStatus.NO_CONTENT)
        return ResponseEntity.ok(meal.toDto())
    }

    override fun createMeal(mealDto: MealDto): ResponseEntity<MealDto> {
        return ResponseEntity.ok(mealService.create(mealDto.fromDto()).toDto())
    }

    override fun editMeal(mealId: Long, mealDto: MealDto): ResponseEntity<Unit> {
        val product = mealDto.copy(id = mealId).fromDto()
        mealService.update(product)
        return ResponseEntity(HttpStatus.OK)
    }

    override fun deleteMeal(mealId: Long): ResponseEntity<Unit> {
        mealService.delete(mealId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    override fun getMealEntries(mealId: Long): ResponseEntity<List<ProductEntryDto>> {
        val entries = mealProductEntryService.getAll(mealId).map { it.toDto() }
        return ResponseEntity.ok(entries)
    }

    override fun addMealEntry(mealId: Long, productEntryDto: ProductEntryDto): ResponseEntity<ProductEntryDto> {
        val createdProductEntry = mealProductEntryService.create(mealId, productEntryDto.fromDto()).toDto()
        return ResponseEntity.ok(createdProductEntry)
    }

    override fun updateMealEntry(mealId: Long, entryId: Long, productEntryDto: ProductEntryDto): ResponseEntity<Unit> {
        mealProductEntryService.update(mealId, productEntryDto.copy(id = entryId).fromDto())
        return ResponseEntity(HttpStatus.OK)
    }

    override fun deleteMealEntry(mealId: Long, entryId: Long): ResponseEntity<Unit> {
        mealProductEntryService.delete(mealId, entryId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}
