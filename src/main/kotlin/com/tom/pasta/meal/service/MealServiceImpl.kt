package com.tom.pasta.meal.service

import com.tom.pasta.meal.MealService
import com.tom.pasta.meal.model.Meal
import com.tom.pasta.meal.repository.MealRepository
import org.springframework.stereotype.Service

@Service
class MealServiceImpl(val mealRepository: MealRepository) : MealService {
    override fun findById(id: Long): Meal? {
        return mealRepository.findMealById(id)
    }

    override fun getAll(): List<Meal> {
        return mealRepository.getAllMeals()
    }
}