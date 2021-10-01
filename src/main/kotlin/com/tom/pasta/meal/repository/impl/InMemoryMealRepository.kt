package com.tom.pasta.meal.repository.impl

import com.tom.pasta.meal.model.Meal
import com.tom.pasta.meal.repository.MealRepository
import com.tom.pasta.product.repository.MealProductEntryRepository
import org.springframework.stereotype.Repository

@Repository
class InMemoryMealRepository(val mealProductEntryRepository: MealProductEntryRepository) : MealRepository {

    private val allMeals = listOf(
        asMeal(1, "Lasagna"),
        asMeal(2, "Chicken Wrap"),
    )

    override fun findMealById(id: Long): Meal? {
        return allMeals.firstOrNull { it.id == id }
    }

    override fun getAllMeals(): List<Meal> {
        return allMeals
    }

    private fun asMeal(
        id: Long,
        name: String
    ): Meal {
        val productEntries = mealProductEntryRepository.getAllByMealId(id)
        return Meal(id, name, productEntries)
    }
}
