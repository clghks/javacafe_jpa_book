package com.clghks.jpashop.repository

import com.clghks.jpashop.domain.Item
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository

@Repository
class ItemRepository(
    @PersistenceContext
    private val entityManager: EntityManager
) {
    fun save(item: Item) {
        if (item.id == null) {
            entityManager.persist(item)
        } else {
            entityManager.merge(item)
        }
    }

    fun findOne(id: Long?): Item {
        return entityManager.find(Item::class.java, id)
    }

    fun findAll(): List<Item> {
        return entityManager.createQuery("select i from Item i", Item::class.java).resultList
    }
}