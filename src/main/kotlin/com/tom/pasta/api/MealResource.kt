package com.tom.pasta.api

import com.tom.pasta.api.converter.toDto
import com.tom.pasta.meal.MealService
import com.tom.pasta.model.MealDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class MealResource(val mealService: MealService) : MealsApi {
    override fun getMeals(): ResponseEntity<List<MealDto>> {
        return ResponseEntity.ok(mealService.getAll().map { it.toDto() })
    }
}
