package com.clghks.jpashop.service

import com.clghks.jpashop.domain.Item
import com.clghks.jpashop.repository.ItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ItemService(
    @Autowired
    private val itemRepository: ItemRepository
) {
    fun saveItem(item: Item) {
        itemRepository.save(item)
    }

    fun findItems(): List<Item> {
        return itemRepository.findAll()
    }

    fun findOne(itemId: Long?): Item {
        return itemRepository.findOne(itemId)
    }
}