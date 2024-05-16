package com.clghks.jpashop.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "ORDERS")
class Order {
    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    var member: Member? = null

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    var orderItems: MutableList<OrderItem> = arrayListOf()

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "DELIVERY_ID")
    var delivery: Delivery? = null

    var orderDate: Date? = null

    @Enumerated(EnumType.STRING)
    var status: OrderStatus? = null

    constructor() {}

    constructor(member: Member) {
        this.member = member
        member.orders?.add(this)
    }

    fun addOrderItem(orderItem: OrderItem) {
        this.orderItems.add(orderItem)
        orderItem.order = this
    }

    fun addDelivery(delivery: Delivery) {
        this.delivery = delivery
        delivery.order = this
    }

    // 주문취소
    fun cancel() {
        if (this.delivery?.status == DeliveryStatus.COMP) {
            throw RuntimeException("이미 배송완료된 상품은 취소가 불가능합니다.")
        }

        this.status = OrderStatus.CANCEL
        for (orderItem in this.orderItems) {
            orderItem.cancel()
        }
    }

    // 조회
    fun getTotalPrice(): Int {
        var totalPrice = 0
        for (orderItem in this.orderItems) {
            totalPrice += orderItem.getTotalPrice()
        }
        return totalPrice
    }

    companion object {
        // 생성 메소드
        fun createOrder(member: Member, delivery: Delivery, vararg orderItems: OrderItem): Order {
            val order = Order()
            order.member = member
            order.delivery = delivery
            for (orderItem in orderItems) {
                order.addOrderItem(orderItem)
            }
            order.status = OrderStatus.ORDER
            order.orderDate = Date()

            return order
        }
    }
}