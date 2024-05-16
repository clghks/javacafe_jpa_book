package com.clghks.jpashop.web

import com.clghks.jpashop.domain.Book
import com.clghks.jpashop.domain.Item
import com.clghks.jpashop.service.ItemService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class ItemController(
    private val itemService: ItemService
) {
    @RequestMapping(value = ["/items/new"], method = [RequestMethod.GET])
    fun createForm(): String {
        return "items/createItemForm"
    }

    @RequestMapping(value = ["/items/new"], method = [RequestMethod.POST])
    fun create(book: Book): String {
        itemService.saveItem(book)

        return "redirect:/items"
    }

    @RequestMapping(value = ["/items/{itemId}/edit"], method = [RequestMethod.GET])
    fun updateItemFrom(@PathVariable itemId: Long, model: Model): String {
        val item: Item = itemService.findOne(itemId)
        model.addAttribute("item", item)

        return "items/updateItemForm"
    }

    @RequestMapping(value = ["/items/{itemId}/edit"], method = [RequestMethod.POST])
    fun updateItem(@ModelAttribute item: Book): String {
        itemService.saveItem(item)

        return "redirect:/items"
    }


}