package com.tom.pasta.api

import com.tom.pasta.api.converter.toDto
import com.tom.pasta.model.ProductQuantityDto
import com.tom.pasta.model.ShoppingListDto
import com.tom.pasta.shoppinglist.ShoppingListService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ShoppingListResource(val shoppingListService: ShoppingListService) : ShoppingListsApi {
    override fun getShoppingList(shoppingListId: Long): ResponseEntity<ShoppingListDto> {
        val shoppingListDto = shoppingListService.findById(shoppingListId)?.toDto()
        return if (shoppingListDto == null) ResponseEntity(HttpStatus.NOT_FOUND)
        else ResponseEntity.ok(shoppingListDto)
    }

    override fun getShoppingListMergedProductEntries(shoppingListId: Long): ResponseEntity<List<ProductQuantityDto>> {
        val productQuantities = shoppingListService.getShoppingListMergedProductEntries(shoppingListId)
            .map { it.toDto() }
        return ResponseEntity.ok(productQuantities)
    }
}
