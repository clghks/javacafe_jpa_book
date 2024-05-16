package com.clghks.jpashop.domain

import com.clghks.jpashop.exception.NotEnoughStockException
import jakarta.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    var id: Long? = null

    var name: String? = null

    var price: Int = 0

    var stockQuantity: Int = 0

    @ManyToMany(mappedBy = "items")
    var categories: MutableList<Category>? = null

    fun addStock(quantity: Int) {
        this.stockQuantity += quantity
    }

    fun removeStock(quantity: Int) {
        val restStock = this.stockQuantity - quantity
        if (restStock < 0) {
            throw NotEnoughStockException("need more stock")
        }
        this.stockQuantity = restStock
    }
}