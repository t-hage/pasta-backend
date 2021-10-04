package com.tom.pasta.api

import com.tom.pasta.api.converter.fromDto
import com.tom.pasta.api.converter.toDto
import com.tom.pasta.meal.MealService
import com.tom.pasta.model.MealDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class MealResource(val mealService: MealService) : MealsApi {
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

    override fun editMeal(mealId: Long, mealDto: MealDto): ResponseEntity<MealDto> {
        val product = mealDto.copy(id = mealId).fromDto()
        val updatedMeal = mealService.update(product) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(updatedMeal.toDto())
    }

    override fun deleteMeal(mealId: Long): ResponseEntity<Unit> {
        mealService.delete(mealId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}
