package com.tom.pasta.meal.repository.impl

import com.tom.pasta.meal.model.Meal
import com.tom.pasta.meal.repository.MealRepository
import com.tom.pasta.product.repository.MealProductEntryRepository
import org.springframework.stereotype.Repository

@Repository
class InMemoryMealRepository(val mealProductEntryRepository: MealProductEntryRepository) : MealRepository {

    private val allMeals = mutableListOf(
        asMeal(1, "Lasagna"),
        asMeal(2, "Chicken Wrap"),
    )

    override fun findById(id: Long): Meal? {
        return allMeals.firstOrNull { it.id == id }
            ?.copy(productEntries = mealProductEntryRepository.getAllByMealId(id))
    }

    override fun getAllMeals(): List<Meal> {
        return allMeals.map { it.copy(productEntries = mealProductEntryRepository.getAllByMealId(it.id!!)) }
    }

    override fun create(meal: Meal): Meal {
        val newMeal = meal.copy(id = getNewId())
        allMeals.add(newMeal)

        mealProductEntryRepository.upsertProductEntriesForMealId(newMeal.id!!, meal.productEntries)
        return findById(newMeal.id) ?: throw Exception("Not found")
    }

    override fun update(meal: Meal): Meal? {
        findById(meal.id!!) ?: return null
        delete(meal.id)
        allMeals.add(meal)
        mealProductEntryRepository.upsertProductEntriesForMealId(meal.id, meal.productEntries)
        return findById(meal.id) ?: throw Exception("Not found")
    }

    override fun delete(id: Long) {
        val toRemove = findById(id) ?: return
        allMeals.remove(toRemove)
        mealProductEntryRepository.deleteAllByMealId(id)
    }

    private fun getNewId(): Long {
        val currentHighestId = allMeals.map { it.id }.maxByOrNull { it!! } ?: 0L
        return currentHighestId + 1
    }

    private fun asMeal(
        id: Long,
        name: String
    ): Meal {
        val productEntries = mealProductEntryRepository.getAllByMealId(id)
        return Meal(id, name, productEntries)
    }
}
