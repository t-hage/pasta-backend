package com.tom.pasta.api

import com.tom.pasta.meal.MealService
import com.tom.pasta.product.ProductService
import org.springframework.web.bind.annotation.RestController

@RestController
class MealResource(val mealService: MealService)
