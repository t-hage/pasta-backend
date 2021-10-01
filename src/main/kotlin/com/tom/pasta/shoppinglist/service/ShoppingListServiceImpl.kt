package com.tom.pasta.shoppinglist.service

import com.tom.pasta.meal.MealService
import com.tom.pasta.meal.model.Meal
import com.tom.pasta.meal.repository.MealRepository
import com.tom.pasta.shoppinglist.ShoppingListService
import com.tom.pasta.shoppinglist.model.ShoppingList
import com.tom.pasta.shoppinglist.repository.ShoppingListRepository
import org.springframework.stereotype.Service

@Service
class ShoppingListServiceImpl(val shoppingListRepository: ShoppingListRepository) : ShoppingListService {
    override fun findById(id: Long): ShoppingList? {
        return shoppingListRepository.findById(id)
    }

    override fun getAll(): List<ShoppingList> {
        return shoppingListRepository.getAll()
    }

}