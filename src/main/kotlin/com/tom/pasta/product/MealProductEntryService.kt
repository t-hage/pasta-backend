package com.tom.pasta.product

import com.tom.pasta.product.model.ProductEntry
import com.tom.pasta.product.repository.MealProductEntryRepository
import org.springframework.stereotype.Service

@Service
class MealProductEntryService(val mealProductEntryRepository: MealProductEntryRepository) {
    fun getAllByMealId(id: Long): List<ProductEntry> {
        return mealProductEntryRepository.getAllByMealId(id)
    }
}