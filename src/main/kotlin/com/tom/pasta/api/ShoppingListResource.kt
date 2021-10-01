package com.tom.pasta.api

import com.tom.pasta.api.converter.toDto
import com.tom.pasta.model.ShoppingListDto
import com.tom.pasta.shoppinglist.ShoppingListService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ShoppingListResource(val shoppingListService: ShoppingListService) : ShoppingListsApi {
    override fun getShoppingLists(): ResponseEntity<List<ShoppingListDto>> {
        return ResponseEntity.ok(shoppingListService.getAll().map { it.toDto() })
    }
}
