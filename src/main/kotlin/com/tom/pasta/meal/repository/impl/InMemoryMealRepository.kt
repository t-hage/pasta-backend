package com.tom.pasta.meal.repository.impl

import com.tom.pasta.meal.model.Meal
import com.tom.pasta.meal.repository.MealRepository
import com.tom.pasta.product.repository.MealProductEntryRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException

@Repository
class InMemoryMealRepository(val mealProductEntryRepository: MealProductEntryRepository) : MealRepository {

    private val allMeals = mutableListOf(
        asMeal(1, "Lasagna"),
        asMeal(2, "Chicken Wrap"),
    )

    override fun findById(id: Long): Meal? {
        return allMeals.firstOrNull { it.id == id }
            ?.copy(productEntries = mealProductEntryRepository.getAll(id))
    }

    override fun getAllMeals(): List<Meal> {
        return allMeals.map { it.copy(productEntries = mealProductEntryRepository.getAll(it.id!!)) }
    }

    override fun create(meal: Meal): Meal {
        val newMeal = meal.copy(id = getNewId(), productEntries = emptyList())
        allMeals.add(newMeal)
        return findById(newMeal.id!!) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    override fun update(meal: Meal): Meal? {
        findById(meal.id!!) ?: return null
        delete(meal.id)
        allMeals.add(meal.copy(productEntries = emptyList()))
        return findById(meal.id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    override fun delete(id: Long) {
        allMeals.removeIf { it.id == id }
        mealProductEntryRepository.deleteAll(id)
    }

    private fun getNewId(): Long {
        val currentHighestId = allMeals.map { it.id }.maxByOrNull { it!! } ?: 0L
        return currentHighestId + 1
    }

    private fun asMeal(
        id: Long,
        name: String
    ): Meal {
        return Meal(id, name, mutableListOf())
    }
}
