package com.tom.pasta.product.service

import com.tom.pasta.product.MealProductEntryService
import com.tom.pasta.product.model.ProductEntry
import com.tom.pasta.product.repository.MealProductEntryRepository
import org.springframework.stereotype.Service

@Service
class MealProductEntryServiceImpl(val mealProductEntryRepository: MealProductEntryRepository) :
    MealProductEntryService {
    override fun getAll(mealId: Long): List<ProductEntry> {
        return mealProductEntryRepository.getAll(mealId)
    }

    override fun create(mealId: Long, productEntry: ProductEntry): ProductEntry {
        return mealProductEntryRepository.create(mealId, productEntry)
    }

    override fun update(mealId: Long, productEntry: ProductEntry) {
        mealProductEntryRepository.update(mealId, productEntry)
    }

    override fun delete(mealId: Long, productEntryId: Long) {
        mealProductEntryRepository.deleteForMeal(mealId, productEntryId)
    }
}
