package com.clghks.jpashop.domain

import jakarta.persistence.*

@Entity
@Table(name = "ORDER_ITEM")
class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "ORDER_ITEM_ID")
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    var item: Item? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    var order: Order? = null

    var orderPrice: Int = 0

    var count: Int = 0

    // 주문 취소
    fun cancel() {
        item?.addStock(count)
    }

    fun getTotalPrice(): Int {
        return orderPrice * count
    }

    companion object {

        // 생성 메소드
        fun createOrderItem(item: Item, orderPrice: Int, count: Int): OrderItem {
            val orderItem = OrderItem()
            orderItem.item = item
            orderItem.orderPrice = orderPrice
            orderItem.count = count

            item.removeStock(count)
            return orderItem
        }
    }
}